package day13

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import common.io.inputFile
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day13Test {

    private val sampleFile = inputFile("sample13.txt")

    @Test
    fun `parser test`() {
        PacketGrammar.parseToEnd("[]") shouldBe ListPacket(emptyList())

        PacketGrammar.parseToEnd("[[]]") shouldBe ListPacket(listOf(
            ListPacket(emptyList())
        ))

        PacketGrammar.parseToEnd("[1]") shouldBe ListPacket(listOf(
            IntPacket(1)
        ))

        PacketGrammar.parseToEnd("[1,2,3]") shouldBe ListPacket(listOf(
            IntPacket(1), IntPacket(2), IntPacket(3)
        ))

        PacketGrammar.parseToEnd("[[],[1],[[[10,3]],[],1]]") shouldBe ListPacket(listOf(
            ListPacket(emptyList()),
            ListPacket(listOf(IntPacket(1))),
            ListPacket(listOf(
                ListPacket(listOf(
                    ListPacket(listOf(
                        IntPacket(10), IntPacket(3)
                    ))
                )),
                ListPacket(emptyList()),
                IntPacket(1)
            ))
        ))
    }

    @Test
    fun `solve part one with sample input`() {
        solvePuzzle(sampleFile).first shouldBe 13
    }

    @Test
    fun `solve part two with sample input`() {
        solvePuzzle(sampleFile).second shouldBe 140
    }

    @Test
    fun `solve puzzle`() {
        val solution = solvePuzzle(file)

        solution.first shouldBe 5340
        solution.second shouldBe 21276
    }

}
