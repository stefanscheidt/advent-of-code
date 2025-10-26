package common.strings

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class StringsTest {

  @Test
  fun `compute rows`() {
    val s =
      """
          ABC
          DE
          FGHI
          JKL
      """
        .trimIndent()

    s.rows() shouldBe listOf("ADFJ", "BEGK", "C HL", "  I ")
  }
}
