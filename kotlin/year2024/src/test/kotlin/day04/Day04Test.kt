package day04

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day04Test {

  private val sample =
    """
    MMMSXXMASM
    MSAMXMSMSA
    AMXSXMAAMM
    MSAMASMSMX
    XMASAMXAMM
    XXAMMXXAMA
    SMSMSASXSS
    SAXAMASAAA
    MAMMMXMMMM
    MXMXAXMASX
    """
      .trimIndent()

  @Test
  fun `solve part one with sample input`() {
    part1(sample.lines()) shouldBe "18"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample.lines()) shouldBe "9"
  }

  @Test
  fun `solve puzzle`() {
    val solution = solvePuzzle(file)
    solution.first shouldBe "2500"
    solution.second shouldBe "1933"
  }

}
