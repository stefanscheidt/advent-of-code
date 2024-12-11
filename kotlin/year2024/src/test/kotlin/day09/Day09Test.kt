package day09

import common.io.inputFile
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
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
  fun `solve part two with sample input`() {
    part1(sample) shouldBe "2858"
  }

  @Test
  fun `solve puzzle`() {
    val input = inputFile("day09.txt").readText().trimEnd()
    assertSoftly {
      part1(input) shouldBe "6471961544878"
      part2(input) shouldBe "ANSWER2"
    }
  }

}
