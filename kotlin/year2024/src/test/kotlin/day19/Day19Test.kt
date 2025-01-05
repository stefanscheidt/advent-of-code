package day19

import common.io.inputFile
import common.io.readNonBlankLines
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day19Test {

  private val sample =
    """
    r, wr, b, g, bwu, rb, gb, br

    brwrr
    bggr
    gbbr
    rrbgbr
    ubwu
    bwurrg
    brgr
    bbrgwb
    """
      .trimIndent()

  @Test
  fun `check design`() {
    val patterns = "r, wr, b, g, bwu, rb, gb, br".split(", ")

    "brwrr".isPossibleArragement(patterns) shouldBe true
  }

  @Test
  fun `solve part one with sample input`() {
    part1(sample.lines()) shouldBe "6"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample.lines()) shouldBe "16"
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day19.txt").readNonBlankLines()
    part1(input) shouldBe "267"
  }

  @Test
  fun `solve part two`() {
    val input = inputFile("day19.txt").readNonBlankLines()
    part2(input) shouldBe "796449099271652"
  }
}
