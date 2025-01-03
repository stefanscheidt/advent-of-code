package day10

import common.geom.Point2D
import common.geom.plus

// Grid

typealias TopoMap = List<List<Int>>

fun topoMapOf(lines: List<String>): List<List<Int>> =
  lines.map { line -> line.map { digit -> digit.digitToInt() } }

operator fun TopoMap.contains(p: Point2D): Boolean = p.y in indices && p.x in this[p.y].indices

operator fun TopoMap.get(p: Point2D): Int = this[p.y][p.x]

val cardinalDirections: List<Point2D> =
  listOf(Point2D(0, -1), Point2D(1, 0), Point2D(0, 1), Point2D(-1, 0))

val Point2D.cardinalNeighbors: List<Point2D>
  get() = cardinalDirections.map { this + it }

// Count Paths

fun TopoMap.countReachablePeeks(start: Point2D): Int {
  val queue = mutableListOf(start)
  val seen = mutableSetOf<Point2D>()
  var count = 0

  while (queue.isNotEmpty()) {
    val position = queue.removeFirst()
    if (position !in seen) {
      seen += position
      if (this[position] == 9) {
        count++
      } else {
        queue.addAll(
          position.cardinalNeighbors.filter { neighbour ->
            neighbour in this && this[neighbour] == this[position] + 1
          }
        )
      }
    }
  }

  return count
}

fun TopoMap.countPaths(start: Point2D): Int {
  val queue = mutableListOf(start)
  var count = 0

  while (queue.isNotEmpty()) {
    val position = queue.removeFirst()
    if (this[position] == 9) {
      count++
    } else {
      queue.addAll(
        position.cardinalNeighbors.filter { neighbour ->
          neighbour in this && this[neighbour] == this[position] + 1
        }
      )
    }
  }

  return count
}

val TopoMap.trailheads: List<Point2D>
  get() = flatMapIndexed { y, row ->
    row.mapIndexedNotNull { x, hight -> if (hight == 0) Point2D(x, y) else null }
  }

fun TopoMap.scoreTrails(): Int {
  return trailheads.sumOf { trailhead -> countReachablePeeks(trailhead) }
}

fun TopoMap.rateTrails(): Int {
  return trailheads.sumOf { trailhead -> countPaths(trailhead) }
}

// Part 1

fun part1(input: List<String>): String = topoMapOf(input).scoreTrails().toString()

// Part 2

fun part2(input: List<String>): String = topoMapOf(input).rateTrails().toString()
