package day10

import common.io.inputFile
import common.io.readNonBlankLines
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day10Test {

  private val sample =
    """
    89010123
    78121874
    87430965
    96549874
    45678903
    32019012
    01329801
    10456732
    """
      .trimIndent()

  @Test
  fun `solve part one with sample input`() {
    part1(sample.lines()) shouldBe "36"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample.lines()) shouldBe "81"
  }

  @Test
  fun `solve puzzle`() {
    val input = inputFile("day10.txt").readNonBlankLines()
    assertSoftly {
      part1(input) shouldBe "822"
      part2(input) shouldBe "ANSWER2"
    }
  }
}
