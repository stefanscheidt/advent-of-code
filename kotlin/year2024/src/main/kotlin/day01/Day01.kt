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
  val frequencies = others.groupingBy { it }.eachCount()
  val result = that.sumOf { value -> value * frequencies.getOrDefault(value, 0) }
  return "$result"
}

private fun inputLists(input: String): Pair<List<Long>, List<Long>> =
  input
    .lines()
    .filterNot(String::isBlank)
    .map { line ->
      val (fst, snd) = line.split("""\s+""".toRegex())
      Pair(fst.toLong(), snd.toLong())
    }
    .unzip()
