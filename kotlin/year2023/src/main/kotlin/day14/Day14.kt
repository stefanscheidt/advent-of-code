package day14

import common.io.inputFile
import common.strings.rows
import java.io.File


val file = inputFile("day14.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<String, String> {
    val input = file.readText().trim()
    return Pair("${solvePartOne(input)}", "${solvePartTwo(input)}")
}

fun solvePartOne(input: String): Int =
    input.rows().map(String::tiltStart).totalLoadNorth

fun solvePartTwo(input: String): Int {
    val seen = mutableMapOf<Int, Int>()
    val maxCycleCnt = 1_000_000_000
    val platform = Platform(input)
    var cycleCnt = 1
    while (cycleCnt <= maxCycleCnt) {
        platform.cycle()
        val hash = platform.hashCode()
        if (hash !in seen) {
            seen[hash] = cycleCnt
            cycleCnt++
        } else {
            val remainingCycles = (maxCycleCnt - cycleCnt) % (cycleCnt - seen.getValue(hash))
            repeat(remainingCycles) { platform.cycle() }
            break
        }
    }
    return platform.totalLoadNorth
}

fun String.tiltStart(): String =
    split("#").joinToString(separator = "#") { part ->
        val rocksCnt = part.count { it == 'O' }
        val spacesCnt = part.length - rocksCnt
        "${"O".repeat(rocksCnt)}${".".repeat(spacesCnt)}"
    }

fun String.tiltEnd(): String =
    split("#").joinToString(separator = "#") { part ->
        val rocksCnt = part.count { it == 'O' }
        val spacesCnt = part.length - rocksCnt
        "${".".repeat(spacesCnt)}${"O".repeat(rocksCnt)}"
    }

fun String.tiltNorth(): String =
    rows().map(String::tiltStart).rowJoin()

fun String.tiltEast(): String =
    lines().map(String::tiltEnd).lineJoin()

fun String.tiltSouth(): String =
    rows().map(String::tiltEnd).rowJoin()

fun String.tiltWest(): String =
    lines().map(String::tiltStart).lineJoin()

fun String.cycle(): String =
    tiltNorth().tiltWest().tiltSouth().tiltEast()


val List<String>.totalLoadNorth: Int
    get() = sumOf { row ->
        row.mapIndexed { index, char -> if (char == 'O') size - index else 0 }.sum()
    }

val String.totalLoadNorth: Int
    get() = rows().totalLoadNorth

fun List<String>.lineJoin(): String =
    joinToString(separator = System.lineSeparator())

fun List<String>.rowJoin(): String =
    (0 until maxOf(String::length))
        .joinToString(separator = System.lineSeparator()) { row ->
            map { it.getOrNull(row) ?: " " }.joinToString(separator = "")
        }

data class Platform(private var platform: String) {

    fun cycle() {
        platform = platform.cycle()
    }

    val totalLoadNorth: Int
        get() = platform.totalLoadNorth

}
