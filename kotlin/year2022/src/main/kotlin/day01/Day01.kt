package day01

import common.io.inputFile
import java.io.File


val file = inputFile("day01.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun parseInput(lines: Sequence<String>): List<List<Int>> =
    lines
        .joinToString(separator = "|")
        .split("||")
        .map {
            it.split("|").map(String::toInt)
        }

fun parseInputAsSequence(lines: Sequence<String>): Sequence<List<Int>> {
    val iterator = lines.iterator()
    val collector = mutableListOf<Int>()
    return sequence {
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.isNotBlank()) {
                collector.add(next.toInt())
            } else {
                yield(collector.toList())
                collector.clear()
            }
        }
        yield(collector.toList())
    }
}

fun List<List<Int>>.sortedSums(): List<Int> =
    map(List<Int>::sum).sorted().reversed()

fun solvePuzzle(file: File): Pair<Int, Int> {
    val caloryBlocks = file.useLines { parseInput(it) }
    val calories = caloryBlocks.sortedSums()
    return Pair(calories.first(), calories.take(3).sum())
}

