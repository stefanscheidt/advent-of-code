package day22

import common.io.inputFile
import common.io.readNonBlankLines
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day22Test {

  @Test
  fun `mix value into secret`() {
    mix(42, 15) shouldBe 37
  }

  @Test
  fun `prune a secret`() {
    prune(100000000) shouldBe 16113920
  }

  @Test
  fun `compute next secrets`() {
    nextSecrets(123).take(11).toList() shouldBe listOf(
      123L,
      15887950L,
      16495136L,
      527345L,
      704524L,
      1553684L,
      12683156L,
      11100544L,
      12249484L,
      7753432L,
      5908254L,
    )
  }

  @Test
  fun `compute 2000th secret`() {
    nthSecret(1, 2000) shouldBe 8685429L
    nthSecret(10, 2000) shouldBe 4700978L
    nthSecret(100, 2000) shouldBe 15273692L
    nthSecret(2024, 2000) shouldBe 8667524L
  }

  @Test
  fun `solve part one with sample input`() {
    val input =
      """
      1
      10
      100
      2024
      """
        .trimIndent()
        .lines()

    part1(input) shouldBe "37327623"
  }

  @Test
  fun `solve part two with sample input`() {
    val input =
      """
      1
      2
      3
      2024
      """
        .trimIndent()
        .lines()

    part2(input) shouldBe "23"
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day22.txt").readNonBlankLines()
    part1(input) shouldBe "20441185092"
  }

  @Test
  fun `solve part two`() {
    val input = inputFile("day22.txt").readNonBlankLines()
    part2(input) shouldBe "2268"
  }

}
