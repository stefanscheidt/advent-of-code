package day03

import common.io.inputFile
import java.io.File

val file = inputFile("day03.txt")

fun main() {
  val solution = solvePuzzle(file)

  println("1: ${solution.first}")
  println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<String, String> {
  val schematic = Schematic(file.readLines().filterNot(String::isBlank))
  return Pair("${schematic.partNumbers.sum()}", "${schematic.gearRatios.sum()}")
}

data class NumberPosition(val row: Int, val columns: IntRange)

data class CharPosition(val row: Int, val column: Int)

fun CharPosition.isAdjacent(number: NumberPosition): Boolean =
  row in (number.row - 1..number.row + 1) &&
    column in (number.columns.first - 1..number.columns.last + 1)

data class Schematic(val lines: List<String>) {

  private val numbers: List<NumberPosition> by lazy {
    lines.flatMapIndexed { rowIndex, line ->
      numberRegex.findAll(line).map { NumberPosition(rowIndex, it.range) }.toList()
    }
  }

  private val symbols: List<CharPosition> by lazy { findCharPositions { it.isSymbol() } }

  private val stars: List<CharPosition> by lazy { findCharPositions { it == '*' } }

  val partNumbers: List<Int> by lazy {
    numbers.filter { number -> symbols.any { symbol -> symbol.isAdjacent(number) } }.map(::valueAt)
  }

  val gearRatios: List<Int> by lazy {
    stars.mapNotNull { star ->
      val adjacentNumbers = adjacentNumbers(star)
      if (adjacentNumbers.size == 2) valueAt(adjacentNumbers[0]) * valueAt(adjacentNumbers[1])
      else null
    }
  }

  private fun valueAt(number: NumberPosition): Int =
    lines[number.row].substring(number.columns).toInt()

  private fun findCharPositions(predicate: (Char) -> Boolean): List<CharPosition> =
    lines.flatMapIndexed { rowIndex, line ->
      line.mapIndexedNotNull { columnIndex, char ->
        if (predicate(char)) CharPosition(rowIndex, columnIndex) else null
      }
    }

  private fun adjacentNumbers(charPosition: CharPosition): List<NumberPosition> =
    numbers.filter { number -> charPosition.isAdjacent(number) }
}

fun Char.isSymbol(): Boolean = !(isDigit() || this == '.')

val numberRegex = """\d+""".toRegex()
