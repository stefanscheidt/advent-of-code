package day20

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day20Test {

    private val sample = """
        1
        2
        -3
        3
        -2
        0
        4
    """.trimIndent()

    @Test
    fun `parse input`() {
        parseInput(sample.lines()) shouldBe listOf(
            IndexedLong(0, 1),
            IndexedLong(1, 2),
            IndexedLong(2, -3),
            IndexedLong(3, 3),
            IndexedLong(4, -2),
            IndexedLong(5, 0),
            IndexedLong(6, 4),
        )
    }

    @Test
    fun `solve part one with sample input`() {
        val input = parseInput(sample.lines())

        groveCoordinates(decrypt(input)) shouldBe listOf(4, -3, 2)
    }

    @Test
    fun `solve part two with sample input`() {
        solvePartTwo(sample.lines()) shouldBe 1623178306L
    }

    @Test
    fun `solve puzzle`() {
        val solution = solvePuzzle(file)

        solution.first shouldBe 18257L
        solution.second shouldBe 4148032160983L
    }

}
