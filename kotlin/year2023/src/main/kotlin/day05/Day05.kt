package day05

import common.io.inputFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import java.io.File


val file = inputFile("day05.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<String, String> {
    val input = splitInput(file.readText())
    val solutionOne = solvePartOne(input)
    val solution = solvePartTwo(input)
    return Pair("$solutionOne", "$solution")
}

fun splitInput(input: String): List<String> =
    input.split("""\s+\n""".toRegex()).filter(String::isNotBlank)

fun solvePartOne(input: List<String>): Long? {
    val seedValues = parseSeedValues(input.first())
    val instructions = parseCareInstructions(input.drop(1))
    return seedValues.minOfOrNull(instructions::mappedValue)
}

fun solvePartTwo(input: List<String>): Long {
    val seedValueRanges = parseSeedValueRanges(input.first())
    val instructions = parseCareInstructions(input.drop(1))

    return runBlocking(Dispatchers.Default) {
        instructions.minValueFor(seedValueRanges)
    }
}

fun parseSeedValues(input: String): List<Long> =
    input.substringAfter("seeds: ").split(" ").map(String::toLong)

fun parseSeedValueRanges(input: String): List<LongRange> =
    input.substringAfter("seeds: ")
        .split(" ")
        .map(String::toLong)
        .chunked(2)
        .map { (it[0] until it[0] + it[1]) }

fun parseCareInstructions(input: List<String>): CareInstructions =
    input.map(::parseCareInstruction).associateBy { it.src }

fun parseCareInstruction(input: String): CareInstruction {
    val lines = input.lines().filter(String::isNotBlank)
    val (src, dest) = """^(\w+)-to-(\w+) map:$""".toRegex()
        .find(lines.first())
        ?.destructured
        ?: error("invalid care instruction\n$input")
    val mappings = lines.drop(1).map { line ->
        val (destStart, srcStart, length) = line.split(" ").map(String::toLong)
        Mapping((srcStart until srcStart + length), destStart - srcStart)
    }
    return CareInstruction(src, dest, mappings.sortedBy { it.range.first })
}

data class Mapping(val range: LongRange, val offset: Long)

data class CareInstruction(val src: String, val dest: String, val mappings: List<Mapping>) {
    fun mappedValue(srcValue: Long): Long =
        mappings.find { srcValue in it.range }
            ?.let { mapping -> srcValue + mapping.offset }
            ?: srcValue
}

typealias CareInstructions = Map<String, CareInstruction>

tailrec fun CareInstructions.mappedValue(scrValue: Long, src: String = "seed", dest: String = "location"): Long {
    if (src == dest) return scrValue
    val instruction = get(src) ?: return scrValue
    return mappedValue(instruction.mappedValue(scrValue), instruction.dest, dest)
}

suspend fun CareInstructions.minValueFor(seedRanges: List<LongRange>): Long {
    return coroutineScope {
        seedRanges
            .map { range ->
                async {
                    println("[${Thread.currentThread().name}] processing $range ...")
                    range.asSequence().map(::mappedValue).min()
                }
            }
            .awaitAll()
            .min()
    }
}
