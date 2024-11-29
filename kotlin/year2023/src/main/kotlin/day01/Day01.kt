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
    calibrationValue(input.map(::normalizeNumerals).map(::replaceNumerals)),
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

val normalizers =
  mapOf(
    "oneight" to "oneeight",
    "twone" to "twoone",
    "threeight" to "threeeight",
    "fiveight" to "fiveeight",
    "sevenine" to "sevennine",
    "eightwo" to "eighttwo",
    "eighthree" to "eightthree",
    "nineight" to "nineeight",
  )

fun normalizeNumerals(line: String): String =
  normalizers.entries.fold(line) { current, entry -> current.replace(entry.key, entry.value) }

val numerals =
  mapOf(
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9",
  )

fun replaceNumerals(line: String): String =
  numerals.entries.fold(line) { current, entry -> current.replace(entry.key, entry.value) }
