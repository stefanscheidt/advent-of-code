package day05

import common.io.inputFile
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day05Test {

  private val sample =
    """
      3-5
      10-14
      16-20
      12-18

      1
      5
      8
      11
      17
      32
    """
      .trimIndent()

  @Test
  fun `solve part one with sample input`() {
    part1(sample) shouldBe "3"
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day05.txt").readText()
    part1(input) shouldBe "756"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample) shouldBe "14"
  }

  @Test
  fun `solve part two`() {
    val input = inputFile("day05.txt").readText()
    part2(input) shouldBe "355555479253787"
  }
}
