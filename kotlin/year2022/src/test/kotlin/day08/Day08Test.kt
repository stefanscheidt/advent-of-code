package day08

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day08Test {

    private val sample = """
        30373
        25512
        65332
        33549
        35390
    """.trimIndent()

    @Test
    fun `solve part one with sample input`() {
        val forest = parseInput(sample)

        forest.perimeter shouldBe 16

        forest.isVisibleAt(1, 1) shouldBe true
        forest.isVisibleAt(2, 1) shouldBe true
        forest.isVisibleAt(3, 1) shouldBe false

        forest.visibilityScore() shouldBe 21
    }

    @Test
    fun `solve part two with sample input`() {
        val forest = parseInput(sample)

        forest.scenicScoreAt(2, 1) shouldBe 4
        forest.scenicScoreAt(2, 3) shouldBe 8

        forest.scenicScore() shouldBe 8
    }

    @Test
    fun `solve puzzle`() {
        val solution = solvePuzzle(file)

        solution.first shouldBe 1801
        solution.second shouldBe 209880
    }

}
