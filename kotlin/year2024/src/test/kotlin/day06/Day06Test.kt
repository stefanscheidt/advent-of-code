package day06

import common.io.inputFile
import common.io.readNonBlankLines
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day06Test {

  private val sample =
    """
    ....#.....
    .........#
    ..........
    ..#.......
    .......#..
    ..........
    .#..^.....
    ........#.
    #.........
    ......#...
    """
      .trimIndent()
      .lines()

  @Test
  fun `solve part one with sample input`() {
    part1(sample) shouldBe "41"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample) shouldBe "6"
  }

  @Test
  fun `solve puzzle`() {
    val input = inputFile("day06.txt").readNonBlankLines()
    assertSoftly {
      part1(input) shouldBe "5534"
      part2(input) shouldBe "2262"
    }
  }

}
