package day12

import common.io.inputFile
import common.io.readNonBlankLines
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day12Test {

  private val sample =
    """
    RRRRIICCFF
    RRRRIICCCF
    VVRRRCCFFF
    VVRCCCJFFF
    VVVVCJJCFE
    VVIVCCJJEE
    VVIIICJJEE
    MIIIIIJJEE
    MIIISIJEEE
    MMMISSJEEE
    """
      .trimIndent()

  @Test
  fun `solve part one with sample input`() {
    part1(sample.lines()) shouldBe "1930"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample.lines()) shouldBe "1206"
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day12.txt").readNonBlankLines()
    part1(input) shouldBe "1381056"
  }

  @Test
  fun `solve part two`() {
    val input = inputFile("day12.txt").readNonBlankLines()
    part2(input) shouldBe "ANSWER2"
  }

}
