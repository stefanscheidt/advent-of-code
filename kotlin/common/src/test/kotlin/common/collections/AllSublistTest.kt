package common.collections

import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class AllSublistTest {

  @Test
  fun `compute all sublists`() {
    emptyList<Int>().allSublists() shouldBe listOf(emptyList())
    listOf(1).allSublists() shouldBe listOf(emptyList(), listOf(1))
    listOf(1, 2).allSublists() shouldBe listOf(emptyList(), listOf(1), listOf(2), listOf(1, 2))
    listOf(1, 2, 3)
      .allSublists()
      .shouldContainExactlyInAnyOrder(
        emptyList(),
        listOf(1),
        listOf(2),
        listOf(3),
        listOf(1, 2),
        listOf(1, 3),
        listOf(2, 3),
        listOf(1, 2, 3),
      )
  }
}
