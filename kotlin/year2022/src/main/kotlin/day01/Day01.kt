package day01

import common.io.inputFile
import java.io.File


val file = inputFile("day01.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun parseInput(input: String): List<List<Int>> =
    input.trim().split("\n\n")
        .map {
            it.lines().map(String::toInt)
        }

fun List<List<Int>>.sortedSums(): List<Int> =
    map(List<Int>::sum).sorted().reversed()

fun solvePuzzle(file: File): Pair<Int, Int> {
    val caloryBlocks = parseInput(file.readText())
    val calories = caloryBlocks.sortedSums()
    return Pair(calories.first(), calories.take(3).sum())
}

