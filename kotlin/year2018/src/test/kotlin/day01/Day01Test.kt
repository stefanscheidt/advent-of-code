@file:Suppress("unused")

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

        findFrequencyFn(input) shouldBe 709
    }

    private fun findFrequencyImp(input: List<Int>): Int {
        val seen = mutableSetOf(0)
        var sum = 0
        while (true) {
            for (i in input) {
                sum += i
                if (!seen.add(sum)) return sum
            }
        }
    }

    private fun findFrequencyFn(input: List<Int>): Int =
        input.repeat().scan(0, Int::plus).findFirstRepeatedOrNull() ?: 0

}

private fun <T> List<T>.repeat(): Sequence<T> =
    sequence {
        if (isEmpty()) return@sequence
        while (true) yieldAll(this@repeat)
    }

private fun <T> Sequence<T>.findFirstRepeatedOrNull(): T? {
    val seen = mutableSetOf<T>()
    val xs = iterator()

    var x = xs.next()
    while (seen.add(x)) {
        x = xs.next()
    }

    return x
}

private fun <T> Sequence<T>.findFirstRepeatedOrNullRecursive(): T? {
    tailrec fun go(xs: Iterator<T>, seen: MutableSet<T> = mutableSetOf()): T? {
        if (!xs.hasNext()) return null
        return when (val head = xs.next()) {
            in seen -> head
            else -> go(xs, seen.also { it.add(head) })
        }
    }

    return go(iterator())
}
