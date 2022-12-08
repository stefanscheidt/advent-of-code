package day08

import common.io.inputFile
import java.io.File
import kotlin.math.min


val file = inputFile("day08.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<Int, Int> {
    val forest = parseInput(file.readText())
    return Pair(forest.visibilityScore(), forest.scenicScore())
}

fun parseInput(input: String): Forest =
    input.trim().lines().map { line ->
        line.map(Char::digitToInt)
    }

typealias Forest = List<List<Int>>

val Forest.hight: Int
    get() = size

val Forest.width: Int
    get() = firstOrNull()?.size ?: 0

val Forest.perimeter: Int
    get() = 2 * (width + hight - 2)

fun Forest.rowSplitAt(x: Int, y: Int): List<List<Int>> =
    listOf(this[y].subList(0, x).reversed(), this[y].subList(x + 1, width))

fun Forest.columnSplitAt(x: Int, y: Int): List<List<Int>> {
    val column = (0 until hight).map { this[it][x] }
    return listOf(column.subList(0, y).reversed(), column.subList(y + 1, hight))
}

fun Forest.isVisibleAt(x: Int, y: Int): Boolean {
    val (top, bottom) = columnSplitAt(x, y).map { it.max() }
    val (left, right) = rowSplitAt(x, y).map { it.max() }
    return listOf(top, bottom, left, right).any { it < this[y][x] }
}

fun Forest.visibilityScore(): Int {
    val count = (1 until width - 1).flatMap { x ->
        (1 until hight - 1).map { y ->
            isVisibleAt(x, y)
        }
    }.count { it }
    return count + perimeter
}

fun Forest.scenicScoreAt(x: Int, y: Int): Int {
    val h = this[y][x]
    val (top, bottom) = columnSplitAt(x, y).map { scenicScore(it, h) }
    val (left, right) = rowSplitAt(x, y).map { scenicScore(it, h) }
    return top * bottom * left * right
}

fun Forest.scenicScore(): Int =
    (1 until width - 1).flatMap { x ->
        (1 until hight - 1).map { y ->
            scenicScoreAt(x, y)
        }
    }.max()

private fun scenicScore(hights: List<Int>, h: Int): Int =
    min(hights.takeWhile { it < h }.size + 1, hights.size)


