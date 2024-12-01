package day01

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day01Test {

  private val sample =
    """
    3   4
    4   3
    2   5
    1   3
    3   9
    3   3
    """
      .trimIndent()

  @Test
  fun `solve part one with sample input`() {
    part1(sample) shouldBe "11"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample) shouldBe "31"
  }

  @Test
  fun `solve puzzle`() {
    val solution = solvePuzzle(file)
    solution.first shouldBe "3569916"
    solution.second shouldBe "26407426"
  }

}
