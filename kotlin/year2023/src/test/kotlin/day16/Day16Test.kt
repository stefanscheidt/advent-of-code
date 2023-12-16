package day16

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day16Test {

    private val sample = """
        .|...\....
        |.-.\.....
        .....|-...
        ........|.
        ..........
        .........\
        ..../.\\..
        .-.-/..|..
        .|....-|.\
        ..//.|....
    """.trimIndent()

    @Test
    fun `solve part one with sample input`() {
        val grid = sample.lines().filter(String::isNotBlank)

        solvePartOne(grid) shouldBe 46
    }

    @Test
    fun `solve part two with sample input`() {
        val grid = sample.lines().filter(String::isNotBlank)

        solvePartTwo(grid) shouldBe 51
    }

    @Test
    fun `solve puzzle`() {
        val solution = solvePuzzle(file)

        solution.first shouldBe "8539"
        solution.second shouldBe "8674"
    }

}
