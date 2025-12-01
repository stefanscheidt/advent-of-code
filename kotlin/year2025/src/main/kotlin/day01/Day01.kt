package day01

import kotlin.math.abs

// Part 1

fun part1(input: List<String>): String {
  val positions =
    input.runningFold(50) { position, action ->
      val update = parseAction(action)
      (position + update + 100) % 100
    }
  val password = positions.count { it == 0 }
  return "$password"
}

// Part 2

fun part2(input: List<String>): String {
  val visits =
    input
      .runningFold(Pair(50, 0)) { acc, action ->
        val position = acc.first
        val update = parseAction(action)
        updatePosition(position, update)
      }
      .map { it.second }
  val password = visits.sum()

  return "$password"
}

fun parseAction(action: String): Int {
  val direction = action.first()
  val value = action.drop(1).toInt()
  return when (direction) {
    'L' -> -value
    'R' -> value
    else -> 0
  }
}

fun updatePosition(position: Int, update: Int): Pair<Int, Int> {
  return Pair((position + update + 100) % 100, visits(position, update))
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

