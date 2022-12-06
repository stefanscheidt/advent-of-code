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
    stacks1.rearrange(moves, ::mover9000)

    val stacks2 = parseStacks(input)
    stacks2.rearrange(moves, ::mover9001)

    return Pair(stacks1.top(), stacks2.top())
}


typealias Crate = Char

data class Move(val src: Int, val dest: Int, val amount: Int)

fun List<Stack<Crate>>.rearrange(
    moves: List<Move>,
    action: (Stack<Crate>, Stack<Crate>, Int) -> Unit
) {
    moves.forEach { action(this[it.src], this[it.dest], it.amount) }
}

fun mover9000(src: Stack<Crate>, dest: Stack<Crate>, amount: Int) {
    repeat(amount) { dest.push(src.pop()) }
}

fun mover9001(src: Stack<Crate>, dest: Stack<Crate>, amount: Int) {
    val temp = buildList<Crate> {
        repeat(amount) { add(src.pop()) }
    }
    temp.reversed().forEach {
        dest.push(it)
    }
}

fun List<Stack<Crate>>.top(): String =
    map(Stack<Crate>::peek).joinToString("")

fun parseStacks(input: String): List<Stack<Crate>> {
    val lines = input.lines()
        .takeWhile { it.contains('[') }
        .reversed()
    val numberOfStacks = (lines.first().length + 1) / 4
    val stacks = List(numberOfStacks) { Stack<Char>() }
    lines.forEach { line ->
        for (i in 0 until numberOfStacks) {
            val char = line[4 * i + 1]
            if (char.isUpperCase()) stacks[i].push(char)
        }
    }
    return stacks
}

fun parseMoves(input: String): List<Move> =
    input.lines()
        .dropWhile { !it.startsWith("move") }
        .mapNotNull { line ->
            instructionPattern.matchEntire(line)?.let { match ->
                val (repeat, src, dest) = match.destructured
                Move(src.toInt() - 1, dest.toInt() - 1, repeat.toInt())
            }
        }

val instructionPattern = """move (\d+) from (\d+) to (\d+)""".toRegex()
