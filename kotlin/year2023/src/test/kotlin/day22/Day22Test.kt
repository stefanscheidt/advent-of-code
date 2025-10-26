package day22

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class Day22Test {

  // 1,0,1~1,2,1   <- A
  // 0,0,2~2,0,2   <- B
  // 0,2,3~2,2,3   <- C
  // 0,0,4~0,2,4   <- D
  // 2,0,5~2,2,5   <- E
  // 0,1,6~2,1,6   <- F
  // 1,1,8~1,1,9   <- G
  //
  // Brick A is the only brick supporting bricks B and C.
  // Brick B is one of two bricks supporting brick D and brick E.
  // Brick C is the other brick supporting brick D and brick E.
  // Brick D supports brick F.
  // Brick E also supports brick F.
  // Brick F supports brick G.
  // Brick G isn't supporting any bricks.
  private val sample =
    """
        1,0,1~1,2,1
        0,0,2~2,0,2
        0,2,3~2,2,3
        0,0,4~0,2,4
        2,0,5~2,2,5
        0,1,6~2,1,6
        1,1,8~1,1,9
    """
      .trimIndent()

  @Test
  fun `parse brick`() {
    parseBrick("1,0,1~1,2,1") shouldBe Brick((1..1), (0..2), (1..1))
  }

  @Test
  fun `overlapping ranges`() {
    ((1..1) overlapps 0..2).shouldBeTrue()
    ((0..2) overlapps 1..1).shouldBeTrue()

    ((1..1) overlapps 2..2).shouldBeFalse()
    ((2..2) overlapps 1..1).shouldBeFalse()
  }

  @Test
  fun `settle bricks`() {
    parseBricks(sample.lines()).settled() shouldBe
      bricksOf(
        "1,0,1~1,2,1",
        "0,0,2~2,0,2",
        "0,2,2~2,2,2",
        "0,0,3~0,2,3",
        "2,0,3~2,2,3",
        "0,1,4~2,1,4",
        "1,1,5~1,1,6",
      )
  }

  @Test
  fun `link supporting and supported bricks`() {
    val input =
      """
          1,0,1~1,2,1
          0,0,2~2,0,2
          0,2,3~2,2,3
          0,0,4~0,2,4
      """
        .trimIndent()
    val bricks = parseBricks(input.lines()).settled()

    bricks.map(Brick::supporting) shouldBe
      listOf(
        bricksOf("0,0,2~2,0,2", "0,2,2~2,2,2").toSet(),
        bricksOf("0,0,3~0,2,3").toSet(),
        bricksOf("0,0,3~0,2,3").toSet(),
        bricksOf().toSet(),
      )

    bricks.map(Brick::supportedBy) shouldBe
      listOf(
        bricksOf().toSet(),
        bricksOf("1,0,1~1,2,1").toSet(),
        bricksOf("1,0,1~1,2,1").toSet(),
        bricksOf("0,0,2~2,0,2", "0,2,2~2,2,2").toSet(),
      )
  }

  @Test
  fun `solve part one with sample input`() {
    val bricks = parseBricks(sample.lines())

    solvePartOne(bricks) shouldBe 5
  }

  @Test
  fun `solve part two with sample input`() {
    val bricks = parseBricks(sample.lines())

    solvePartTwo(bricks) shouldBe 7
  }

  @Test
  @Disabled("because it's slow")
  fun `solve puzzle`() {
    val solution = solvePuzzle(file)

    solution.first shouldBe "522"
    solution.second shouldBe "83519"
  }
}

fun bricksOf(vararg inputs: String): List<Brick> = parseBricks(inputs.toList())
