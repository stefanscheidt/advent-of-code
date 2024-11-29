package day20

import common.io.inputFile
import java.io.File

val file = inputFile("day20.txt")

const val decryptionKey = 811589153L

fun main() {
  val solution = solvePuzzle(file)

  println("1: ${solution.first}")
  println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<Long, Long> {
  val input = file.readLines()
  return Pair(solvePartOne(input), solvePartTwo(input))
}

fun solvePartOne(input: List<String>): Long {
  val numbers = parseInput(input)
  val decrypted = decrypt(numbers)
  return groveCoordinates(decrypted).sum()
}

fun solvePartTwo(input: List<String>): Long {
  val numbers = parseInput(input).map { it.copy(value = it.value * decryptionKey) }
  val decrypted = (0..9).fold(numbers) { acc, _ -> decrypt(acc) }
  return groveCoordinates(decrypted).sum()
}

fun parseInput(input: List<String>): List<IndexedLong> =
  input.mapIndexed { index, s -> IndexedLong(index = index, value = s.toLong()) }

data class IndexedLong(val index: Int, val value: Long)

fun decrypt(input: List<IndexedLong>): List<IndexedLong> {
  val decrypted = input.toMutableList()
  decrypted.indices.forEach { index ->
    val oldIndex = decrypted.indexOfFirst { it.index == index }
    val elementToMove = decrypted.removeAt(oldIndex)
    val newIndex = (oldIndex + elementToMove.value).mod(decrypted.size)
    decrypted.add(newIndex, elementToMove)
  }
  return decrypted.toList()
}

fun groveCoordinates(input: List<IndexedLong>): List<Long> {
  val indexOfFirstZero = input.indexOfFirst { it.value == 0L }
  return listOf(1000, 2000, 3000).map { offset ->
    input[(indexOfFirstZero + offset) % input.size].value
  }
}
