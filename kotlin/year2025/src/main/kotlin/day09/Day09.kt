package day09

import common.geom.Point2D
import common.geom.minus
import common.geom.p2
import common.geom.plus
import kotlin.math.max
import kotlin.math.min

// Part 1

fun part1(input: List<String>): String {
  val points = input.map(::parseLine)
  val rectangles = allRectanglesOf(points)
  return rectangles.maxOf(Rectangle::area).toString()
}

// Part 2

fun part2(input: List<String>): String {
  val points = input.map(::parseLine)
  val connections = (points + points.first()).zipWithNext()
  val rectangles = allRectanglesOf(points).sortedByDescending(Rectangle::area)
  val biggest =
    rectangles.first { rectangle ->
      val inner = rectangle.inner()
      connections.none { connection -> connection.crosses(inner) }
    }

  return biggest.area.toString()
}

// Helper

fun parseLine(line: String): Point2D {
  val (x, y) = line.split(",").map(String::toInt)
  return p2(x, y)
}

data class Rectangle(val topLeft: Point2D, val bottomRight: Point2D) {

  val area: Long

  init {
    val width = (bottomRight.x - topLeft.x + 1).toLong()
    val height = (bottomRight.y - topLeft.y + 1).toLong()
    area = width * height
  }

  fun inner(): Rectangle = Rectangle(topLeft + p2(1, 1), bottomRight - p2(1, 1))

  fun overlaps(other: Rectangle): Boolean =
    topLeft.x <= other.bottomRight.x &&
      bottomRight.x >= other.topLeft.x &&
      topLeft.y <= other.bottomRight.y &&
      bottomRight.y >= other.topLeft.y
}

fun rectangleOf(p1: Point2D, p2: Point2D): Rectangle =
  Rectangle(
    topLeft = Point2D(x = min(p1.x, p2.x), y = min(p1.y, p2.y)),
    bottomRight = Point2D(x = max(p1.x, p2.x), max(p1.y, p2.y)),
  )

fun allRectanglesOf(points: List<Point2D>): List<Rectangle> = buildList {
  for (i in points.indices) {
    for (j in i + 1 until points.size) {
      add(rectangleOf(points[i], points[j]))
    }
  }
}

typealias Connection = Pair<Point2D, Point2D>

fun Connection.crosses(rectangle: Rectangle): Boolean =
  rectangleOf(first, second).overlaps(rectangle)
