package day11

import common.io.inputFile
import common.io.readNonBlankLines
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day11Test {

  private val sample1 =
    """
      aaa: you hhh
      you: bbb ccc
      bbb: ddd eee
      ccc: ddd eee fff
      ddd: ggg
      eee: out
      fff: out
      ggg: out
      hhh: ccc fff iii
      iii: out
    """
      .trimIndent()

  private val sample2 =
    """
      svr: aaa bbb
      aaa: fft
      fft: ccc
      bbb: tty
      tty: ccc
      ccc: ddd eee
      ddd: hub
      hub: fff
      eee: dac
      dac: fff
      fff: ggg hhh
      ggg: out
      hhh: out
    """
      .trimIndent()

  @Test
  fun `solve part one with sample input`() {
    part1(sample1.lines()) shouldBe "5"
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day11.txt").readNonBlankLines()
    part1(input) shouldBe "791"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample2.lines()) shouldBe "2"
  }

  @Test
  fun `solve part two`() {
    val input = inputFile("day11.txt").readNonBlankLines()
    part2(input) shouldBe "520476725037672"
  }
}
