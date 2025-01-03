package day15

import common.geom.Point2D
import common.geom.p2
import common.geom.plus

// Directions

fun Char.toDirection(): Point2D? =
  when (this) {
    '^' -> p2(0, -1)
    '>' -> p2(1, 0)
    'v' -> p2(0, 1)
    '<' -> p2(-1, 0)
    else -> null
  }

// Warehouse

data class Warehouse(val walls: Set<Point2D>, val boxes: MutableSet<Point2D>, var robot: Point2D) {
  fun moveRobot(direction: Point2D) {
    val next = robot + direction

    if (next in walls) {
      return
    }

    if (next !in boxes) {
      robot = next
      return
    }

    when (val behindBoxes = behindBoxes(robot, direction)) {
      is Wall -> {
        return
      }
      is Free -> {
        boxes.remove(next)
        boxes.add(behindBoxes.position)
        robot = next
        return
      }
    }
  }

  private tailrec fun behindBoxes(position: Point2D, direction: Point2D): BehindBox =
    when (val next = position + direction) {
      in walls -> Wall(next)
      !in boxes -> Free(next)
      else -> behindBoxes(position + direction, direction)
    }
}

sealed interface BehindBox {
  val position: Point2D
}

data class Wall(override val position: Point2D) : BehindBox

data class Free(override val position: Point2D) : BehindBox

fun String.toWarehouse(): Warehouse? {
  val walls = mutableSetOf<Point2D>()
  val boxes = mutableSetOf<Point2D>()
  var robot: Point2D? = null
  this.lines().forEachIndexed { y, line ->
    line.forEachIndexed { x, c ->
      when (c) {
        '#' -> walls.add(p2(x, y))
        'O' -> boxes.add(p2(x, y))
        '@' -> robot = p2(x, y)
      }
    }
  }
  return if (robot != null) Warehouse(walls, boxes, robot!!) else null
}

// Part 1

fun part1(input: String): String {
  val (w, d) = input.split("\n\n")
  val warehouse = w.toWarehouse() ?: error("Invalid warehouse map")
  val directions = d.lines().joinToString(separator = "").mapNotNull(Char::toDirection)
  directions.forEach { warehouse.moveRobot(it) }
  return warehouse.boxes.sumOf { it.x + 100 * it.y }.toString()
}

// Part 2

fun part2(input: String): String {
  return "TODO2"
}
