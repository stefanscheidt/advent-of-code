package day12

import common.io.inputFile
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day12Test {

  private val sample =
    """
      0:
      ###
      ##.
      ##.

      1:
      ###
      ##.
      .##

      2:
      .##
      ###
      ##.

      3:
      ##.
      ###
      ##.

      4:
      ###
      #..
      ###

      5:
      ###
      .#.
      ###

      4x4: 0 0 0 0 2 0
      12x5: 1 0 1 0 2 2
      12x5: 1 0 1 0 3 2
    """
      .trimIndent()

  @Test
  fun `solve part one with sample input`() {
    part1(sample) shouldBe "3" // "2"
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day12.txt").readText().trimEnd()
    part1(input) shouldBe "512"
  }
}
