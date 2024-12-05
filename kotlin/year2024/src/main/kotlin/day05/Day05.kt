package day05

import common.io.inputFile
import common.strings.notEmptyLines
import java.io.File

val file = inputFile("day05.txt")

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
  val (rules, allUpdates) = parseInput(input)

  val correctUpdates = allUpdates.filter { update -> update.isInOrder(rules) }

  return correctUpdates.sumOf { update -> update[update.size / 2] }.toString()
}

fun part2(input: String): String {
  return "TODO2"
}

typealias Rules = Map<Int, List<Int>>

typealias Update = List<Int>

fun Update.isInOrder(rules: Rules): Boolean =
  mapIndexed { index, page ->
    subList(index + 1, size).all { it in (rules[page] ?: emptyList()) }
  }.all { it }

fun parseInput(input: String): Pair<Rules, List<Update>> {
  val (rulesInput, updatesInput) = input.split("\n\n")

  val rules = rulesInput.notEmptyLines()
    .map { line -> line.split("|") }
    .groupBy(
      keySelector = { it[0].toInt() },
      valueTransform = { it[1].toInt() },
    )
  val updates = updatesInput.notEmptyLines()
    .map { line -> line.split(",").map { it.toInt() } }

  return Pair(rules, updates)
}
