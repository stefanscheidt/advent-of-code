package day03

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day03Test {

  private val sample =
    """
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw
    """
      .trimIndent()

  @Test
  fun `eval items`() {
    "vJrwpWtwJgWrhcsFMMfFFhFp".priority() shouldBe 16

    sample.lines().sumOf(Inventory::priority) shouldBe 157
  }

  @Test
  fun `solve puzzle`() {
    val solution = solvePuzzle(file)

    solution.first shouldBe 7428
    solution.second shouldBe 2650
  }
}
