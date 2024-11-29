package day10

import common.io.inputFile
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day10Test {

  @Test
  fun `solve part one with sample input`() {
    val values = inputFile("sample10.txt").readLines().run()

    values.signalStrength() shouldBe 13140
  }

  @Test
  fun `solve part two with sample input`() {
    val output = inputFile("sample10.txt").readLines().run().render()

    output.trim() shouldBe
      """
            ##..##..##..##..##..##..##..##..##..##..
            ###...###...###...###...###...###...###.
            ####....####....####....####....####....
            #####.....#####.....#####.....#####.....
            ######......######......######......####
            #######.......#######.......#######.....
        """
        .trimIndent()
  }

  @Test
  fun `solve puzzle`() {
    val solution = solvePuzzle(file)

    solution.first shouldBe 16880
    solution.second.trim() shouldBe
      """
            ###..#..#..##..####..##....##.###..###..
            #..#.#.#..#..#....#.#..#....#.#..#.#..#.
            #..#.##...#..#...#..#..#....#.###..#..#.
            ###..#.#..####..#...####....#.#..#.###..
            #.#..#.#..#..#.#....#..#.#..#.#..#.#.#..
            #..#.#..#.#..#.####.#..#..##..###..#..#.
        """
        .trimIndent()
  }
}
