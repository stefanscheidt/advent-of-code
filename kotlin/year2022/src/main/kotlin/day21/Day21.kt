package day21

import common.io.inputFile
import java.io.File


val file = inputFile("day21.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<Long, Long> {
    val expressions = parseInput(file.readLines())
    return Pair(solvePartOne(expressions), -1)
}

fun solvePartOne(expressions: Map<String, Expression>): Long =
    expressions["root"]?.eval() ?: 0


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
        "+" -> Long::plus
        "-" -> Long::minus
        "*" -> Long::times
        "/" -> Long::div
        else -> error("invalid operation $input")
    }

typealias Operator = Long.(Long) -> Long

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
    val operator: Operator
) :
    Expression {
    lateinit var left: Expression
    lateinit var right: Expression

    override fun eval(): Long = left.eval().operator(right.eval())
}

val operationPattern = """([a-z]{4}) ([+\-*/]) ([a-z]{4})""".toRegex()

