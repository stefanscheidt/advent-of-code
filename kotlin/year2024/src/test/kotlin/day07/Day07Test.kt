package day07

import common.io.inputFile
import common.io.readNonBlankLines
import day07.Operation.ADD
import day07.Operation.MULT
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day07Test {

  private val sample =
    """
    190: 10 19
    3267: 81 40 27
    83: 17 5
    156: 15 6
    7290: 6 8 6 15
    161011: 16 10 13
    192: 17 8 14
    21037: 9 7 18 13
    292: 11 6 16 20
    """
      .trimIndent()

  @Test
  fun `any combination of ops`() {
    anyCombinationOf(listOf(ADD, MULT), 3) shouldContainExactlyInAnyOrder
      listOf(
        listOf(ADD, ADD, ADD),
        listOf(ADD, ADD, MULT),
        listOf(ADD, MULT, ADD),
        listOf(ADD, MULT, MULT),
        listOf(MULT, ADD, ADD),
        listOf(MULT, ADD, MULT),
        listOf(MULT, MULT, ADD),
        listOf(MULT, MULT, MULT),
      )
  }

  @Test
  fun `solve part one with sample input`() {
    part1(sample.lines()) shouldBe "3749"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample.lines()) shouldBe "11387"
  }

  @Test
  fun `solve puzzle`() {
    val input = inputFile("day07.txt").readNonBlankLines()
    assertSoftly {
      part1(input) shouldBe "12940396350192"
      part2(input) shouldBe "106016735664498"
    }
  }
}
