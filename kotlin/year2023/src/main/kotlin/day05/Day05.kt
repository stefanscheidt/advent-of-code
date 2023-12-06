package day05

import common.io.inputFile
import java.io.File


val file = inputFile("day05.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<String, String> {
    val input = splitInput(file.readText())
    return Pair("${solvePartOne(input)}", "${solvePartTwo(input)}")
}

fun splitInput(input: String): List<String> =
    input.split("""\s+\n""".toRegex()).filter(String::isNotBlank)

fun solvePartOne(input: List<String>): Long? {
    val seedValues = input.first().substringAfter("seeds: ").split(" ").map(String::toLong)
    val instructions = parseCareInstructions(input.drop(1))
    return seedValues.minOfOrNull(instructions::mappedValue)
}

fun solvePartTwo(input: List<String>): Long {
    val seedValueRanges = input.first().substringAfter("seeds: ").split(" ").map(String::toLong).chunked(2).map {
        listOf((it[0] until it[0] + it[1]))
    }
    val instructions = parseCareInstructions(input.drop(1))
    return seedValueRanges.minOf(instructions::minimumMappedValue)
}

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

    fun mappedValues(srcValues: List<LongRange>): List<LongRange> {
        // TODO
        return srcValues
    }
}

typealias CareInstructions = Map<String, CareInstruction>

tailrec fun CareInstructions.mappedValue(scrValue: Long, src: String = "seed", dest: String = "location"): Long {
    if (src == dest) return scrValue
    val instruction = get(src) ?: return scrValue
    return mappedValue(instruction.mappedValue(scrValue), instruction.dest, dest)
}

tailrec fun CareInstructions.minimumMappedValue(srcValues: List<LongRange>, src: String = "seed", dest: String = "location"): Long {
    if (src == dest) return srcValues.first().first
    val instruction = get(src) ?: return srcValues.first().first
    return minimumMappedValue(instruction.mappedValues(srcValues), instruction.dest, dest)
}
