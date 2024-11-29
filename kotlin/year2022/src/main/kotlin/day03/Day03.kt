package day03

import common.io.inputFile
import java.io.File

val file = inputFile("day03.txt")

fun main() {
  val solution = solvePuzzle(file)

  println("1: ${solution.first}")
  println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<Int, Int> {
  val inventories = file.readLines()

  val first = inventories.sumOf { it.priority() }
  val second = inventories.chunked(3).map { it.badge() }.sumOf { it.priority() }

  return Pair(first, second)
}

typealias Item = Char

fun Char.priority(): Int =
  if (isLowerCase()) {
    code - 'a'.code + 1
  } else {
    code - 'A'.code + 27
  }

typealias Inventory = String

fun Inventory.wrongPackedItem(): Item {
  val firstHalf = substring(0, length / 2)
  val secondHalf = substring(length / 2)
  return firstHalf.toSet().intersect(secondHalf.toSet()).first()
}

fun Inventory.priority(): Int = wrongPackedItem().priority()

private fun List<Inventory>.badge(): Item {
  val head = first()
  val tail = drop(1)
  return tail.fold(head.toSet()) { acc, items -> acc.intersect(items.toSet()) }.first()
}
