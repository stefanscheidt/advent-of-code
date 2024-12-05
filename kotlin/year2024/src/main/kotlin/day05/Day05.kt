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

  return correctUpdates.sumOf(Update::middlePage).toString()
}

fun part2(input: String): String {
  val (rules, allUpdates) = parseInput(input)

  val incorrectUpdatesSorted =
    allUpdates
      .filter { update -> !update.isInOrder(rules) }
      .map {
        it.sortedWith { page1, page2 ->
          when {
            page1.mustBePrintedBefore(page2, rules) -> -1
            page2.mustBePrintedBefore(page1, rules) -> 1
            else -> 0
          }
        }
      }

  return incorrectUpdatesSorted.sumOf(List<Int>::middlePage).toString()
}

typealias Rules = Map<Int, List<Int>>

fun Int.mustBePrintedBefore(after: Int, rules: Rules): Boolean =
  after in (rules[this] ?: emptyList())

typealias Update = List<Int>

fun Update.isInOrder(rules: Rules): Boolean =
  mapIndexed { index, fst ->
      subList(index + 1, size).all { snd -> fst.mustBePrintedBefore(snd, rules) }
    }
    .all { it }

val Update.middlePage: Int
  get() = this[size / 2]

fun parseInput(input: String): Pair<Rules, List<Update>> {
  val (rulesInput, updatesInput) = input.split("\n\n")

  val rules =
    rulesInput
      .notEmptyLines()
      .map { line -> line.split("|") }
      .groupBy(keySelector = { it[0].toInt() }, valueTransform = { it[1].toInt() })

  val updates = updatesInput.notEmptyLines().map { line -> line.split(",").map { it.toInt() } }

  return Pair(rules, updates)
}
