package day13

import common.io.inputFile
import java.io.File
import kotlin.math.min


val file = inputFile("day13.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<String, String> {
    val patters = parseInput(file.readText().trim())
    return Pair("${solvePartOne(patters)}", "")
}

fun parseInput(input: String): List<String> =
    input.split("""\s+\n""".toRegex())

fun solvePartOne(patterns: List<String>): Int =
    patterns.sumOf { pattern ->
        val horizontal = pattern.lines().mirrorCandidates().firstOrNull() ?: 0
        val vertical = pattern.rows().mirrorCandidates().firstOrNull() ?: 0
        horizontal + 100 * vertical
    }

fun String.rows(): List<String> {
    val lines = lines()
    return (0 until lines.first().length)
        .map { column -> lines.map { it[column] }.joinToString(separator = "") }
}

fun String.mirrorCandidates(): Set<Int> =
    (1 until length)
        .asSequence()
        .map { it to min(it, length - it) }
        .map { (i, len) -> (i - len until i) to (i until i + len) }
        .map { substring(it.first) to substring(it.second) }
        .mapIndexed { index, parts -> index to (parts.first == parts.second.reversed()) }
        .filter { (_, mirrored) -> mirrored }
        .map { it.first + 1 }
        .toSet()

fun List<String>.mirrorCandidates(): Set<Int> =
    map(String::mirrorCandidates).reduce(Set<Int>::intersect)
