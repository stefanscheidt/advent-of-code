package day04

import common.geom.Point2D
import common.geom.plus

private fun parseInput(input: List<String>): Set<Point2D> = buildSet {
  input.forEachIndexed { y, line ->
    line.forEachIndexed { x, char -> if (char == '@') this.add(Point2D(x, y)) }
  }
}

// Part 1

fun part1(input: List<String>): String = parseInput(input).removableRolls().size.toString()

// Part 2

fun part2(input: List<String>): String {
  val rolls = parseInput(input).toMutableSet()

  var removableRolls = rolls.removableRolls()
  var count = removableRolls.size
  while (removableRolls.isNotEmpty()) {
    rolls.removeAll(removableRolls.toSet())
    removableRolls = rolls.removableRolls()
    count += removableRolls.size
  }

  return count.toString()
}

// Common

fun Set<Point2D>.removableRolls(): List<Point2D> = filter { candidate ->
  candidate.neighbours.count { it in this } < 4
}

val Point2D.neighbours: Set<Point2D>
  get() =
    setOf(
      this + Point2D(-1, -1),
      this + Point2D(0, -1),
      this + Point2D(1, -1),
      this + Point2D(-1, 0),
      this + Point2D(1, 0),
      this + Point2D(-1, 1),
      this + Point2D(0, 1),
      this + Point2D(1, 1),
    )
