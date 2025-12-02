package day02

import common.io.inputFile
import common.io.readNonBlankLines
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day02Test {

  private val sample =
    """
      11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124
    """
      .trimIndent()

  @Test
  fun `solve part one with sample input`() {
    part1(sample.lines()) shouldBe "1227775554"
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day02.txt").readNonBlankLines()
    part1(input) shouldBe "ANSWER1"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample.lines()) shouldBe "ANSWER2"
  }

  @Test
  fun `solve part two`() {
    val input = inputFile("day02.txt").readNonBlankLines()
    part2(input) shouldBe "ANSWER2"
  }

  @Test
  fun `invalid ids in range`() {
    invalidIds(11L, 22L) shouldBe listOf(11L, 22L)
    invalidIds(95L, 115L) shouldBe listOf(99L)
  }
}
