package day02

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day02Test {

    private val sample = """
            A Y
            B X
            C Z
        """.trimIndent().lines()

    @Test
    fun `solve part one with sample`() {
        solvePartOne(sample) shouldBe 15
    }

    @Test
    fun `solve part two with sample`() {
        solvePartTwo(sample) shouldBe 12
    }

    @Test
    fun `solve puzzle`() {
        val solution = solvePuzzle(file)

        solution.first shouldBe 10310
        solution.second shouldBe 14859
    }

}
