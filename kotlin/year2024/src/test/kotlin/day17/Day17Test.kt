package day17

import common.io.inputFile
import common.io.readNonBlankLines
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day17Test {

  @Test
  fun `run sample program`() {
    val computer = Computer(program = listOf(0, 1, 5, 4, 3, 0).map(Int::toLong), regA = 729)
    computer.run() shouldBe listOf(4, 6, 3, 5, 6, 3, 5, 2, 1, 0).map(Int::toLong)
  }

  @Test
  fun `solve part one with sample input`() {
    val sample =
      """
          Register A: 729
          Register B: 0
          Register C: 0
      
          Program: 0,1,5,4,3,0
          """
        .trimIndent()
    part1(sample.lines()) shouldBe "4,6,3,5,6,3,5,2,1,0"
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day17.txt").readNonBlankLines()
    part1(input) shouldBe "7,1,2,3,2,6,7,2,5"
  }

  @Test
  fun `solve part two with sample input`() {
    val sample =
      """
      Register A: 2024
      Register B: 0
      Register C: 0
      
      Program: 0,3,5,4,3,0
      """
        .trimIndent()

    part2(sample.lines()) shouldBe "117440"
  }

  @Test
  fun `solve part two`() {
    val input = inputFile("day17.txt").readNonBlankLines()
    part2(input) shouldBe "202356708354602"
  }
}
