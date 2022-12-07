package day07

import common.io.inputFile
import java.io.File
import java.util.Stack


val file = inputFile("day07.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<Int, Int> {
    val input = file.readLines()
    val dirs = parseInput(input)
    return Pair(solvePartOne(dirs), solvePartTwo(dirs))
}

fun solvePartOne(dirs: Map<String, Int>): Int =
    dirs.values.filter { it <= 100_000 }.sum()

fun solvePartTwo(dirs: Map<String, Int>): Int {
    val spaceUnused = totalDiscSpace - (dirs["/"] ?: 0)
    return dirs.values.filter { it >= spaceNeeded - spaceUnused }.min()
}

fun parseInput(input: List<String>): Map<String, Int> {
    val acc = input.fold(Acc()) { acc, line -> acc.accept(line) }
    while (acc.path.peek() != "/") {
        acc.accept("$ cd ..")
    }

    return acc.dirs.toMap()
}

private typealias Path = Stack<String>

private fun Path.isRoot(): Boolean =
    peek() == separator

private fun Path.absolute(): String =
    if (isRoot()) separator else toList().joinToString(separator).drop(1)

private data class Acc(
    val path: Path = Stack<String>(),
    val dirs: MutableMap<String, Int> = mutableMapOf()
) {

    private fun addSize(size: Int?) {
        val currentDir = path.absolute()
        dirs[currentDir] = (dirs[currentDir] ?: 0) + (size ?: 0)
    }

    fun accept(line: String): Acc {
        when {
            line == "$ cd .." -> {
                val leavingDir = path.absolute()
                path.pop()
                addSize(dirs[leavingDir])
            }

            line.startsWith("$ cd ") -> {
                val newDir = line.split(" ").last()
                path.push(newDir)
                addSize(0)
            }

            line.matches(filePattern) -> {
                filePattern.matchEntire(line)?.let { match ->
                    val (size, _) = match.destructured
                    addSize(size.toInt())
                }
            }
        }

        return this
    }

}

val filePattern = """(\d+) (\S+)""".toRegex()

private const val separator = "/"
private const val totalDiscSpace = 70_000_000
private const val spaceNeeded = 30_000_000
