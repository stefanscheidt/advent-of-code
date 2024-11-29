package day09

import common.geom.p2
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day09Test {

  private val sample =
    """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """
      .trimIndent()

  @Test
  fun `tail follows head`() {
    p2(1, 1).follow(p2(1, 1)) shouldBe p2(1, 1)
    p2(1, 1).follow(p2(2, 1)) shouldBe p2(1, 1)
    p2(2, 1).follow(p2(1, 2)) shouldBe p2(2, 1)

    p2(1, 1).follow(p2(3, 1)) shouldBe p2(2, 1)

    p2(1, 1).follow(p2(2, 3)) shouldBe p2(2, 2)
  }

  @Test
  fun `solve part one with sample`() {
    val input = parseInput(sample.lines())

    solvePartOne(input) shouldBe 13
  }

  @Test
  fun `solve puzzle`() {
    val solution = solvePuzzle(file)

    solution.first shouldBe 6197
    solution.second shouldBe 2562
  }
}
