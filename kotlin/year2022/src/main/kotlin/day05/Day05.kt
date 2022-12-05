package day05

import common.io.inputFile
import java.io.File
import java.util.Stack


val file = inputFile("day05.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<String, String> {
    val input = file.readText()
    val moves = parseMoves(input)

    val stacks1 = parseStacks(input)
    stacks1.apply(moves, ::move9000)

    val stacks2 = parseStacks(input)
    stacks2.apply(moves, ::move9001)

    return Pair(stacks1.top(), stacks2.top())
}

data class Move(val src: Int, val dest: Int, val amount: Int)

fun List<Stack<Char>>.apply(
    moves: List<Move>,
    action: (Stack<Char>, Stack<Char>, Int) -> Unit
) {
    moves.forEach { move ->
        val src = this[move.src]
        val dest = this[move.dest]
        action(src, dest, move.amount)
    }
}

fun move9000(src: Stack<Char>, dest: Stack<Char>, amount: Int) {
    repeat(amount) { dest.push(src.pop()) }
}

fun move9001(src: Stack<Char>, dest: Stack<Char>, amount: Int) {
    val temp = buildList<Char> {
        repeat(amount) { add(src.pop()) }
    }
    temp.reversed().forEach {
        dest.push(it)
    }
}

fun List<Stack<Char>>.top(): String =
    map(Stack<Char>::peek).joinToString("")

fun parseStacks(input: String): List<Stack<Char>> {
    val lines = input.lines()
        .takeWhile { it.trim().startsWith('[') }
        .reversed()
    val numberOfStacks = (lines.first().length + 1) / 4
    val stacks = List(numberOfStacks) { Stack<Char>() }
    lines.forEach {
        for (i in 0 until numberOfStacks) {
            val char = it[4 * i + 1]
            if (char != ' ') stacks[i].push(char)
        }
    }
    return stacks
}

fun parseMoves(input: String): List<Move> =
    input.lines()
        .dropWhile { !it.startsWith("move") }
        .mapNotNull { line ->
            moveRegex.matchEntire(line)?.let { matchResult ->
                val (repeat, src, dest) = matchResult.destructured
                Move(src.toInt() - 1, dest.toInt() - 1, repeat.toInt())
            }
        }

val moveRegex = """move\s+(\d+)\s+from\s+(\d+)\s+to\s+(\d+)""".toRegex()
