package day24

import common.geom.p3
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day24Test {

    private val sample = """
        19, 13, 30 @ -2,  1, -2
        18, 19, 22 @ -1, -1, -2
        20, 25, 34 @ -2, -2, -4
        12, 31, 28 @ -1, -2, -1
        20, 19, 15 @  1, -5, -3
    """.trimIndent()

    @Test
    fun `parse hailstone`() {
        parseHailstone("19, 13, 30 @ -2,  1, -2") shouldBe Hailstone(p = p3(19, 13, 30), v = p3(-2, 1, -2))
    }

    @Test
    fun `solve part one with sample input`() {
        val hailstones = sample.lines().map(::parseHailstone)

        solvePartOne(hailstones, range = (7L..27L)) shouldBe 2
    }

    @Test
    fun `solve part two with sample input`() {
        TODO()
    }

    @Test
    fun `solve puzzle`() {
        val solution = solvePuzzle(file)

        solution.first shouldBe "15107"
        solution.second shouldBe ""
    }

}
