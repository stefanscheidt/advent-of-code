package day01

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day01Test {

  @Test
  fun `solve puzzle`() {
    val solution = solvePuzzle(file)

    solution.first shouldBe 500
    solution.second shouldBe 709
  }
}
