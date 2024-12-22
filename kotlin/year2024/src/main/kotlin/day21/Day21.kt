package day21

import common.geom.minus
import common.geom.p2
import common.geom.plus

// Moves

val numKeys = mapOf(
  '0' to p2(-1, 0),
  'A' to p2(0, 0),
  '1' to p2(-2, 1),
  '2' to p2(-1, 1),
  '3' to p2(0, 1),
  '4' to p2(-2, 2),
  '5' to p2(-1, 2),
  '6' to p2(0, 2),
  '7' to p2(-2, 3),
  '8' to p2(-1, 3),
  '9' to p2(0, 3),
)

val dirKeys = mapOf(
  '<' to p2(-2, 0),
  'v' to p2(-1, 0),
  '>' to p2(0, 0),
  '^' to p2(-1, 1),
  'A' to p2(0, 1),
)

fun moves(code: String): String =
  "A$code"
    .zipWithNext()
    .map { (from, to) ->
      val pFrom = numKeys[from]!!
      val pTo = numKeys[to]!!
      pTo - pFrom
    }
    .joinToString("") { (dx, dy) ->
      buildString {
        if (dx > 0) append(">".repeat(dx))
        if (dy > 0) append("^".repeat(dy))
        if (dx < 0) append("<".repeat(-dx))
        if (dy < 0) append("v".repeat(-dy))
        append("A")
      }
    }

fun directions(moves: String): String =
  "A$moves"
    .zipWithNext()
    .map { (from, to) ->
      val pFrom = dirKeys[from]!!
      val pTo = dirKeys[to]!!
      (pTo - pFrom)
    }
    .joinToString("") { (dx, dy) ->
      buildString {
        if (dx > 0) append(">".repeat(dx))
        if (dy > 0) append("^".repeat(dy))
        if (dy < 0) append("v".repeat(-dy))
        if (dx < 0) append("<".repeat(-dx))
        append("A")
      }
    }

fun shortestSequence(code: String): Int =
  moves(code)
    .let(::directions)
    .let(::directions)
    .length

fun complexity(code: String): Int =
  shortestSequence(code) * code.substringBefore("A").toInt()

// Part 1

fun part1(input: List<String>): String {
  return input.sumOf(::complexity).toString()
}

// Part 2

fun part2(input: List<String>): String {
  return "TODO2"
}

// Debugging

val directions = mapOf(
  '<' to p2(-1, 0),
  '>' to p2(1, 0),
  '^' to p2(0, 1),
  'v' to p2(0, -1),
  'A' to p2(0, 0),
)

val numPos = numKeys.map { (numKey, position) -> position to numKey }.toMap()
val dirPos = dirKeys.map { (dirKey, position) -> position to dirKey }.toMap()

fun moveOnNumPad(moves: String): String =
  moves.fold(Pair(numKeys['A']!!, "")) { (pos, result), move ->
    if (move == 'A') {
      Pair(pos, result + numPos[pos]!!)
    } else {
      Pair(pos + directions[move]!!, result)
    }
  }.second

fun moveOnDirPad(moves: String): String =
  moves.fold(Pair(dirKeys['A']!!, "")) { (pos, result), move ->
    if (move == 'A') {
      Pair(pos, result + dirPos[pos]!!)
    } else {
      Pair(pos + directions[move]!!, result)
    }
  }.second

fun main() {
  "<v<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A"
    .also { println(it) }
    .let(::moveOnDirPad)
    .also { println(it) }
    .let(::moveOnDirPad)
    .also { println(it) }
    .let(::moveOnNumPad)
    .let(::println)

  moves("379A")
    .also { println(it) }
    .let(::directions)
    .also { println(it) }
    .let(::directions)
    .also { println(it) }
    .let(::moveOnDirPad)
    .also { println(it) }
    .let(::moveOnDirPad)
    .also { println(it) }
    .let(::moveOnNumPad)
    .let(::println)

}
