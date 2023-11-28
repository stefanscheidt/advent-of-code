package day01

import common.io.inputFile
import java.io.File


val file = inputFile("day01.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<String, String> {
    return Pair("", "")
}

fun parseInput(input: String): Any {
    TODO()
}
