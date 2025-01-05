package day21

import common.io.inputFile
import common.io.readNonBlankLines
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

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

  @ParameterizedTest
  @CsvSource(
    "029A,<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A",
    "980A,<v<A>>^AAAvA^A<vA<AA>>^AvAA<^A>A<v<A>A>^AAAvA<^A>A<vA>^A<A>A",
    "179A,<v<A>>^A<vA<A>>^AAvAA<^A>A<v<A>>^AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A",
    "456A,<v<A>>^AA<vA<A>>^AAvAA<^A>A<vA>^A<A>A<vA>^A<A>A<v<A>A>^AAvA<^A>A",
    "379A,<v<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A",
  )
  fun `compute shortest sequence`(code: String, sequence: String) {
    shortestSequenceLength(code, 2) shouldBe sequence.length
  }

  @Test
  fun `compute complexity`() {
    complexity("029A", 2) shouldBe 68 * 29
  }

  @Test
  fun `solve part one with sample input`() {
    part1(sample.lines()) shouldBe "126384"
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day21.txt").readNonBlankLines()
    part1(input) shouldBe "152942"
  }

  @Test
  fun `solve part two`() {
    val input = inputFile("day21.txt").readNonBlankLines()
    part2(input) shouldBe "189235298434780"
  }
}
