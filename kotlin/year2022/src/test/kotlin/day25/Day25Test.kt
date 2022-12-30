package day25

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day25Test {

    @Test
    fun `decimal to SNAFU`() {
        decimalToSnafu(314159265) shouldBe "1121-1110-1=0"
    }

    @Test
    fun `SNAFU to decimal`() {
        snafuToDecimal("1121-1110-1=0") shouldBe 314159265
    }

    @Test
    fun `solve puzzle`() {
        solvePuzzle(file) shouldBe "2=--00--0220-0-21==1"
    }

}
