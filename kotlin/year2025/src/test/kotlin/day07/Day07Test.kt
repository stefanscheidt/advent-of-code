package day07

import common.io.inputFile
import common.io.readNonBlankLines
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day07Test {

  private val sample =
    """
      .......S.......
      ...............
      .......^.......
      ...............
      ......^.^......
      ...............
      .....^.^.^.....
      ...............
      ....^.^...^....
      ...............
      ...^.^...^.^...
      ...............
      ..^...^.....^..
      ...............
      .^.^.^.^.^...^.
      ...............
    """
      .trimIndent()

  @Test
  fun `solve part one with sample input`() {
    part1(sample.lines()) shouldBe "21"
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day07.txt").readNonBlankLines()
    part1(input) shouldBe "1490"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample.lines()) shouldBe "40"
  }

  @Test
  fun `solve part two`() {
    val input = inputFile("day07.txt").readNonBlankLines()
    part2(input) shouldBe "3806264447357"
  }
}
