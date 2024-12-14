package day14

import common.geom.Point2D
import common.geom.p2
import common.geom.plus
import common.geom.times

// Robot

data class Robot(
  val position: Point2D,
  val velocity: Point2D
) {
  fun move(times: Int = 1, width: Int = 101, hight: Int = 103): Robot {
    val p = position + times * velocity
    val x = p.x.mod(width)
    val y = p.y.mod(hight)
    return copy(position = p2(x, y))
  }
}

fun String.toRobot(): Robot {
  val (p, v) = split(" ")
  val position = p.substringAfter("p=").split(",").map(String::toInt).let { (x, y) -> p2(x, y) }
  val velocity = v.substringAfter("v=").split(",").map(String::toInt).let { (x, y) -> p2(x, y) }
  return Robot(position, velocity)
}

// Part 1

fun part1(input: List<String>, width: Int = 101, hight: Int = 103): String {
  val robots = input.map(String::toRobot)
    .map { it.move(times = 100, width = width, hight = hight) }
  val topLeft = Pair(0..<width / 2, 0..<hight / 2)
  val topRight = Pair(width / 2 + 1..<width, 0..<hight / 2)
  val bottomLeft = Pair(0..<width / 2, hight / 2 + 1..<hight)
  val bottomRight = Pair(width / 2 + 1..<width, hight / 2 + 1..<hight)

  val safetyFactor = listOf(topLeft, topRight, bottomLeft, bottomRight)
    .map { (xs, ys) ->
      robots.count { robot -> robot.position.x in xs && robot.position.y in ys }
    }
    .fold(1) { acc, count -> acc * count }

  return safetyFactor.toString()
}

// Part 2

fun part2(input: List<String>): String {
  return "TODO"
}

