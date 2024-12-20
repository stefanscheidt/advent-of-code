package day20

import common.geom.Point2D
import common.geom.p2
import common.geom.plus

// Directions

val cardinalDirections: List<Point2D> =
  listOf(Point2D(0, -1), Point2D(1, 0), Point2D(0, 1), Point2D(-1, 0))

// Racetrack

typealias Racetrack = List<CharArray>

fun List<String>.toRacetrack(): Racetrack =
  map { it.toCharArray() }

operator fun Racetrack.contains(p: Point2D): Boolean =
  p.y in indices && p.x in this[p.y].indices

operator fun Racetrack.get(p: Point2D): Char = this[p.y][p.x]

operator fun Racetrack.set(p: Point2D, value: Char) {
  this[p.y][p.x] = value
}

fun Racetrack.findChar(char: Char): Point2D? {
  for (y in indices) {
    for (x in this[y].indices) {
      if (this[y][x] == char) {
        return p2(x, y)
      }
    }
  }
  return null
}

fun Racetrack.shortestPathLength(start: Point2D, end: Point2D): Int? {
  if (start !in this || end !in this) return null

  var shortestsLength: Int? = null
  val queue = mutableListOf(start to 0)
  val seen = mutableSetOf<Point2D>()
  while (queue.isNotEmpty()) {
    val (position, length) = queue.removeFirst()
    if (position == end) {
      shortestsLength = length.coerceAtMost(shortestsLength ?: length)
    } else {
      seen += position
      val nextPositions = cardinalDirections.map { position + it }
        .filter { it in this && this[it] != '#' && it !in seen }
      queue.addAll(nextPositions.map { it to length + 1 })
    }

  }

  return shortestsLength
}

fun Racetrack.innerWalls(): Set<Point2D> =
  buildSet {
    val maze = this@innerWalls
    for (y in 1..<maze.size - 1) {
      for (x in 1..<maze[y].size - 1) {
        if (maze[y][x] == '#') add(p2(x, y))
      }
    }
  }

fun <T> Racetrack.withCheatAt(pos: Point2D, fn: (Racetrack) -> T): T {
  this[pos] = '.'
  val result = fn(this)
  this[pos] = '#'
  return result
}

fun Racetrack.savings(): Map<Int, List<Point2D>> {
  val start = findChar('S') ?: error("Start not found")
  val end = findChar('E') ?: error("End not found")
  val withoutCheating = shortestPathLength(start, end) ?: error("No path found")

  return innerWalls()
    .map { wallPosition ->
      val withCheating =
        withCheatAt(wallPosition) { it.shortestPathLength(start, end) ?: withoutCheating }
      wallPosition to withoutCheating - withCheating
    }
    .filterNot { it.second == 0 }
    .groupBy(
      keySelector = { it.second },
      valueTransform = { it.first },
    )
}

// Part 1

fun part1(input: List<String>): String {
  val maze = input.toRacetrack()
  val savings = maze.savings()
  val sum = savings.filterKeys { it >= 100 }.values.sumOf { it.size }

  return sum.toString()
}

// Part 2

fun part2(input: List<String>): String {
  return "TODO2"
}
