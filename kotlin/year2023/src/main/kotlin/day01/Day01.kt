package day01

import common.io.inputFile
import java.io.File


val file = inputFile("day01.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<Int, Int> {
    val input = file.readLines()
    return Pair(
        calibrationValue(input),
        calibrationValue(input.map(::replaceNumerals))
    )
}

fun calibrationValue(line: String): Int {
    val firstDigit = line.first { it.isDigit() }
    val lastDigit = line.last { it.isDigit() }
    return "$firstDigit$lastDigit".toInt()
}

fun calibrationValue(input: List<String>): Int {
    return input.sumOf { calibrationValue(it) }
}

val numerals = mapOf(
    "one" to "1",
    "oneight" to "18",
    "two" to "2",
    "twone" to "21",
    "three" to "3",
    "threeight" to "38",
    "four" to "4",
    "five" to "5",
    "fiveight" to "58",
    "six" to "6",
    "seven" to "7",
    "sevenine" to "79",
    "eight" to "8",
    "eightwo" to "82",
    "eighthree" to "83",
    "nine" to "9",
    "nineight" to "98",
)

fun replaceNumerals(line: String): String {
    return numerals.entries
        .sortedByDescending { it.key.length }
        .fold(line) { current, entry ->
            current.replace(entry.key, entry.value)
        }
}
