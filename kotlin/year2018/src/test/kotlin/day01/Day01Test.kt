package day01

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.io.File

class Day01Test {

    @Test
    fun `solve part one`() {
        val result = File("./input/day01.txt").useLines { lines ->
            lines.map(String::toInt).sum()
        }

        result shouldBe 500
    }

    @Test
    fun `solve part two`() {
        val input = File("./input/day01.txt").readLines().map(String::toInt)

        findFrequencyImp2(input) shouldBe 709
    }

    @Test
    fun `solve part two second example`() {
        val input = listOf(+3, +3, +4, -2, -4)
        val result = findFrequencyFn1(input)

        result shouldBe 10
    }

    private fun findFrequencyImp1(input: List<Int>): Int {
        val frequencies = mutableSetOf(0)
        var sum = 0
        while (true) {
            for (i in input) {
                sum += i
                if (frequencies.contains(sum)) {
                    return sum
                }
                frequencies.add(sum)
            }
        }
    }

    private fun findFrequencyImp2(input: List<Int>): Int {
        val frequencies = mutableSetOf(0)
        var sum = 0
        return input.repeat()
            .map {
                sum += it
                sum
            }
            .first { !frequencies.add(it) }
    }

    private fun findFrequencyFn1(input: List<Int>): Int {
        data class Acc(val frequencies: List<Int> = listOf(0), val result: Int? = null)

        return input.repeat()
            .scan(Acc()) { acc, i ->
                val next = acc.frequencies.last() + i
                if (next in acc.frequencies)
                    acc.copy(result = next)
                else
                    acc.copy(frequencies = acc.frequencies + next)
            }
            .map { it.result }
            .first { it != null }
            ?: 0
    }


    private fun <T> List<T>.repeat(): Sequence<T> =
        sequence {
            if (isEmpty()) return@sequence
            while (true) yieldAll(this@repeat)
        }

}
