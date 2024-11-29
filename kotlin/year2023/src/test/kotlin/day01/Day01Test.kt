package day01

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day01Test {

  private val sample1 =
    """
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet
    """
      .trimIndent()

  private val sample2 =
    """
        two1nine
        eightwothree
        abcone2threexyz
        xtwone3four
        4nineeightseven2
        zoneight234
        7pqrstsixteen
    """
      .trimIndent()

  @Test
  fun `solve part one with sample input`() {
    calibrationValue(sample1.lines()) shouldBe 142
  }

  @Test
  fun `replace number words`() {
    calibrationValue(replaceNumerals(normalizeNumerals("eightwo"))) shouldBe 82
  }

  @Test
  fun `solve part two with sample input`() {
    calibrationValue(sample2.lines().map(::normalizeNumerals).map(::replaceNumerals)) shouldBe 281
  }

  @Test
  fun `solve puzzle`() {
    val solution = solvePuzzle(file)

    solution.first shouldBe 54877
    solution.second shouldBe 54100
  }
}
