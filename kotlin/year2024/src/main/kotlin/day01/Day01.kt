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
  return Pair(part1(file.readText()), part2(file.readText()))
}

fun part1(input: String): String =
  inputLists(input)
    .let { (list1, list2) -> list1.sorted().zip(list2.sorted()) }
    .let { pairs -> pairs.map { (first, second) -> abs(first - second) } }
    .let { distances -> "${distances.sum()}" }

fun part2(input: String): String =
  inputLists(input)
    .let { (that, others) -> that.map { value -> value.score(others) } }
    .let { scores -> "${scores.sum()}" }

private fun Long.score(others: List<Long>): Long =
  this * others.count { other -> this == other }

private fun inputLists(input: String): Pair<List<Long>, List<Long>> =
  input.lines()
    .filterNot(String::isBlank)
    .map { line -> line.split("""\s+""".toRegex()).map(String::toLong) }
    .toPairOfLists()

private fun List<List<Long>>.toPairOfLists(): Pair<List<Long>, List<Long>> =
  Pair(mutableListOf<Long>(), mutableListOf<Long>())
    .apply {
      forEach { pair ->
        first += pair[0]
        second += pair[1]
      }
    }
