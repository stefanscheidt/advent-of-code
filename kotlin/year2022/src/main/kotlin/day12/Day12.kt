package day12

import common.geom.Point2D
import common.io.inputFile
import java.io.File
import java.util.PriorityQueue


val file = inputFile("day12.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<Int, Int> {
    val heightmap = parseInput(file.readLines())

    return Pair(heightmap.shortestPathCostUp(), heightmap.shortestPathCostDown())
}

fun List<String>.find(char: Char): Point2D? {
    forEachIndexed { y, row ->
        if (row.contains(char)) return Point2D(row.indexOf(char), y)
    }
    return null
}

fun parseInput(input: List<String>): Heightmap {
    val heightmap = input.map { row ->
        row.map { char ->
            when (char) {
                'S' -> 0
                'E' -> 'z'.code - 'a'.code
                else -> char.code - 'a'.code
            }
        }
    }
    val start = input.find('S') ?: error("no start found")
    val dest = input.find('E') ?: error("no destination found")
    return Heightmap(heightmap, start, dest)
}

data class Heightmap(
    val heights: List<List<Int>>,
    val start: Point2D,
    val dest: Point2D,
) {
    fun contains(point: Point2D): Boolean =
        point.y in heights.indices && point.x in heights[point.y].indices

    fun heightAt(point: Point2D): Int =
        heights[point.y][point.x]
}

fun Point2D.neighbours(): Set<Point2D> =
    setOf(
        copy(x = x - 1),
        copy(x = x + 1),
        copy(y = y - 1),
        copy(y = y + 1),
    )

data class PointWithCost(
    val point: Point2D,
    val cost: Int,
) : Comparable<PointWithCost> {
    override fun compareTo(other: PointWithCost): Int {
        return cost.compareTo(other.cost)
    }
}

fun Heightmap.shortestPathCostUp(): Int =
    shortestPathCost(
        start = start,
        isReachable = { start, dest -> heightAt(dest) - heightAt(start) <= 1 },
        isDest = { point -> point == dest }
    )

fun Heightmap.shortestPathCostDown(): Int =
    shortestPathCost(
        start = dest,
        isReachable = { start, dest -> heightAt(start) - heightAt(dest) <= 1 },
        isDest = { point -> heightAt(point) == 0 }
    )

fun Heightmap.shortestPathCost(
    start: Point2D,
    isReachable: (Point2D, Point2D) -> Boolean,
    isDest: (Point2D) -> Boolean
): Int {
    val seen = mutableSetOf<Point2D>()
    val costs = PriorityQueue<PointWithCost>()
    costs.add(PointWithCost(start, 0))
    while (costs.isNotEmpty()) {
        val next = costs.poll()
        if (next.point !in seen) {
            seen.add(next.point)
            val reachable = next.point.neighbours()
                .filter { contains(it) && isReachable(next.point, it) }
            if (reachable.any(isDest)) return next.cost + 1
            costs.addAll(reachable.map { PointWithCost(it, next.cost + 1) })
        }
    }
    error("No path found")
}
