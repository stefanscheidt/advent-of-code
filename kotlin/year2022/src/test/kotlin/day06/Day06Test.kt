package day06

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day06Test {

    private val sample = "mjqjpqmgbljsphdztnvjfqwrcgsmlb"

    @Test
    fun `compute start of first marker with size 4`() {
        sample.startOfFirstMarkerWithSize(4) shouldBe 3
    }

    @Test
    fun `solve puzzle`() {
        val solution = solvePuzzle(file)

        solution.first shouldBe 1275
        solution.second shouldBe 3605
    }

}
