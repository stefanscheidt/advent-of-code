package day03

import common.io.inputFile
import common.io.readNonBlankLines
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day03Test {

  private val sample =
    """
      987654321111111
      811111111111119
      234234234234278
      818181911112111
    """
      .trimIndent()

  @Test
  fun `solve part one with sample input`() {
    part1(sample.lines()) shouldBe "357"
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day03.txt").readNonBlankLines()
    part1(input) shouldBe "17330"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample.lines()) shouldBe "3121910778619"
  }

  @Test
  fun `solve part two`() {
    val input = inputFile("day03.txt").readNonBlankLines()
    part2(input) shouldBe "171518260283767"
  }

  @ParameterizedTest
  @CsvSource(value = ["987654321111111,98", "811111111111119,89"])
  fun `compute max joltage for part one`(bank: String, maxJoltage: Int) {
    joltage(bank, 2) shouldBe maxJoltage
  }

  @ParameterizedTest
  @CsvSource(
    value =
      [
        "987654321111111,12,987654321111",
        "811111111111119,12,811111111119",
        "234234234234278,12,434234234278",
        "818181911112111,12,888911112111",
      ]
  )
  fun `compute joltage for given bank and battery count`(
    bank: String,
    batteries: Int,
    expected: Long,
  ) {
    joltage(bank, batteries) shouldBe expected
  }
}
