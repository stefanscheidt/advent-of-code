package day11

import common.io.inputFile
import common.io.readNonBlankLines
import io.kotest.matchers.shouldBe
import io.kotest.assertions.assertSoftly
import org.junit.jupiter.api.Test

class Day11Test {

  private val sample =
    """
    125 17
    """
      .trimIndent()

  @Test
  fun `solve part one with sample input`() {
    part1(sample.lines()) shouldBe "55312"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample.lines()) shouldBe "65601038650482"
  }

  @Test
  fun `solve puzzle`() {
    val input = inputFile("day11.txt").readNonBlankLines()
    assertSoftly {
      part1(input) shouldBe "203953"
      part2(input) shouldBe "242090118578155"
    }
  }

}
