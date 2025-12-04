package day04

import common.io.inputFile
import common.io.readNonBlankLines
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day04Test {

  private val sample =
    """
      ..@@.@@@@.
      @@@.@.@.@@
      @@@@@.@.@@
      @.@@@@..@.
      @@.@@@@.@@
      .@@@@@@@.@
      .@.@.@.@@@
      @.@@@.@@@@
      .@@@@@@@@.
      @.@.@@@.@.
    """
      .trimIndent()

  @Test
  fun `solve part one with sample input`() {
    part1(sample.lines()) shouldBe "13"
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day04.txt").readNonBlankLines()
    part1(input) shouldBe "1467"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample.lines()) shouldBe "43"
  }

  @Test
  fun `solve part two`() {
    val input = inputFile("day04.txt").readNonBlankLines()
    part2(input) shouldBe "8484"
  }
}
