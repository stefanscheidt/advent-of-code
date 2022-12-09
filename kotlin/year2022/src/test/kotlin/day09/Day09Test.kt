package day09

import common.geom.Point2D
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day09Test {

    private val sample = """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent()

    @Test
    fun `tail follows head`() {
        Point2D(1, 1).follow(Point2D(1, 1)) shouldBe Point2D(1, 1)
        Point2D(1, 1).follow(Point2D(2, 1)) shouldBe Point2D(1, 1)
        Point2D(2, 1).follow(Point2D(1, 2)) shouldBe Point2D(2, 1)

        Point2D(1, 1).follow(Point2D(3, 1)) shouldBe Point2D(2, 1)

        Point2D(1, 1).follow(Point2D(2, 3)) shouldBe Point2D(2, 2)
    }

    @Test
    fun `solve part one with sample`() {
        val input = parseInput(sample.lines())

        solvePartOne(input) shouldBe 13
    }

    @Test
    fun `solve puzzle`() {
        val solution = solvePuzzle(file)

        solution.first shouldBe 0
        solution.second shouldBe 6197
    }

}
