package day06

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day06Test {

    private val sample = """
        Time:      7  15   30
        Distance:  9  40  200
    """.trimIndent()

    @Test
    fun `travel distance`() {
        travelDistance(7, 0) shouldBe 0
        travelDistance(7, 1) shouldBe 6
        travelDistance(7, 2) shouldBe 10
        travelDistance(7, 5) shouldBe 10
        travelDistance(7, 6) shouldBe 6
        travelDistance(7, 7) shouldBe 0
    }

    @Test
    fun `winning options count`() {
        Race(7, 9).winningOptionsCount shouldBe 4
        Race(15, 40).winningOptionsCount shouldBe 8
        Race(30, 200).winningOptionsCount shouldBe 9
    }

    @Test
    fun `solve part one with sample input`() {
        solvePartOne(parseValues(sample)) shouldBe 288
    }

    @Test
    fun `solve part two with sample input`() {
        solvePartTwo(parseValues(sample)) shouldBe 71503
    }

    @Test
    fun `solve puzzle`() {
        val solution = solvePuzzle(file)

        solution.first shouldBe "316800"
        solution.second shouldBe "45647654"
    }

}
