package day08

import common.geom.Point2D
import common.geom.minus
import common.geom.p2
import common.geom.plus

// Antennas

val antennaRegex = Regex("""([^.])""")

// Grid

typealias Grid = List<String>

fun Grid.contains(p: Point2D): Boolean = (p.y in indices) && (p.x in this[p.y].indices)

val Grid.antennas: Map<String, List<Point2D>>
  get() =
    flatMapIndexed { y, row ->
        antennaRegex.findAll(row).map { match -> match.value to p2(match.range.first, y) }
      }
      .groupBy(keySelector = { it.first }, valueTransform = { it.second })

fun Grid.antinodes(generator: (Pair<Point2D, Point2D>) -> List<Point2D>): List<Point2D> =
  antennas
    .flatMap { (_, positions) -> allPairs(positions).flatMap(generator) }
    .toSet()
    .filter { contains(it) }

// Part 1

fun part1(input: List<String>): String =
  input
    .antinodes { (fst, snd) -> listOf(snd + (snd - fst), fst + (fst - snd)) }
    .also(::println)
    .size
    .toString()

// Part 2

fun part2(input: List<String>): String =
  input
    .antinodes { (fst, snd) ->
      val an1 = generateSequence(fst) { it + (fst - snd) }.takeWhile { input.contains(it) }.toList()
      val an2 = generateSequence(snd) { it + (snd - fst) }.takeWhile { input.contains(it) }.toList()
      an1 + an2
    }
    .size
    .toString()

// Utils

fun <T> allPairs(list: List<T>): List<Pair<T, T>> {
  return list.flatMapIndexed { index, fst -> list.drop(index + 1).map { snd -> fst to snd } }
}
