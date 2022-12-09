package day09

import common.geom.Point2D
import common.geom.minus
import common.geom.origin2D
import common.geom.plus
import common.io.inputFile
import java.io.File
import java.lang.Integer.min
import kotlin.math.absoluteValue
import kotlin.math.sign


val file = inputFile("day09.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<Int, Int> {
    val motions = parseInput(file.readLines())

    val first = solvePartOne(motions)

    return Pair(first, 0)
}

fun solvePartOne(motions: List<Motion>): Int =
    Rope(origin2D, origin2D)
        .trail(motions)
        .map { it.tail }
        .toSet()
        .count()

fun parseInput(input: List<String>): List<Motion> =
    input.map { line ->
        val (direction, steps) = line.split(" ")
        Motion(direction.first(), steps.toInt())
    }

typealias Direction = Char

fun Direction.vector(): Point2D =
    when (this) {
        'R' -> Point2D(1, 0)
        'L' -> Point2D(-1, 0)
        'U' -> Point2D(0, 1)
        'D' -> Point2D(0, -1)
        else -> throw IllegalArgumentException("invald direction $this")
    }

data class Motion(val direction: Direction, val steps: Int)

fun Point2D.move(direction: Direction): Point2D =
    this + direction.vector()

fun Point2D.follow(other: Point2D): Point2D {
    val (dx, dy) = other - this
    return if (dx.absoluteValue <= 1 && dy.absoluteValue <= 1) {
        this
    } else {
        val vec = Point2D(
            x = dx.sign * min(dx.absoluteValue, 1),
            y = dy.sign * min(dy.absoluteValue, 1),
        )
        this + vec
    }
}

data class Rope(val head: Point2D, val tail: Point2D) {

    fun move(direction: Direction): Rope {
        val newHead = head.move(direction)
        val newTail = tail.follow(newHead)
        return Rope(newHead, newTail)
    }

    fun trail(motion: Motion): List<Rope> =
        generateSequence(this) { it.move(motion.direction) }
            .take(motion.steps + 1)
            .toList()

    fun trail(motions: List<Motion>): List<Rope> =
        motions.fold(mutableListOf(this)) { list, motion ->
            val last = list.removeLast()
            list.addAll(last.trail(motion))
            list
        }

}
