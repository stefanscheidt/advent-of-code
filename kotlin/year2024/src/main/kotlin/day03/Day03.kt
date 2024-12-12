package day03

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
    .fold(State(isEnabled = true, sum = 0)) { state, operation ->
      when (operation) {
        is Do -> state.enabled()
        is Dont -> state.disabled()
        is Mult -> state.updated(operation.eval())
      }
    }
    .let { "${it.sum}" }
}

data class State(val isEnabled: Boolean, val sum: Long) {
  fun enabled(): State = copy(isEnabled = true)

  fun disabled(): State = copy(isEnabled = false)

  fun updated(value: Long): State = if (isEnabled) copy(sum = sum + value) else this
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
