package day21

import common.geom.Point2D
import common.geom.cardinalNeighbors
import common.geom.minus
import common.geom.p2
import java.util.PriorityQueue

// Keypads

typealias Keypad = Map<Point2D, Char>

val numPad =
  mapOf(
    p2(0, 0) to '7',
    p2(1, 0) to '8',
    p2(2, 0) to '9',
    p2(0, 1) to '4',
    p2(1, 1) to '5',
    p2(2, 1) to '6',
    p2(0, 2) to '1',
    p2(1, 2) to '2',
    p2(2, 2) to '3',
    p2(1, 3) to '0',
    p2(2, 3) to 'A',
  )

val dirPad =
  mapOf(p2(1, 0) to '^', p2(2, 0) to 'A', p2(0, 1) to '<', p2(1, 1) to 'v', p2(2, 1) to '>')

fun Keypad.findPaths(start: Point2D, end: Point2D): List<String> {
  val queue = PriorityQueue<Pair<List<Point2D>, Int>>(compareBy { it.second })
  queue.add(listOf(start) to 0)
  val seen = mutableMapOf<Point2D, Int>()
  var finalCost: Int? = null
  val paths = mutableListOf<String>()

  while (queue.isNotEmpty()) {
    val (path, cost) = queue.poll()
    val current = path.last()
    if (finalCost != null && cost > finalCost) {
      return paths
    } else if (path.last() == end) {
      finalCost = cost
      val newPath =
        path
          .zipWithNext()
          .map { (from, to) ->
            when (val diff = to - from) {
              p2(1, 0) -> '>'
              p2(-1, 0) -> '<'
              p2(0, -1) -> '^'
              p2(0, 1) -> 'v'
              else -> error("Invalid direction: $diff")
            }
          }
          .joinToString("") + "A"
      paths.add(newPath)
    } else if (seen[current] == null || seen.getValue(current) >= cost) {
      seen[current] = cost
      current.cardinalNeighbors.filter { it in keys }.forEach { queue.add(path + it to cost + 1) }
    }
  }

  return paths
}

fun Keypad.allPaths(): Map<Pair<Char, Char>, List<String>> {
  val allStartsAndEnds = keys.flatMap { start -> keys.map { end -> start to end } }
  return allStartsAndEnds.associate { (start, end) ->
    (getValue(start) to getValue(end)) to findPaths(start, end)
  }
}

val allNumPaths = numPad.allPaths()

val allDirPaths = dirPad.allPaths()

fun shortestSequenceLength(
  code: String,
  indirections: Int,
  allPaths: Map<Pair<Char, Char>, List<String>> = allNumPaths,
  cache: MutableMap<Pair<String, Int>, Long> = mutableMapOf(),
): Long =
  cache.getOrPut(code to indirections) {
    "A$code".zipWithNext().sumOf { transition ->
      val paths = allPaths.getValue(transition)
      if (indirections == 0) {
        paths.minOf { it.length }.toLong()
      } else {
        paths.minOf { shortestSequenceLength(it, indirections - 1, allDirPaths, cache) }
      }
    }
  }

fun complexity(code: String, indirections: Int): Long =
  shortestSequenceLength(code, indirections) * code.substringBefore("A").toLong()

// Part 1

fun part1(input: List<String>): String = input.sumOf { code -> complexity(code, 2) }.toString()

// Part 2

fun part2(input: List<String>): String = input.sumOf { code -> complexity(code, 25) }.toString()
