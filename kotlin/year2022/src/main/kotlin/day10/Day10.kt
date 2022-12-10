package day10

import common.io.inputFile
import java.io.File


val file = inputFile("day10.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<Int, Int> {
    val instruction = parseInput(file.readLines())

    return Pair(
        instruction.run().signalStrength(),
        0
    )
}

fun parseInput(input: List<String>): List<Instruction> =
    input.map(::parseInstruction)

fun parseInstruction(input: String): Instruction =
    when {
        input == "noop" -> Noop
        input.startsWith("addx") -> Addx(input.split(" ")[1].toInt())
        else -> throw IllegalArgumentException("invalid input $input")
    }

sealed class Instruction(val cycles: Int, val value: Int)

class Addx(value: Int) : Instruction(2, value)

object Noop : Instruction(1, 0)

data class State(val cycles: Int, val value: Int) {
    fun apply(instruction: Instruction): State =
        copy(cycles = cycles + instruction.cycles, value = value + instruction.value)
}

fun List<Instruction>.run(): List<State> =
    scan(State(0, 1)) { state, instruction ->
        state.apply(instruction)
    }

fun List<State>.signalStrengthAt(cycle: Int): Int? =
    zipWithNext().firstOrNull { it.second.cycles >= cycle }
        ?.let { match -> cycle * match.first.value }

fun List<State>.signalStrength(): Int =
    probeCycles.mapNotNull(::signalStrengthAt).sum()

val probeCycles = listOf(20, 60, 100, 140, 180, 220)
