package day17

// Computer

data class Computer(
  val program: List<Long>,
  var regA: Long = 0,
  var regB: Long = 0,
  var regC: Long = 0,
) {
  private var ip = 0
  private val out = mutableListOf<Long>()

  private val literal: Long
    get() = program[ip + 1]

  private val combo: Long
    get() =
      when (val operand = program[ip + 1]) {
        0L,
        1L,
        2L,
        3L -> operand

        4L -> regA
        5L -> regB
        6L -> regC
        else -> error("Unknown operand $operand")
      }

  fun run(): List<Long> {
    while (ip in program.indices) {
      when (program[ip]) {
        0L -> {
          regA /= (1 shl combo.toInt())
          ip += 2
        }

        1L -> {
          regB = regB xor literal
          ip += 2
        }

        2L -> {
          regB = combo % 8
          ip += 2
        }

        3L -> {
          ip = if (regA == 0L) ip + 2 else literal.toInt()
        }

        4L -> {
          regB = regB xor regC
          ip += 2
        }

        5L -> {
          out.add(combo % 8)
          ip += 2
        }

        6L -> {
          regB = regA / (1 shl combo.toInt())
          ip += 2
        }

        7L -> {
          regC = regA / (1 shl combo.toInt())
          ip += 2
        }
      }
    }
    return out
  }

  companion object {
    fun from(input: List<String>): Computer {
      val (regA, regB, regC) = input.take(3).map { it.split(": ").last().toLong() }
      val program = input.last().substringAfter("Program: ").split(",").map(String::toLong)
      return Computer(program, regA, regB, regC)
    }
  }
}

// Part 1

fun part1(input: List<String>): String = Computer.from(input).run().joinToString(",")

// Part 2

fun part2(input: List<String>): String =
  Computer.from(input).let { computer ->
    computer
      .program
      .reversed()
      .fold(listOf(0L)) { candidates, instruction ->
        candidates.flatMap { candidate ->
          val shifted = candidate shl 3
          (shifted..shifted + 8).mapNotNull { attempt ->
            attempt.takeIf { regA ->
              computer.copy(regA = regA).run().first() == instruction
            }
          }
        }
      }
      .first()
      .toString()
  }
