package day09

import common.io.inputFile
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class Day09Test {

  private val sample =
    """
    2333133121414131402
    """
      .trimIndent()

  @Test
  fun `solve part one with sample input`() {
    part1(sample) shouldBe "1928"
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day09.txt").readText().trimEnd()
    part1(input) shouldBe "6471961544878"
  }

  @Test
  @Disabled
  fun `solve part two with sample input`() {
    part1(sample) shouldBe "2858"
  }

  @Test
  @Disabled
  fun `solve part two`() {
    val input = inputFile("day09.txt").readText().trimEnd()
    part2(input) shouldBe "ANSWER2"
  }
}
