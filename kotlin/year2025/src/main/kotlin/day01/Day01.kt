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
        Pair((position + update).mod(100), visitsOfZero(position, update))
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

fun visitsOfZero(position: Int, update: Int): Int {
  val fullTurns = abs(update / 100)
  val signedRemainingClicks = update.rem(100)
  val signedNewPosition = position + signedRemainingClicks
  val additionalVisit =
    (position > 0 && (signedNewPosition <= 0 || signedNewPosition >= 100)) ||
      (position < 0 && (signedNewPosition >= 0 || signedNewPosition <= -100))
  return fullTurns + (if (additionalVisit) 1 else 0)
}
