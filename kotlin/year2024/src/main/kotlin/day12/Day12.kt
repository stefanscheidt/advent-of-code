package day12

import common.geom.Point2D
import common.geom.plus

// Points

val cardinalDirections = listOf(Point2D(0, -1), Point2D(1, 0), Point2D(0, 1), Point2D(-1, 0))

val Point2D.cardinalNeighbors: List<Point2D>
  get() = cardinalDirections.map { this + it }

// Garden

typealias GardenMap = Map<Point2D, Char>

fun List<String>.intoGardenMap(): GardenMap =
  flatMapIndexed { y, row -> row.mapIndexed { x, c -> Point2D(x, y) to c } }.toMap()

fun GardenMap.regions(): Map<Point2D, Int> = buildMap {
  var regionId = 0
  for ((point, plant) in this@regions) {
    if (point in this) continue

    val queue = mutableListOf(point)
    while (queue.isNotEmpty()) {
      val current = queue.removeFirst()
      if (this@regions[current] == plant) {
        this[current] = regionId
        queue.addAll(current.cardinalNeighbors.filter { it !in queue && it !in this })
      }
    }

    regionId++
  }
}

// Part 1

fun part1(input: List<String>): String {
  val gardenMap = input.intoGardenMap()
  val regions = gardenMap.regions()

  val minId = regions.values.min()
  val maxId = regions.values.max()

  val totalPrice =
    (minId..maxId).sumOf { id ->
      val points = regions.filter { it.value == id }.keys
      val area = points.size
      val perimeter = points.sumOf { p -> 4 - p.cardinalNeighbors.count { it in points } }
      area * perimeter
    }

  return totalPrice.toString()
}

// Part 2

fun part2(input: List<String>): String {
  return "TODO2"
}
