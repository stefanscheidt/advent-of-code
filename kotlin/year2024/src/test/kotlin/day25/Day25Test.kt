package day25

import common.io.inputFile
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day25Test {

  private val sample =
    """
    #####
    .####
    .####
    .####
    .#.#.
    .#...
    .....

    #####
    ##.##
    .#.##
    ...##
    ...#.
    ...#.
    .....

    .....
    #....
    #....
    #...#
    #.#.#
    #.###
    #####

    .....
    .....
    #.#..
    ###..
    ###.#
    ###.#
    #####

    .....
    .....
    .....
    #....
    #.#..
    #.#.#
    #####
    """
      .trimIndent()

  @Test
  fun `solve part one with sample input`() {
    part1(sample) shouldBe "3"
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day25.txt").readText().trimEnd()
    part1(input) shouldBe "2824"
  }
}
