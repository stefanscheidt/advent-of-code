package day01

import common.io.inputFile
import java.io.File
import kotlin.math.abs

val file = inputFile("day01.txt")

fun main() {
  val (part1, part2) = solvePuzzle(file)
  println("1: $part1")
  println("2: $part2")
}

fun solvePuzzle(file: File): Pair<String, String> {
  val input = file.readText()
  return Pair(part1(input), part2(input))
}

fun part1(input: String): String {
  val (list1, list2) = inputLists(input)
  val pairs = list1.sorted().zip(list2.sorted())
  val result = pairs.sumOf { (first, second) -> abs(first - second) }
  return "$result"
}

fun part2(input: String): String {
  val (that, others) = inputLists(input)
  val result = that.sumOf { value -> value.score(others) }
  return "$result"
}

private fun Long.score(others: List<Long>): Long = this * others.count { other -> this == other }

private fun inputLists(input: String): Pair<List<Long>, List<Long>> =
  input
    .lines()
    .filterNot(String::isBlank)
    .map { line -> line.split("""\s+""".toRegex()).map(String::toLong) }
    .toPairOfLists()

private fun List<List<Long>>.toPairOfLists(): Pair<List<Long>, List<Long>> =
  Pair(mutableListOf<Long>(), mutableListOf<Long>()).apply {
    forEach { pair ->
      first += pair[0]
      second += pair[1]
    }
  }
