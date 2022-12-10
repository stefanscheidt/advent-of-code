package day10

import common.io.inputFile
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day10Test {

    private val sample = """
            noop
            addx 3
            addx -5
        """.trimIndent()


    @Test
    fun `parse input`() {
        parseInput(sample.lines()) shouldBe listOf(
            Noop,
            Addx(3),
            Addx(-5),
        )
    }

    @Test
    fun `solve part one with sample input`() {
        val instructions = parseInput(inputFile("sample10.txt").readLines())
        val values = instructions.run()

        values.signalStrength() shouldBe 13140
    }

    @Test
    fun `solve part two with sample input`() {
        val instructions = parseInput(inputFile("sample10.txt").readLines())
        val output = instructions.run().render()

        output.trim() shouldBe """
            ##..##..##..##..##..##..##..##..##..##..
            ###...###...###...###...###...###...###.
            ####....####....####....####....####....
            #####.....#####.....#####.....#####.....
            ######......######......######......####
            #######.......#######.......#######.....
        """.trimIndent()
    }

    @Test
    fun `solve puzzle`() {
        val solution = solvePuzzle(file)

        solution.first shouldBe 16880
        solution.second.trim() shouldBe """
            ###..#..#..##..####..##....##.###..###..
            #..#.#.#..#..#....#.#..#....#.#..#.#..#.
            #..#.##...#..#...#..#..#....#.###..#..#.
            ###..#.#..####..#...####....#.#..#.###..
            #.#..#.#..#..#.#....#..#.#..#.#..#.#.#..
            #..#.#..#.#..#.####.#..#..##..###..#..#.
        """.trimIndent()
    }

}
