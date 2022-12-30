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
    return Pair(solvePartOne(expressions), solvePartTwo(expressions))
}

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
        Operation(id, leftId, rightId, operator)
    } else {
        Number(id, expression.toLong())
    }
}

fun solvePartOne(expressions: Map<String, Expression>): Long =
    expressions["root"]?.eval() ?: 0

fun solvePartTwo(expressions: Map<String, Expression>): Long =
    expressions.solve("root", "humn")

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
    val operator: String
) :
    Expression {
    lateinit var left: Expression
    lateinit var right: Expression

    override fun eval(): Long =
        when (operator) {
            "+" -> left.eval() + right.eval()
            "-" -> left.eval() - right.eval()
            "*" -> left.eval() * right.eval()
            "/" -> left.eval() / right.eval()
            else -> error("invalid operation $operator")
        }
}

val operationPattern = """([a-z]{4}) ([+\-*/]) ([a-z]{4})""".toRegex()

fun Expression.nodesBetween(to: Expression): Set<Expression> =
    when (this) {
        is Number -> if (this.id == to.id) setOf(this) else emptySet()
        is Operation -> {
            val nodesBetweenLeft = left.nodesBetween(to)
            val nodesBetweenRight = right.nodesBetween(to)
            when {
                nodesBetweenLeft.isNotEmpty() -> nodesBetweenLeft + this
                nodesBetweenRight.isNotEmpty() -> nodesBetweenRight + this
                else -> emptySet()
            }
        }
    }

fun Expression.solve(unknown: Number, nodes: Set<Expression>, other: Long): Long =
    when (this) {
        is Number -> {
            if (this.id == unknown.id) other else value
        }

        is Operation -> {
            if (left in nodes) {
                val newOther = when(operator) {
                    "+" -> other - right.eval()
                    "-" -> other + right.eval()
                    "*" -> other / right.eval()
                    "/" -> other * right.eval()
                    else -> error("invalid operation $operator")
                }
                left.solve(unknown, nodes, newOther)
            } else {
                val newOther = when(operator) {
                    "+" -> other - left.eval()
                    "-" -> left.eval() - other
                    "*" -> other / left.eval()
                    "/" -> left.eval() / other
                    else -> error("invalid operation $operator")
                }
                right.solve(unknown, nodes, newOther)
            }
        }
    }

fun Map<String, Expression>.solve(rootId: String, unknownId: String): Long {
    val root = this[rootId] ?: return 0
    if (root !is Operation) return 0

    val unknown = this[unknownId] ?: return 0
    if (unknown !is Number) return 0

    val nodesBetween = root.nodesBetween(unknown)

    return if (root.left in nodesBetween) {
        root.left.solve(unknown, nodesBetween, root.right.eval())
    } else {
        root.right.solve(unknown, nodesBetween, root.left.eval())
    }
}
