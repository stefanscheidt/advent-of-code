package day05

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.Stack

class Day05Test {

    private val sample = """
       |    [D]    
       |[N] [C]    
       |[Z] [M] [P]
       | 1   2   3 
       |
       |move 1 from 2 to 1
       |move 3 from 1 to 3
       |move 2 from 2 to 1
       |move 1 from 1 to 2
    """.trimMargin()

    @Test
    fun `parse sample input stacks`() {
        parseStacks(sample) shouldBe listOf(
            stackOf('Z', 'N'),
            stackOf('M', 'C', 'D'),
            stackOf('P'),
        )
    }

    @Test
    fun `parse sample input moves`() {
        parseMoves(sample) shouldBe listOf(
            Move(1, 0, 1),
            Move(0, 2, 3),
            Move(1, 0, 2),
            Move(0, 1, 1),
        )
    }

    @Test
    fun `apply9000 sample moves to sample stacks`() {
        val moves = parseMoves(sample)
        val stacks = parseStacks(sample)
        stacks.apply(moves, ::move9000)

        stacks shouldBe listOf(
            stackOf('C'),
            stackOf('M'),
            stackOf('P', 'D', 'N', 'Z'),
        )

        stacks.top() shouldBe "CMZ"
    }

    @Test
    fun `apply9001 sample moves to sample stacks`() {
        val moves = parseMoves(sample)
        val stacks = parseStacks(sample)
        stacks.apply(moves, ::move9001)

        stacks shouldBe listOf(
            stackOf('M'),
            stackOf('C'),
            stackOf('P', 'Z', 'N', 'D'),
        )

        stacks.top() shouldBe "MCD"
    }

    @Test
    fun `solve puzzle`() {
        val solution = solvePuzzle(file)

        solution.first shouldBe "QMBMJDFTD"
        solution.second shouldBe "NBTVTJNFJ"
    }

}

private fun stackOf(vararg crates: Char): Stack<Char> =
    Stack<Char>().also { stack -> crates.forEach(stack::push) }
