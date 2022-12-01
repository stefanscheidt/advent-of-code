package day01

import common.io.inputFile
import common.sequences.findFirstRepeatedOrNull
import common.sequences.repeat
import java.io.File


val file = inputFile("day01.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<Int, Int> {
    val input = file.readLines().map(String::toInt)
    return Pair(
        input.sum(),
        input.repeat().scan(0, Int::plus).findFirstRepeatedOrNull() ?: 0
    )
}
