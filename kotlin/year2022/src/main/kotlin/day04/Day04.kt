package day04

import common.io.inputFile
import java.io.File


val file = inputFile("day04.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<Int, Int> {
    val lines = file.readLines()

    val ranges = parseInput(lines)
    val first = ranges.count(::oneFullyContainsOther)
    val second = ranges.count(::overlap)

    return Pair(first, second)
}

fun parseInput(lines: List<String>): List<List<IntRange>> =
    lines.map { line ->
        line.split("[-,]".toRegex())
            .chunked(2)
            .map { it[0].toInt()..it[1].toInt() }
    }

fun oneFullyContainsOther(ranges: List<IntRange>): Boolean =
    (ranges[0].first <= ranges[1].first && ranges[1].last <= ranges[0].last)
            || (ranges[1].first <= ranges[0].first && ranges[0].last <= ranges[1].last)

fun overlap(ranges: List<IntRange>): Boolean =
    ranges[0].intersect(ranges[1]).isNotEmpty()
