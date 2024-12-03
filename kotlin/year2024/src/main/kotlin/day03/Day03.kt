package day03

import common.io.inputFile
import java.io.File

val file = inputFile("day03.txt")

fun main() {
  val (part1, part2) = solvePuzzle(file)
  println("1: $part1")
  println("2: $part2")
}

fun solvePuzzle(file: File): Pair<String, String> {
  val input = file.readText()
  return Pair(part1(input), part2(input))
}

fun part1(input: String): String =
  Regex("""mul\((\d*),(\d*)\)""")
    .findAll(input)
    .map { it.toOperation() }
    .sumOf { it.eval() }
    .let(Long::toString)

fun part2(input: String): String {
  return Regex("""mul\((\d*),(\d*)\)|do\(\)|don't\(\)""")
    .findAll(input)
    .map { it.toOperation() }
    .fold(State()) { state, operation ->
      when (operation) {
        is Do -> state.enabled()
        is Dont -> state.disabled()
        is Mult -> state.add(operation.eval())
      }
    }
    .let { "${it.sum}" }
}

sealed interface Operation {
  fun eval(): Long = 0L
}

data object Do : Operation

data object Dont : Operation

data class Mult(val op1: Long, val op2: Long) : Operation {
  override fun eval(): Long = op1 * op2
}

fun MatchResult.toOperation(): Operation =
  when {
    value.startsWith("do(") -> Do
    value.startsWith("don't(") -> Dont
    value.startsWith("mul(") -> Mult(groupValues[0].toLong(), groupValues[1].toLong())
    else -> error("illegal match result $value")
  }

data class State(val isEnabled: Boolean = true, val sum: Long = 0L) {
  fun enabled(): State = copy(isEnabled = true)

  fun disabled(): State = copy(isEnabled = false)

  fun add(value: Long) = if (isEnabled) copy(sum = sum + value) else this
}
