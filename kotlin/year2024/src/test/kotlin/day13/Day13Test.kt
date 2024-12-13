package day13

import common.io.inputFile
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day13Test {

  private val sample =
    """
    Button A: X+94, Y+34
    Button B: X+22, Y+67
    Prize: X=8400, Y=5400

    Button A: X+26, Y+66
    Button B: X+67, Y+21
    Prize: X=12748, Y=12176

    Button A: X+17, Y+86 
    Button B: X+84, Y+37
    Prize: X=7870, Y=6450

    Button A: X+69, Y+23
    Button B: X+27, Y+71
    Prize: X=18641, Y=10279
    """
      .trimIndent()

  @Test
  fun `parse behaviour`() {
    val behavior =
      """
      Button A: X+94, Y+34
      Button B: X+22, Y+67
      Prize: X=8400, Y=5400
      """
        .trimIndent()

    behavior.intoBehavior() shouldBe Behavior(p(94, 34), p(22, 67), p(8400L, 5400L))
  }

  @Test
  fun `solve part one with sample input`() {
    part1(sample) shouldBe "480"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample) shouldBe "875318608908"
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day13.txt").readText().trimEnd()
    part1(input) shouldBe "35729"
  }

  @Test
  fun `solve part two`() {
    val input = inputFile("day13.txt").readText().trimEnd()
    part2(input) shouldBe "88584689879723"
  }

}
