package day12

import common.geom.Point2D
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day12Test {

  private val sample =
    """
        Sabqponm
        abcryxxl
        accszExk
        acctuvwj
        abdefghi
    """
      .trimIndent()

  @Test
  fun `parse input`() {
    val heightmap = parseInput(sample.lines())

    heightmap.start shouldBe Point2D(0, 0)
    heightmap.dest shouldBe Point2D(5, 2)
  }

  @Test
  fun `solve part one for sample`() {
    val heightmap = parseInput(sample.lines())

    heightmap.shortestPathCostUp() shouldBe 31
  }

  @Test
  fun `solve part two for sample`() {
    val heightmap = parseInput(sample.lines())

    heightmap.shortestPathCostDown() shouldBe 29
  }

  @Test
  fun `solve puzzle`() {
    val solution = solvePuzzle(file)

    solution.first shouldBe 447
    solution.second shouldBe 446
  }
}
