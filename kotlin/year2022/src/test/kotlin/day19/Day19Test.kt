package day19

import day19.Mineral.Clay
import day19.Mineral.Geode
import day19.Mineral.Obsidian
import day19.Mineral.Ore
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day19Test {

  private val blueprint1 =
    "Blueprint 1:" +
      " Each ore robot costs 4 ore." +
      " Each clay robot costs 2 ore." +
      " Each obsidian robot costs 3 ore and 14 clay." +
      " Each geode robot costs 2 ore and 7 obsidian."

  private val blueprint2 =
    "Blueprint 2:" +
      " Each ore robot costs 2 ore." +
      " Each clay robot costs 3 ore." +
      " Each obsidian robot costs 3 ore and 8 clay." +
      " Each geode robot costs 3 ore and 12 obsidian."

  private val sample = listOf(blueprint1, blueprint2)

  @Test
  fun `parse blueprint`() {
    parseBlueprint(blueprint1) shouldBe
      Blueprint(
        mapOf(
          Ore to mapOf(Ore to 4),
          Clay to mapOf(Ore to 2),
          Obsidian to mapOf(Ore to 3, Clay to 14),
          Geode to mapOf(Ore to 2, Obsidian to 7),
        )
      )
  }

  @Test
  fun `solve part one with sample input`() {
    solvePartOne(parseInput(sample)) shouldBe 33
  }

  @Test
  fun `solve part two with sample input`() {
    solvePartTwo(parseInput(sample)) shouldBe 56 * 62
  }

  @Test
  fun `solve puzzle`() {
    val solution = solvePuzzle(file)

    solution.first shouldBe 1624
    solution.second shouldBe 12628
  }
}
