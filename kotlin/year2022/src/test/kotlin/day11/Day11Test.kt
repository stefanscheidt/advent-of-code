package day11

import common.io.inputFile
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test


class Day11Test {

    private val sample = inputFile("sample11.txt").readText()

    @Test
    fun `parse operation`() {
        val plus6 = parseOperation("  Operation: new = old + 6")
        val times19 = parseOperation("  Operation: new = old * 19")
        val square = parseOperation("  Operation: new = old * old")

        plus6(2) shouldBe 2 + 6
        times19(2) shouldBe 2 * 19
        square(2) shouldBe 2 * 2
    }

    @Test
    fun `parse monkey`() {
        val input = """
            |Monkey 0:
            |  Starting items: 79, 98
            |  Operation: new = old * 19
            |  Test: divisible by 23
            |    If true: throw to monkey 2
            |    If false: throw to monkey 3
        """.trimMargin()

        val monkey = parseMonkey(input)

        monkey.items shouldBe mutableListOf(79, 98)
        monkey.operation(2) shouldBe 2 * 19
        monkey.test shouldBe 23
        monkey.ifTrue shouldBe 2
        monkey.ifFalse shouldBe 3
    }

    @Test
    fun `inspect items`() {
        val input = """
            |Monkey 0:
            |  Starting items: 79, 98
            |  Operation: new = old * 19
            |  Test: divisible by 23
            |    If true: throw to monkey 2
            |    If false: throw to monkey 3
        """.trimMargin()
        val monkey = parseMonkey(input)

        monkey.inspectItems() shouldBe listOf(Pair(3, 500L), Pair(3, 620L))
    }

    @Test
    fun `parse input`() {
        val monkeys = parseInput(sample)

        monkeys.map { it.test } shouldBe listOf(23, 19, 13, 17)
    }

    @Test
    fun `tick one round`() {
        val monkeys = parseInput(sample)

        monkeys.tick()

        monkeys.map { it.items } shouldBe listOf(
            mutableListOf(20, 23, 27, 26),
            mutableListOf(2080, 25, 167, 207, 401, 1046),
            mutableListOf(),
            mutableListOf(),
        )
    }

    @Test
    fun `compute monkey business for sample with default divisor`() {
        val monkeys = parseInput(sample)

        repeat(20) { monkeys.tick() }

        monkeys.business() shouldBe 10605L
    }

    @Test
    fun `compute monkey business for sample after 10000 rounds`() {
        val monkeys = parseInput(sample)
        val lcd = monkeys.lowestCommonTest()

        repeat(10_000) { monkeys.tick { it % lcd }  }

        monkeys.business() shouldBe 2_713_310_158
    }

    @Test
    fun `solve puzzle`() {
        val solution = solvePuzzle(file)

        solution.first shouldBe 111210
        solution.second shouldBe 0
    }

}
