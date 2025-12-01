package day01

import kotlin.math.abs

// Part 1

fun part1(input: List<String>): String {
  val password =
    input
      .map(::parseInput)
      .runningFold(50) { position, update -> (position + update).mod(100) }
      .count { it == 0 }
  return "$password"
}

// Part 2

fun part2(input: List<String>): String {
  val password =
    input
      .map(::parseInput)
      .runningFold(Pair(50, 0)) { (position, _), update ->
        Pair((position + update).mod(100), visits(position, update))
      }
      .sumOf { it.second }

  return "$password"
}

fun parseInput(input: String): Int {
  val direction = input.first()
  val value = input.drop(1).toInt()
  return when (direction) {
    'L' -> -value
    'R' -> value
    else -> 0
  }
}

fun visits(position: Int, update: Int): Int {
  val newPosition = position + update % 100
  val offset =
    if (
      (position > 0 && (newPosition <= 0 || newPosition >= 100)) ||
        (position < 0 && (newPosition >= 0 || newPosition <= -100))
    )
      1
    else 0
  return abs(update / 100) + offset
}
