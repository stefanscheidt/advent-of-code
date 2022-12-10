package day10

import common.io.inputFile
import java.io.File


val file = inputFile("day10.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2:")
    println(solution.second)
}

fun solvePuzzle(file: File): Pair<Int, String> {
    val instruction = parseInput(file.readLines())
    val values = instruction.run()

    return Pair(
        values.signalStrength(),
        values.render()
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

sealed interface Instruction {
    val cycles: Int
    val value: Int
}

data class Addx(override val value: Int) : Instruction {
    override val cycles: Int
        get() = 2
}

object Noop : Instruction {
    override val cycles: Int
        get() = 1
    override val value: Int
        get() = 0
}

fun List<Instruction>.run(): List<Int> {
    val values = mutableListOf<Int>()
    var x = 1
    forEach { instruction ->
        repeat(instruction.cycles) { values.add(x) }
        if (instruction is Addx) x += instruction.value
    }
    return values
}

fun List<Int>.signalStrengthAt(cycle: Int): Int =
    cycle * getOrElse(cycle - 1) { 0 }

fun List<Int>.signalStrength(): Int =
    probeCycles.map(::signalStrengthAt).sum()

val probeCycles = listOf(20, 60, 100, 140, 180, 220)

fun List<Int>.render(): String {
    val pixels = mapIndexed { index, value ->
        if (index % 40 in value - 1..value + 1) "#" else "."
    }

    val output = StringBuilder()
    pixels.forEachIndexed { index, pixel ->
        output.append(pixel)
        if (index % 40 == 39) output.append("\n")
    }
    return output.toString()
}
