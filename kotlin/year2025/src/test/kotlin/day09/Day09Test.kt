package day09

import common.io.inputFile
import common.io.readNonBlankLines
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day09Test {

  private val sample =
    """
      7,1
      11,1
      11,7
      9,7
      9,5
      2,5
      2,3
      7,3
    """
      .trimIndent()

  @Test
  fun `solve part one with sample input`() {
    part1(sample.lines()) shouldBe "50"
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day09.txt").readNonBlankLines()
    part1(input) shouldBe "4777816465"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample.lines()) shouldBe "24"
  }

  @Test
  fun `solve part two`() {
    val input = inputFile("day09.txt").readNonBlankLines()
    part2(input) shouldBe "1410501884"
  }
}
