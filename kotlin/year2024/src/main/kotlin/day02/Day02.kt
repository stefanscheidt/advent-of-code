package day02

import common.io.inputFile
import java.io.File
import kotlin.math.abs

val file = inputFile("day02.txt")

fun main() {
  val (part1, part2) = solvePuzzle(file)
  println("1: $part1")
  println("2: $part2")
}

fun solvePuzzle(file: File): Pair<String, String> =
  file.readLines().let { lines -> Pair(part1(lines), part2(lines)) }

fun part1(input: List<String>): String = parseInput(input).count(Report::isSafe).let(Int::toString)

fun part2(input: List<String>): String =
  parseInput(input)
    .count { report -> report.isSafe || report.dampedReports().any(Report::isSafe) }
    .let(Int::toString)

private fun parseInput(input: List<String>): List<Report> =
  input.map { line -> line.split(" ").map(String::toLong) }

// Domain

typealias Report = List<Long>

val Report.isSafe: Boolean
  get() = zipWithNext().run { (allIncreasing || allDecreasing) && allDistancesInRange(1L..3L) }

fun Report.dampedReports(): List<Report> =
  indices.map { index -> toMutableList().apply { removeAt(index) } }

// Helper

private val List<Pair<Long, Long>>.allIncreasing
  get() = all { (fst, snd) -> fst <= snd }

private val List<Pair<Long, Long>>.allDecreasing
  get() = all { (fst, snd) -> fst >= snd }

private fun List<Pair<Long, Long>>.allDistancesInRange(range: LongRange) = all { (fst, snd) ->
  abs(fst - snd) in range
}
