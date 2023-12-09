package day07

import day07.HandType.*
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day07Test {

    private val sample = """
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483
    """.trimIndent()

    @Test
    fun `hand type without joker`() {
        "32T3K".type() shouldBe P1
        "KK677".type() shouldBe P2
        "KTJJT".type() shouldBe P2
        "T55J5".type() shouldBe K3
        "QQQJA".type() shouldBe K3
    }

    @Test
    fun `hand type with joker`() {
        "32T3K".typeWithJoker() shouldBe P1
        "KK677".typeWithJoker() shouldBe P2
        "KTJJT".typeWithJoker() shouldBe K4
        "T55J5".typeWithJoker() shouldBe K4
        "QQQJA".typeWithJoker() shouldBe K4
    }

    @Test
    fun `solve part one with sample input`() {
        val hands = parseHands(sample.lines())
        solveIt(hands, handComparator) shouldBe 6440
    }

    @Test
    fun `solve part two with sample input`() {
        val hands = parseHands(sample.lines())
        solveIt(hands, handComparatorWithJoker) shouldBe 5905
    }

    @Test
    fun `solve puzzle`() {
        val solution = solvePuzzle(file)

        solution.first shouldBe "250058342"
        solution.second shouldBe "250506580"
    }

}
