package day08

import common.geom.Point2D
import common.geom.minus
import common.geom.p2
import common.geom.plus

// Antennas

val antennaRegex = Regex("""([^.])""")

// Grid

typealias Grid = List<String>

fun Grid.contains(p: Point2D): Boolean =
  (p.y in indices) && (p.x in this[p.y].indices)

val Grid.antennas: Map<String, List<Point2D>>
  get() =
    flatMapIndexed { y, row ->
      antennaRegex.findAll(row).map { match -> match.value to p2(match.range.first, y) }
    }
      .groupBy(keySelector = { it.first }, valueTransform = { it.second })

// Part 1

fun part1(input: List<String>): String {
  val antinodes = input.antennas
    .flatMap { (_, positions) ->
      allPairs(positions)
        .flatMap { (fst, snd) -> listOf(snd + (snd - fst), fst + (fst - snd)) }
    }
    .toSet()
    .filter { input.contains(it) }

  return antinodes.size.toString()
}

// Part 2

fun part2(input: List<String>): String {
  val antennas = input.antennas
  val antinodes = antennas
    .flatMap { (_, positions) ->
      allPairs(positions)
        .flatMap { (fst, snd) ->
          val an1 = generateSequence(snd + (snd - fst)) { it + (snd - fst) }
            .takeWhile { input.contains(it) }
            .toList()
          val an2 = generateSequence(fst + (fst - snd)) { it + (fst - snd) }
            .takeWhile { input.contains(it) }
            .toList()
          an1 + an2
        }
    }
    .toSet()

  return antinodes.size.toString()
}

// Utils

fun <T> allPairs(list: List<T>): List<Pair<T, T>> {
  return list.flatMapIndexed { index, fst -> list.drop(index + 1).map { snd -> fst to snd } }
}
