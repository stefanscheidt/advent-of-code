package day04

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day04Test {

  private val sample =
    """
        2-4,6-8
        2-3,4-5
        5-7,7-9
        2-8,3-7
        6-6,4-6
        2-6,4-8
    """
      .trimIndent()

  @Test
  fun `one fully contains other`() {
    oneFullyContainsOther(1..83, 1..84) shouldBe true
  }

  @Test
  fun `solve frist puzzle with sample`() {
    val ranges = parseInput(sample.lines())

    ranges.count { oneFullyContainsOther(it.first, it.second) } shouldBe 2
  }

  @Test
  fun `solve second puzzle with sample`() {
    val ranges = parseInput(sample.lines())

    ranges.count { overlap(it.first, it.second) } shouldBe 4
  }

  @Test
  fun `solve puzzle`() {
    val solution = solvePuzzle(file)

    solution.first shouldBe 503
    solution.second shouldBe 827
  }
}
