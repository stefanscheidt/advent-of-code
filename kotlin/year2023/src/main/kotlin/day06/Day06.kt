package day06

import common.io.inputFile
import java.io.File


val file = inputFile("day06.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<String, String> {
    val input = parseValues(file.readText().trim())
    return Pair("${solvePartOne(input)}", "${solvePartTwo(input)}")
}

fun parseValues(input: String): List<List<String>> =
    input.lines().map { it.substringAfter(":").trim().split("""\s+""".toRegex()) }

fun solvePartOne(input: List<List<String>>): Long {
    val (times, distances) = input.map { it.map(String::toLong) }
    val races = times.zip(distances).map { (time, recordDistance) -> Race(time, recordDistance) }
    return races.map(Race::winningOptionsCount).fold(1L, Long::times)
}

fun solvePartTwo(input: List<List<String>>): Long {
    val (time, recordDistance) = input.map { it.joinToString(separator = "").toLong() }
    return Race(time, recordDistance).winningOptionsCount
}

data class Race(val time: Long, val recordDistance: Long) {
    val winningOptionsCount: Long by lazy {
        var count = 0L
        for (millis in (0L..time)) {
            if (travelDistance(time, millis) > recordDistance) count++
        }
        count
    }
}

fun travelDistance(raceTime: Long, buttonPressTime: Long): Long =
    buttonPressTime * (raceTime - buttonPressTime)
