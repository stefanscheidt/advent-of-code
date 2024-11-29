package day06

import common.io.inputFile
import java.io.File

val file = inputFile("day06.txt")

fun main() {
  val solution = solvePuzzle(file)

  println("1: ${solution.first}")
  println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<Int, Int> {
  val input = file.readText()

  val first = input.startOfFirstMarkerWithSize(4) + 4
  val second = input.startOfFirstMarkerWithSize(14) + 14

  return Pair(first, second)
}

fun String.startOfFirstMarkerWithSize(size: Int): Int =
  windowed(size).map { it.toSet() }.indexOfFirst { it.size == size }
