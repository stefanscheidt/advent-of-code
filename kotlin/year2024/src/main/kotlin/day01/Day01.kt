package day01

import common.io.inputFile
import java.io.File
import kotlin.math.abs

val file = inputFile("day01.txt")

fun main() {
  val solution = solvePuzzle(file)

  println("1: ${solution.first}")
  println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<String, String> {
  val input = file.readText()
  return Pair(part1(input), part2(input))
}

fun part1(input: String): String {
  val (list1, list2) = inputLists(input)
  val pairs = list1.sorted().zip(list2.sorted())
  val distances = pairs.map { (first, second) -> abs(first - second) }
  return "${distances.sum()}"
}

fun part2(input: String): String {
  val (that, others) = inputLists(input)
  val scores = that.map { value -> value.score(others) }
  return "${scores.sum()}"
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
