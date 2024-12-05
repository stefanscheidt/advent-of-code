package day04

import common.geom.Point2D
import common.geom.p2
import common.geom.plus
import common.io.inputFile
import java.io.File

val file = inputFile("day04.txt")

fun main() {
  val (part1, part2) = solvePuzzle(file)
  println("1: $part1")
  println("2: $part2")
}

fun solvePuzzle(file: File): Pair<String, String> {
  val input = file.readLines()
  return Pair(part1(input), part2(input))
}

fun part1(input: List<String>): String =
  input
    .traverse { pos, char ->
      if (char == 'X') {
        directions.count { direction -> input.containsWordAt("XMAS", pos, direction) }
      } else {
        0
      }
    }
    .sum()
    .toString()

fun part2(input: List<String>): String =
  input
    .traverse { pos, char ->
      if (char == 'A') {
        X.tips.mapNotNull { input.getAtOrNull(pos + it) }.joinToString("") in X.tipChars
      } else {
        false
      }
    }
    .count { it }
    .toString()

fun <T> List<String>.traverse(fn: (Point2D, Char) -> T): List<T> = flatMapIndexed { y, row ->
  row.mapIndexed { x, char -> fn(p2(x, y), char) }
}

fun List<String>.getAtOrNull(pos: Point2D): Char? = getOrNull(pos.y)?.getOrNull(pos.x)

tailrec fun List<String>.containsWordAt(word: String, pos: Point2D, direction: Point2D): Boolean =
  when {
    word.isEmpty() -> true
    word.first() != getAtOrNull(pos) -> false
    else -> containsWordAt(word.drop(1), pos + direction, direction)
  }

val directions =
  listOf(p2(-1, -1), p2(-1, 0), p2(-1, 1), p2(0, -1), p2(0, 1), p2(1, -1), p2(1, 0), p2(1, 1))

data object X {
  // counter-clockwise, order matters
  val tips = listOf(p2(-1, -1), p2(-1, 1), p2(1, 1), p2(1, -1))
  // possible tip chars when visiting tips counter-clockwise
  val tipChars = listOf("MMSS", "MSSM", "SSMM", "SMMS")
}
