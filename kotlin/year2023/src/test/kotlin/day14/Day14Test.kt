package day14

import common.strings.rows
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day14Test {

    private val sample = """
        O....#....
        O.OO#....#
        .....##...
        OO.#O....O
        .O.....O#.
        O.#..O.#.#
        ..O..#O..O
        .......O..
        #....###..
        #OO..#....
    """.trimIndent()

    @Test
    fun `tilt north`() {
        val rows = sample.rows()

        val tilted = """
            OOOO.#.O..
            OO..#....#
            OO..O##..O
            O..#.OO...
            ........#.
            ..#....#.#
            ..O..#.O.O
            ..O.......
            #....###..
            #....#....
        """.trimIndent()

        rows.map(String::tiltStart) shouldBe tilted.rows()
    }

    @Test
    fun `solve part one with sample input`() {
        solvePartOne(sample) shouldBe 136
    }

    @Test
    fun `join lines`() {
        val input = """
            abc
            def
            ghi
            jkl
        """.trimIndent()

        val lines = input.lines()

        lines.lineJoin() shouldBe input
    }

    @Test
    fun `join rows`() {
        val input = """
            abc
            def
            ghi
            jkl
        """.trimIndent()

        val rows = input.rows()

        rows.rowJoin() shouldBe input
    }

    @Test
    fun `cycle around`() {
        val afterOneCycle = """
            .....#....
            ....#...O#
            ...OO##...
            .OO#......
            .....OOO#.
            .O#...O#.#
            ....O#....
            ......OOOO
            #...O###..
            #..OO#....
        """.trimIndent()

        val afterTwoCycles = """
            .....#....
            ....#...O#
            .....##...
            ..O#......
            .....OOO#.
            .O#...O#.#
            ....O#...O
            .......OOO
            #..OO###..
            #.OOO#...O
        """.trimIndent()

        val afterThreeCycles = """
            .....#....
            ....#...O#
            .....##...
            ..O#......
            .....OOO#.
            .O#...O#.#
            ....O#...O
            .......OOO
            #...O###.O
            #.OOO#...O
        """.trimIndent()

        val cycleOne = sample.cycle()
        val cycleTwo = cycleOne.cycle()
        val cycleThree = cycleTwo.cycle()

        cycleOne shouldBe afterOneCycle
        cycleTwo shouldBe afterTwoCycles
        cycleThree shouldBe afterThreeCycles
    }

    @Test
    fun `solve part two with sample input`() {
        solvePartTwo(sample) shouldBe 64
    }

    @Test
    fun `solve puzzle`() {
        val solution = solvePuzzle(file)

        solution.first shouldBe "106186"
        solution.second shouldBe "106390"
    }

}
