package day05

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day05Test {

  private val sample =
    """
    47|53
    97|13
    97|61
    97|47
    75|29
    61|13
    75|53
    29|13
    97|29
    53|29
    61|53
    97|53
    61|29
    47|13
    75|47
    97|75
    47|61
    75|61
    47|29
    75|13
    53|13

    75,47,61,53,29
    97,61,53,29,13
    75,29,13
    75,97,47,61,53
    61,13,29
    97,13,75,29,47
    """
      .trimIndent()

  @Test
  fun `solve part one with sample input`() {
    part1(sample) shouldBe "143"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample) shouldBe "123"
  }

  @Test
  fun `solve puzzle`() {
    val solution = solvePuzzle(file)
    solution.first shouldBe "4609"
    solution.second shouldBe "5723"
  }
}
