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
    val first = ranges.count { oneFullyContainsOther(it.first, it.second) }
    val second = ranges.count { overlap(it.first, it.second) }

    return Pair(first, second)
}

fun parseInput(lines: List<String>): List<Pair<IntRange, IntRange>> =
    lines.map { line ->
        val (s1, e1, s2, e2) = line.split("-", ",").map { it.toInt() }
        Pair(s1..e1, s2..e2)
    }

fun oneFullyContainsOther(range1: IntRange, range2: IntRange): Boolean =
    (range2.first in range1 && range2.last in range1)
            || (range1.first in range2 && range1.last in range2)

fun overlap(range1: IntRange, range2: IntRange): Boolean =
    range1.intersect(range2).isNotEmpty()
