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
    .mapNotNull { it.toOperation() }
    .sumOf { it.eval() }
    .let(Long::toString)

fun part2(input: String): String {
  return Regex("""mul\((\d*),(\d*)\)|do\(\)|don't\(\)""")
    .findAll(input)
    .mapNotNull { it.toOperation() }
    .fold(State()) { state, operation -> state.apply(operation) }
    .let { "${it.sum}" }
}

data class State(val isEnabled: Boolean = true, val sum: Long = 0L) {
  fun apply(operation: Operation): State =
    when (operation) {
      is Do -> copy(isEnabled = true)
      is Dont -> copy(isEnabled = false)
      is Mult -> if (isEnabled) this.copy(sum = sum + operation.eval()) else this
    }
}

sealed interface Operation {
  fun eval(): Long = 0L
}

data object Do : Operation

data object Dont : Operation

data class Mult(val op1: Long, val op2: Long) : Operation {
  override fun eval(): Long = op1 * op2
}

fun MatchResult.toOperation(): Operation? =
  when {
    value.startsWith("do(") -> Do
    value.startsWith("don't(") -> Dont
    value.startsWith("mul(") -> Mult(groupValues[1].toLong(), groupValues[2].toLong())
    else -> null
  }
