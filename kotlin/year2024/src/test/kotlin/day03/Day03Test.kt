package day03

import common.io.inputFile
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day03Test {

  private val sample1 =
    """
    xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))
    """
      .trimIndent()

  private val sample2 =
    """
    xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))
    """
      .trimIndent()

  @Test
  fun `solve part one with sample input`() {
    part1(sample1) shouldBe "161"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample2) shouldBe "48"
  }

  @Test
  fun `solve puzzle`() {
    val input = inputFile("day03.txt").readText()
    assertSoftly {
      part1(input) shouldBe "155955228"
      part2(input) shouldBe "100189366"
    }
  }
}
