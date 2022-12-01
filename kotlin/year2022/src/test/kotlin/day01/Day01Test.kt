package day01

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day01Test {

    private val sampleInput = """
        1000
        2000
        3000

        4000

        5000
        6000

        7000
        8000
        9000

        10000
    """.trimIndent().lineSequence()

    @Test
    fun `parse sample input`() {
        parseInput(sampleInput) shouldBe listOf(
            listOf(1000, 2000, 3000),
            listOf(4000),
            listOf(5000, 6000),
            listOf(7000, 8000, 9000),
            listOf(10000),
        )
    }

    @Test
    fun `parse sample input as sequence`() {
        parseInputAsSequence(sampleInput).toList() shouldBe listOf(
            listOf(1000, 2000, 3000),
            listOf(4000),
            listOf(5000, 6000),
            listOf(7000, 8000, 9000),
            listOf(10000),
        )
    }

    @Test
    fun `calories for sample input`() {
        parseInput(sampleInput).sortedSums() shouldBe listOf(
            24000,
            11000,
            10000,
            6000,
            4000,
        )
    }

    @Test
    fun `solve puzzle`() {
        val solution = solvePuzzle(file)

        solution.first shouldBe 71471
        solution.second shouldBe 211189
    }

}
