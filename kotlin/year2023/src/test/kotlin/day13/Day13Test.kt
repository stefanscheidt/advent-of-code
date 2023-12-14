package day13

import common.strings.rows
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day13Test {

    private val sample = """
        #.##..##.
        ..#.##.#.
        ##......#
        ##......#
        ..#.##.#.
        ..##..##.
        #.#.##.#.

        #...##..#
        #....#..#
        ..##..###
        #####.##.
        #####.##.
        ..##..###
        #....#..#
    """.trimIndent()

    @Test
    fun `find reflections in row`() {
        "#.##..##.".mirrorCandidates() shouldBe setOf(5, 7)
        "##..##..".mirrorCandidates() shouldBe setOf(1, 3, 5, 7)
        "##....##".mirrorCandidates() shouldBe setOf(1, 4, 7)
    }

    @Test
    fun `find reflection across vertical line`() {
        val pattern = """
            #.##..##.
            ..#.##.#.
            ##......#
            ##......#
            ..#.##.#.
            ..##..##.
            #.#.##.#.
         """.trimIndent()

        pattern.lines().mirrorCandidates() shouldBe setOf(5)
    }

    @Test
    fun `find reflection across horizontal line`() {
        val pattern = """
            #...##..#
            #....#..#
            ..##..###
            #####.##.
            #####.##.
            ..##..###
            #....#..#
         """.trimIndent()

        pattern.rows().mirrorCandidates() shouldBe setOf(4)
    }

    @Test
    fun `solve part one with sample input`() {
        solvePartOne(parseInput(sample)) shouldBe 405
    }

    @Test
    fun `solve part two with sample input`() {
        TODO()
    }

    @Test
    fun `solve puzzle`() {
        val solution = solvePuzzle(file)

        solution.first shouldBe "33975"
        solution.second shouldBe ""
    }

}
