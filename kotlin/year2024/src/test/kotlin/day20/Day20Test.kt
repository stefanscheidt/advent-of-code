package day20

import common.geom.Point2D
import common.io.inputFile
import common.io.readNonBlankLines
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day20Test {

  private val sample =
    """
    ###############
    #...#...#.....#
    #.#.#.#.#.###.#
    #S#...#.#.#...#
    #######.#.#.###
    #######.#.#...#
    #######.#.###.#
    ###..E#...#...#
    ###.#######.###
    #...###...#...#
    #.#####.#.###.#
    #.#...#.#.#...#
    #.#.#.#.#.#.###
    #...#...#...###
    ###############
    """
      .trimIndent()

  @Test
  fun `compute shortest path length`() {
    val racetrack = sample.lines().toRacetrack()
    val start = racetrack.findChar('S') ?: error("Start not found")
    val end = racetrack.findChar('E') ?: error("End not found")
    racetrack.shortestPathLength(start, end) shouldBe 84
  }

  @Test
  fun `solve part one with sample input`() {
    val racetrack = sample.lines().toRacetrack()

    savingsToString(racetrack.savings()) shouldBe """
      There are 14 cheats that save 2 picoseconds.
      There are 14 cheats that save 4 picoseconds.
      There are 2 cheats that save 6 picoseconds.
      There are 4 cheats that save 8 picoseconds.
      There are 2 cheats that save 10 picoseconds.
      There are 3 cheats that save 12 picoseconds.
      There is one cheat that saves 20 picoseconds.
      There is one cheat that saves 36 picoseconds.
      There is one cheat that saves 38 picoseconds.
      There is one cheat that saves 40 picoseconds.
      There is one cheat that saves 64 picoseconds.
    """.trimIndent()
  }

  @Test
  fun `solve part two with sample input`() {
    val racetrack = sample.lines().toRacetrack()

    savingsToString(racetrack.savings()) shouldBe """
      There are 32 cheats that save 50 picoseconds.
      There are 31 cheats that save 52 picoseconds.
      There are 29 cheats that save 54 picoseconds.
      There are 39 cheats that save 56 picoseconds.
      There are 25 cheats that save 58 picoseconds.
      There are 23 cheats that save 60 picoseconds.
      There are 20 cheats that save 62 picoseconds.
      There are 19 cheats that save 64 picoseconds.
      There are 12 cheats that save 66 picoseconds.
      There are 14 cheats that save 68 picoseconds.
      There are 12 cheats that save 70 picoseconds.
      There are 22 cheats that save 72 picoseconds.
      There are 4 cheats that save 74 picoseconds.
      There are 3 cheats that save 76 picoseconds.
    """.trimIndent()
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day20.txt").readNonBlankLines()
    part1(input) shouldBe "1499"
  }

  @Test
  fun `solve part two`() {
    val input = inputFile("day20.txt").readNonBlankLines()
    part2(input) shouldBe "ANSWER2"
  }

}

private fun savingToString(saving: Int, cheats: List<Point2D>): String =
  if (cheats.size == 1) "There is one cheat that saves $saving picoseconds."
  else "There are ${cheats.size} cheats that save $saving picoseconds."

private fun savingsToString(savings: Map<Int, List<Point2D>>): String = savings
  .toSortedMap()
  .map { (saving, cheats) -> savingToString(saving, cheats) }
  .joinToString(System.lineSeparator())
