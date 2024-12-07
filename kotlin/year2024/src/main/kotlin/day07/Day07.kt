package day07

import day07.Operation.ADD
import day07.Operation.CONCAT
import day07.Operation.MULT

// Operations

enum class Operation {
  ADD, MULT, CONCAT
}

class OperationCombinator {
  private val cache = mutableMapOf<Int, List<List<Operation>>>()

  tailrec fun anyCombinationOf(
    ops: List<Operation>,
    n: Int,
    acc: List<List<Operation>> = listOf(emptyList())
  ): List<List<Operation>> =
    if (cache[n] != null) {
      cache.getValue(n)
    } else if (acc[0].size == n) {
      cache[n] = acc
      acc
    } else {
      anyCombinationOf(ops, n, acc.flatMap { comb -> ops.map { comb + it } })
    }

}

tailrec fun anyCombinationOf(
  ops: List<Operation>,
  n: Int,
  acc: List<List<Operation>> = listOf(emptyList())
): List<List<Operation>> =
  if (acc[0].size == n)
    acc
  else
    anyCombinationOf(ops, n, acc.flatMap { comb -> ops.map { comb + it } })

// Equations

data class Equation(val value: Long, val operands: List<Long>)

fun String.intoEquation(): Equation {
  val (test, operands) = split(": ")
  return Equation(test.toLong(), operands.split(" ").map { it.toLong() })
}


fun List<Equation>.filterTrue(vararg operations: Operation): List<Equation> {
  val combinator = OperationCombinator()
  return filter { (value, operands) ->
    combinator.anyCombinationOf(operations.toList(), operands.size - 1).any { ops ->
      val opsIter = ops.iterator()
      val result = operands.drop(1).fold(operands.first()) { acc, operand ->
        when (opsIter.next()) {
          ADD -> acc + operand
          MULT -> acc * operand
          CONCAT -> "$acc$operand".toLong()
        }
      }
      result == value
    }
  }
}

fun List<Equation>.sum(): Long =
  sumOf(Equation::value)

// Part 1

fun part1(input: List<String>): String {
  val equations = input.map(String::intoEquation)
  return equations.filterTrue(ADD, MULT).sum().toString()
}

// Part 2

fun part2(input: List<String>): String {
  val equations = input.map(String::intoEquation)
  return equations.filterTrue(ADD, MULT, CONCAT).sum().toString()
}
