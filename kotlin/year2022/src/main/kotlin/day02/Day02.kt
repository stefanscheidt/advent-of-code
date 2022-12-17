package day02

import common.io.inputFile
import day02.Result.Draw
import day02.Result.Loss
import day02.Result.Win
import day02.Shape.Paper
import day02.Shape.Rock
import day02.Shape.Scissor
import java.io.File


val file = inputFile("day02.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<Int, Int> {
    val input = file.readLines()
    return Pair(solvePartOne(input), solvePartTwo(input))
}

fun solvePartOne(input: List<String>): Int =
    input.map { line ->
        val parts = line.split(" ")
        val other = parts[0].first().toShape()
        val me = parts[1].first().toShape()
        Pair(other, me)
    }.totalScore()

fun solvePartTwo(input: List<String>): Int {
    fun selectShape(other: Shape, result: Result): Shape =
        when (result to other) {
            Win to Rock -> Paper
            Win to Paper -> Scissor
            Win to Scissor -> Rock
            Loss to Rock -> Scissor
            Loss to Paper -> Rock
            Loss to Scissor -> Paper
            else -> other
        }

    return input.map { line ->
        val parts = line.split(" ")
        val other = parts[0].first().toShape()
        val me = selectShape(other, parts[1].first().toResult())
        Pair(other, me)
    }.totalScore()
}

enum class Shape(val score: Int) {
    Rock(1), Paper(2), Scissor(3)
}

fun Char.toShape(): Shape =
    when (this) {
        in listOf('A', 'X') -> Rock
        in listOf('B', 'Y') -> Paper
        in listOf('C', 'Z') -> Scissor
        else -> error("unknown shape encoding $this")
    }

enum class Result(val score: Int) {
    Loss(0), Draw(3), Win(6)
}

fun Char.toResult(): Result =
    when (this) {
        'X' -> Loss
        'Y' -> Draw
        'Z' -> Win
        else -> error("unknown result encoding $this")
    }

fun Shape.against(other: Shape): Result =
    when (this to other) {
        Rock to Paper -> Loss
        Rock to Scissor -> Win
        Paper to Rock -> Win
        Paper to Scissor -> Loss
        Scissor to Rock -> Loss
        Scissor to Paper -> Win
        else -> Draw
    }

typealias Game = List<Pair<Shape, Shape>>

fun Game.totalScore(): Int =
    sumOf { (other, me) -> me.score + me.against(other).score }
