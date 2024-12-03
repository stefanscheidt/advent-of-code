package day02

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day02Test {

  private val sample =
    """
    7 6 4 2 1
    1 2 7 8 9
    9 7 6 2 1
    1 3 2 4 5
    8 6 4 4 1
    1 3 6 7 9
    """
      .trimIndent()

  @Test
  fun `solve part one with sample input`() {
    part1(sample.lines()) shouldBe "2"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample.lines()) shouldBe "4"
  }

  @Test
  fun `damped reports`() {
    val report: Report = listOf(7, 6, 4, 2, 1)
    report.dampedReports() shouldBe
      listOf(
        listOf(6, 4, 2, 1),
        listOf(7, 4, 2, 1),
        listOf(7, 6, 2, 1),
        listOf(7, 6, 4, 1),
        listOf(7, 6, 4, 2),
      )
  }

  @Test
  fun `solve puzzle`() {
    val solution = solvePuzzle(file)
    solution.first shouldBe "564"
    solution.second shouldBe "604"
  }
}
