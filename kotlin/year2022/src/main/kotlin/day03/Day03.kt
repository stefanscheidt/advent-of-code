package day03

import common.io.inputFile
import java.io.File


val file = inputFile("day03.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<Int, Int> {
    val inventories = file.readLines()

    val first = inventories
        .sumOf { it.eval() }
    val second = inventories.chunked(3)
        .map { it.badge() }
        .sumOf { it.eval() }

    return Pair(first, second)
}

typealias Item = Char

fun Char.eval(): Int =
    if (isUpperCase()) {
        code - 'A'.code + 27
    } else {
        code - 'a'.code + 1
    }

typealias Inventory = String

fun Inventory.wrongPackedItem(): Item {
    val firstHalf = substring(0, length / 2)
    val secondHalf = substring(length / 2)
    return firstHalf.toSet().intersect(secondHalf.toSet()).first()
}

fun Inventory.eval(): Int =
    wrongPackedItem().eval()

private fun List<Inventory>.badge(): Item =
    fold(emptySet<Char>()) { acc, items ->
        if (acc.isEmpty()) items.toSet() else acc.intersect(items.toSet())
    }.first()
