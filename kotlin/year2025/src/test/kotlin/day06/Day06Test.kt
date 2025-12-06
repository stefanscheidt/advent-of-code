package day06

import common.io.inputFile
import common.io.readNonBlankLines
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day06Test {

  private val sample =
    """
      123 328  51 64 
       45 64  387 23 
        6 98  215 314
      *   +   *   +  
    """
      .trimIndent()

  @Test
  fun `solve part one with sample input`() {
    part1(sample.lines()) shouldBe "4277556"
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day06.txt").readNonBlankLines()
    part1(input) shouldBe "8108520669952"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample.lines()) shouldBe "3263827"
  }

  @Test
  fun `solve part two`() {
    val input = inputFile("day06.txt").readNonBlankLines()
    part2(input) shouldBe "11708563470209"
  }
}
