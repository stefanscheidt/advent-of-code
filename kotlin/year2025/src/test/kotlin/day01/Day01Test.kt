package day01

import common.io.inputFile
import common.io.readNonBlankLines
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day01Test {

  private val sample =
    """
      L68
      L30
      R48
      L5
      R60
      L55
      L1
      L99
      R14
      L82
    """
      .trimIndent()

  @Test
  fun `solve part one with sample input`() {
    part1(sample.lines()) shouldBe "3"
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day01.txt").readNonBlankLines()
    part1(input) shouldBe "999"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample.lines()) shouldBe "6"
  }

  @Test
  fun `solve part two`() {
    val input = inputFile("day01.txt").readNonBlankLines()
    part2(input) shouldBe "6099"
  }

  @ParameterizedTest
  @CsvSource(value = ["50,-68,82,1", "82,-30,52,0", "52,48,0,1", "0,-5,95,0"])
  fun `update position and count visits of zero`(
    position: Int,
    update: Int,
    newPosition: Int,
    visits: Int,
  ) {
    updatePosition(position, update) shouldBe Pair(newPosition, visits)
  }
}
