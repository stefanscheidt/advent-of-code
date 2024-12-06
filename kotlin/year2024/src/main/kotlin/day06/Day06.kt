package day06

import common.geom.Point2D
import common.geom.p2
import common.geom.plus
import day06.Direction.EAST
import day06.Direction.NORTH
import day06.Direction.SOUTH
import day06.Direction.WEST


// Direction

enum class Direction(val vec: Point2D) {
  NORTH(p2(0, -1)),
  EAST(p2(1, 0)),
  SOUTH(p2(0, 1)),
  WEST(p2(-1, 0));

  fun turnRight(): Direction =
    when (this) {
      NORTH -> EAST
      EAST -> SOUTH
      SOUTH -> WEST
      WEST -> NORTH
    }
}

fun Char.toDirection(): Direction? =
  when (this) {
    '^' -> NORTH
    '>' -> EAST
    'v' -> SOUTH
    '<' -> WEST
    else -> null
  }

// Map

typealias Map = List<CharArray>

operator fun Map.get(pos: Point2D): Char? =
  getOrNull(pos.y)?.getOrNull(pos.x)

operator fun Map.set(pos: Point2D, value: Char) {
  this[pos.y][pos.x] = value
}

// Guard

data class Guard(val pos: Point2D, val direction: Direction) {

  fun move(map: Map): Guard {
    val nextPos = pos + direction.vec
    return if (map[nextPos] == '#') copy(direction = direction.turnRight()) else copy(pos = nextPos)
  }

}

val Set<Guard>.positions: Set<Point2D>
  get() = map(Guard::pos).toSet()

fun Map.findGuard(): Guard {
  forEachIndexed { y, row ->
    row.forEachIndexed { x, symbol ->
      val direction = symbol.toDirection()
      if (direction != null) return Guard(p2(x, y), direction)
    }
  }
  error("Guard not found")
}

// Traverse

data class Trace(val positions: Set<Point2D>, val isLoop: Boolean)

fun Map.traverse(guard: Guard = findGuard()): Trace {
  val seen = mutableSetOf<Guard>()

  var current = guard
  while (current !in seen && this[current.pos] != null) {
    seen += current
    current = current.move(map = this)
  }

  return Trace(seen.positions, this[current.pos] != null)
}

// Part 1

fun part1(input: List<String>): String =
  input.map { it.toCharArray() }.traverse().positions.size.toString()

// Part 2

fun <T> Map.withObstacleAt(pos: Point2D, fn: (Map) -> T): T {
  val original = this[pos]
  if (original != null) this[pos] = '#'
  val result = fn(this)
  if (original != null) this[pos] = original
  return result
}

fun part2(input: List<String>): String {
  val map = input.map { it.toCharArray() }
  val guard = map.findGuard()
  val candidates = map
    .traverse(guard)
    .positions
    .filterNot { it == guard.pos }
  return candidates
    .count { pos -> map.withObstacleAt(pos) { it.traverse(guard).isLoop } }
    .toString()
}
