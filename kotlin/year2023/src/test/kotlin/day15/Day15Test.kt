package day15

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day15Test {

  private val sample =
    """
        rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7
    """
      .trimIndent()

  @Test
  fun `parse ignores new lines and trailing commans`() {
    val input =
      """
            ABC,
            DEF,
        """
        .trimIndent()

    parseInput(input) shouldBe listOf("ABC", "DEF")
  }

  @Test
  fun `compute hash of HASH`() {
    "HASH".holidayHashCode() shouldBe 52
  }

  @Test
  fun `parse a command`() {
    parseCommand("rn=1") shouldBe AddOrReplace("rn", 1)
    parseCommand("cm-") shouldBe Remove("cm")
  }

  @Test
  fun `apply add or replace`() {
    val box = mutableListOf<Lens>()
    box.apply(AddOrReplace("rn", 1))

    box shouldBe mutableListOf(Lens("rn", 1))

    box.apply(AddOrReplace("rn", 2))

    box shouldBe mutableListOf(Lens("rn", 2))
  }

  @Test
  fun `apply remove command`() {
    val box = mutableListOf(Lens("aa", 1), Lens("bb", 2), Lens("cc", 3))

    box.apply(Remove("xx"))

    box.size shouldBe 3

    box.apply(Remove("bb"))

    box shouldBe mutableListOf(Lens("aa", 1), Lens("cc", 3))
  }

  @Test
  fun `solve part one with sample input`() {
    solvePartOne(parseInput(sample)) shouldBe 1320
  }

  @Test
  fun `solve part two with sample input`() {
    val commands = parseInput(sample).mapNotNull(::parseCommand)
    solvePartTwo(commands) shouldBe 145
  }

  @Test
  fun `solve puzzle`() {
    val solution = solvePuzzle(file)

    solution.first shouldBe "503487"
    solution.second shouldBe "261505"
  }
}
