package day10

import common.io.inputFile
import common.io.readNonBlankLines
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day10Test {

  private val sample =
    """
    [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
    [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
    [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
    """
      .trimIndent()

  @Test
  fun `solve part one with sample input`() {
    part1(sample.lines()) shouldBe "7"
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day10.txt").readNonBlankLines()
    part1(input) shouldBe "466"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample.lines()) shouldBe "33"
  }

  @Test
  fun `solve part two`() {
    val input = inputFile("day10.txt").readNonBlankLines()
    part2(input) shouldBe "17214"
  }
}
