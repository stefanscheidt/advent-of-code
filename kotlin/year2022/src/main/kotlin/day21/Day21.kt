package day21

import common.io.inputFile
import day21.Operator.Divided
import day21.Operator.Minus
import day21.Operator.Plus
import day21.Operator.Times
import java.io.File

val file = inputFile("day21.txt")

fun main() {
  val solution = solvePuzzle(file)

  println("1: ${solution.first}")
  println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<Long, Long> {
  val expressions = parseInput(file.readLines())
  return Pair(solvePartOne(expressions), solvePartTwo(expressions))
}

// --- Parsing ---

fun parseInput(input: List<String>): Map<String, Expression> {
  val expressions = input.map(::parseExpression).associateBy { it.id }
  expressions.values.filterIsInstance<Operation>().forEach { opration ->
    expressions[opration.leftId]?.let { opration.left = it }
    expressions[opration.rightId]?.let { opration.right = it }
  }
  return expressions
}

fun parseExpression(input: String): Expression {
  val (id, expression) = input.split(": ")
  val operationMatch = operationPattern.matchEntire(expression)
  return if (operationMatch != null) {
    val (leftId, operator, rightId) = operationMatch.destructured
    Operation(id, leftId, rightId, parseOperator(operator))
  } else {
    Number(id, expression.toLong())
  }
}

fun parseOperator(input: String): Operator =
  when (input) {
    "+" -> Plus
    "-" -> Minus
    "*" -> Times
    "/" -> Divided
    else -> error("invalid operation $input")
  }

val operationPattern = """([a-z]{4}) ([+\-*/]) ([a-z]{4})""".toRegex()

// --- Part One ---

fun solvePartOne(expressions: Map<String, Expression>): Long = expressions["root"]?.eval() ?: 0

enum class Operator {
  Plus,
  Minus,
  Times,
  Divided,
}

sealed interface Expression {
  val id: String

  fun eval(): Long
}

data class Number(override val id: String, val value: Long) : Expression {
  override fun eval(): Long = value
}

data class Operation(
  override val id: String,
  val leftId: String,
  val rightId: String,
  val operator: Operator,
) : Expression {
  lateinit var left: Expression
  lateinit var right: Expression

  override fun eval(): Long =
    when (operator) {
      Plus -> left.eval() + right.eval()
      Minus -> left.eval() - right.eval()
      Times -> left.eval() * right.eval()
      Divided -> left.eval() / right.eval()
    }
}

// --- Part Two ---

fun solvePartTwo(expressions: Map<String, Expression>): Long {
  val root = expressions["root"] ?: return 0
  if (root !is Operation) return 0

  val unknown = expressions["humn"] ?: return 0
  if (unknown !is Number) return 0

  val nodesBetween = nodesBetween(root, unknown)

  return if (root.left in nodesBetween) {
    root.left.solve(unknown, root.right.eval(), nodesBetween)
  } else {
    root.right.solve(unknown, root.left.eval(), nodesBetween)
  }
}

fun nodesBetween(from: Expression, to: Expression): Set<Expression> =
  when (from) {
    is Number -> if (from.id == to.id) setOf(from) else emptySet()
    is Operation -> {
      val nodesBetweenLeft = nodesBetween(from.left, to)
      val nodesBetweenRight = nodesBetween(from.right, to)
      when {
        nodesBetweenLeft.isNotEmpty() -> nodesBetweenLeft + from
        nodesBetweenRight.isNotEmpty() -> nodesBetweenRight + from
        else -> emptySet()
      }
    }
  }

fun Expression.solve(unknown: Number, other: Long, nodesBetween: Set<Expression>): Long =
  when (this) {
    is Number -> {
      if (this.id == unknown.id) other else value
    }
    is Operation -> {
      if (left in nodesBetween) {
        val newOther =
          when (operator) {
            Plus -> other - right.eval()
            Minus -> other + right.eval()
            Times -> other / right.eval()
            Divided -> other * right.eval()
          }
        left.solve(unknown, newOther, nodesBetween)
      } else {
        val newOther =
          when (operator) {
            Plus -> other - left.eval()
            Minus -> left.eval() - other
            Times -> other / left.eval()
            Divided -> left.eval() / other
          }
        right.solve(unknown, newOther, nodesBetween)
      }
    }
  }
