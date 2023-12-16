package day16

import common.geom.Point2D
import common.geom.plus
import common.io.inputFile
import java.io.File


val file = inputFile("day16.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<String, String> {
    val grid = file.readText().trim().lines()
    return Pair("${solvePartOne(grid)}", "${solvePartTwo(grid)}")
}

fun solvePartOne(grid: Grid): Int {
    val start = Beam(Point2D(0, 0), East)
    return grid.energize(start).count()
}

fun solvePartTwo(grid: Grid): Int =
    buildList {
        this.addAll(grid.first().indices.map { x -> Beam(Point2D(x, 0), South) })
        this.addAll(grid.first().indices.map { x -> Beam(Point2D(x, grid.lastIndex), North) })
        this.addAll(grid.indices.map { y -> Beam(Point2D(0, y), East) })
        this.addAll(grid.indices.map { y -> Beam(Point2D(grid.first().lastIndex, y), West) })
    }.maxOf { grid.energize(it).count() }

val North = Point2D(0, -1) // y-axis goes downwards
val East = Point2D(1, 0)
val South = Point2D(0, 1) // y-axis goes downwards
val West = Point2D(-1, 0)

data class Beam(val pos: Point2D, val direction: Point2D)

fun Beam.radiate(direction: Point2D): Beam =
    copy(pos = pos + direction, direction = direction)

typealias Grid = List<String>

fun Grid.contains(pos: Point2D): Boolean =
    pos.x in (0 until first().length) && pos.y in indices

fun Beam.radiate(grid: Grid): List<Beam> {
    val tile = grid[pos.y][pos.x]
    return when (direction to tile) {
        North to '/' -> listOf(radiate(East))
        East to '/' -> listOf(radiate(North))
        South to '/' -> listOf(radiate(West))
        West to '/' -> listOf(radiate(South))

        North to '\\' -> listOf(radiate(West))
        East to '\\' -> listOf(radiate(South))
        South to '\\' -> listOf(radiate(East))
        West to '\\' -> listOf(radiate(North))

        North to '-' -> listOf(radiate(West), radiate(East))
        East to '|' -> listOf(radiate(North), radiate(South))
        South to '-' -> listOf(radiate(West), radiate(East))
        West to '|' -> listOf(radiate(North), radiate(South))

        else -> listOf(copy(pos = pos + direction))
    }.filter { grid.contains(it.pos) }
}

fun Grid.energize(start: Beam): Set<Point2D> {
    val seen = mutableSetOf<Beam>().apply { add(start) }
    val beams = ArrayDeque<Beam>().apply { add(start) }
    while (beams.isNotEmpty()) {
        beams.removeFirst()
            .radiate(this)
            .forEach { beam ->
                if (beam !in seen) {
                    seen.add(beam)
                    beams.add(beam)
                }
            }
    }
    return seen.map(Beam::pos).toSet()
}
