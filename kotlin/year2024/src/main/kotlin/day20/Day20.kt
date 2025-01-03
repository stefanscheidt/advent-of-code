package day20

import common.geom.Point2D
import common.geom.p2
import common.geom.plus
import kotlin.math.abs

// Points

fun dist(p1: Point2D, p2: Point2D): Int = abs(p1.x - p2.x) + abs(p1.y - p2.y)

// Directions

val cardinalDirections: List<Point2D> =
  listOf(Point2D(0, -1), Point2D(1, 0), Point2D(0, 1), Point2D(-1, 0))

// Cheat

data class Cheat(val start: Point2D, val end: Point2D)

// Racetrack

typealias Racetrack = List<CharArray>

fun List<String>.toRacetrack(): Racetrack = map { it.toCharArray() }

operator fun Racetrack.contains(p: Point2D): Boolean = p.y in indices && p.x in this[p.y].indices

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

fun Racetrack.shortestPath(start: Point2D, end: Point2D): List<Point2D>? {
  if (start !in this || end !in this) return null

  var shortestPath: List<Point2D>? = null
  val queue = mutableListOf(start to listOf(start))
  val seen = mutableSetOf<Point2D>()
  while (queue.isNotEmpty()) {
    val (position, path) = queue.removeFirst()
    if (position == end && (shortestPath == null || path.size < shortestPath.size)) {
      shortestPath = path
    } else {
      seen += position
      val nextPositions =
        cardinalDirections
          .map { position + it }
          .filter { it in this && this[it] != '#' && it !in seen }
      queue.addAll(nextPositions.map { it to path + it })
    }
  }

  return shortestPath
}

fun Racetrack.savings(allowedDistance: Int): Map<Int, List<Cheat>> {
  val start = findChar('S') ?: error("Start not found")
  val end = findChar('E') ?: error("End not found")
  val shortestPath = shortestPath(start, end) ?: error("No path found")
  val shortestPathLength = shortestPath.size - 1

  val distsFromStart = buildMap { shortestPath.forEachIndexed { index, pos -> this[pos] = index } }
  val distsToEnd = buildMap {
    shortestPath.reversed().forEachIndexed { index, pos -> this[pos] = index }
    this[start] = shortestPathLength
  }

  val cheats =
    shortestPath
      .flatMap { s ->
        shortestPath
          .filter { e -> dist(s, e) in 2..allowedDistance }
          .map { e ->
            val pathLengthWithCheat = distsFromStart[s]!! + dist(s, e) + distsToEnd[e]!!
            val saving = shortestPathLength - pathLengthWithCheat
            Cheat(s, e) to saving
          }
      }
      .filter { it.second > 0 }

  return cheats.groupBy(keySelector = { it.second }, valueTransform = { it.first })
}

// Part 1

fun part1(input: List<String>): String {
  val racetrack = input.toRacetrack()
  val savings = racetrack.savings(2)
  val sum = savings.filterKeys { it >= 100 }.values.sumOf { it.size }
  return sum.toString()
}

// Part 2

fun part2(input: List<String>): String {
  val racetrack = input.toRacetrack()
  val savings = racetrack.savings(20)
  val sum = savings.filterKeys { it >= 100 }.values.sumOf { it.size }
  return sum.toString()
}
