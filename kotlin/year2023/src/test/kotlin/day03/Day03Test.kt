package day03

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day03Test {

  private val sample =
    """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...${'$'}.*....
        .664.598..
    """
      .trimIndent()

  @Test
  fun `solve part one with sample input`() {
    val schematic = Schematic(sample.lines().filterNot(String::isBlank))

    schematic.partNumbers.sum() shouldBe 4361
  }

  @Test
  fun `solve part two with sample input`() {
    val schematic = Schematic(sample.lines().filterNot(String::isBlank))

    schematic.gearRatios.sum() shouldBe 467835
  }

  @Test
  fun `solve puzzle`() {
    val solution = solvePuzzle(file)

    solution.first shouldBe "544433"
    solution.second shouldBe "76314915"
  }
}
