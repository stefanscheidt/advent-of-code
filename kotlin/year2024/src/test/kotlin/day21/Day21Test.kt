package day21

import common.io.inputFile
import common.io.readNonBlankLines
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day21Test {

  private val sample =
    """
    029A
    980A
    179A
    456A
    379A
    """
      .trimIndent()

  @Test
  fun `compute moves`() {
    moves("029A") shouldBe "<A^A>^^AvvvA"
    moves("101A") shouldBe "^<<A>vA^<A>>vA"
  }

  @Test
  fun `compute directions`() {
    directions("<^>") shouldBe "v<<A>^A>vA"
  }

  @Test
  fun `compute shortest sequence`() {
    shortestSequence("029A") shouldBe 68
    shortestSequence("379A") shouldBe 64
  }

  @Test
  fun `compute complexity`() {
    complexity("029A") shouldBe 68 * 29
  }

  @Test
  fun `solve part one with sample input`() {
    part1(sample.lines()) shouldBe "126384"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample.lines()) shouldBe "ANSWER2"
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day21.txt").readNonBlankLines()
    part1(input) shouldBe "ANSWER1"
  }

  @Test
  fun `solve part two`() {
    val input = inputFile("day21.txt").readNonBlankLines()
    part2(input) shouldBe "ANSWER2"
  }

}
