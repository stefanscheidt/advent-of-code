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
    fun `parse sample stacks input`() {
        parseStacks(sample) shouldBe listOf(
            stackOf('Z', 'N'),
            stackOf('M', 'C', 'D'),
            stackOf('P'),
        )
    }

    @Test
    fun `parse sample moves input`() {
        parseMoves(sample) shouldBe listOf(
            Move(1, 0, 1),
            Move(0, 2, 3),
            Move(1, 0, 2),
            Move(0, 1, 1),
        )
    }

    @Test
    fun `rearrange sample stacks with CrateMover 9000`() {
        val moves = parseMoves(sample)
        val stacks = parseStacks(sample)
        stacks.rearrange(moves, ::mover9000)

        stacks shouldBe listOf(
            stackOf('C'),
            stackOf('M'),
            stackOf('P', 'D', 'N', 'Z'),
        )

        stacks.top() shouldBe "CMZ"
    }

    @Test
    fun `rearrange sample stacks with CrateMover 9001`() {
        val moves = parseMoves(sample)
        val stacks = parseStacks(sample)
        stacks.rearrange(moves, ::mover9001)

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
