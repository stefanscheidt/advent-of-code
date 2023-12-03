package day03

import common.io.inputFile
import java.io.File


val file = inputFile("day03.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<String, String> {
    val schematic = file.readLines().filterNot(String::isBlank)
    return Pair(
        "${schematic.partNumbers.sum()}",
        "${schematic.gearRatios.sum()}"
    )
}

data class NumberPosition(val row: Int, val columns: IntRange)

data class CharPosition(val row: Int, val column: Int)

fun CharPosition.isAdjecent(number: NumberPosition): Boolean =
    row in (number.row - 1..number.row + 1)
            && column in (number.columns.first - 1..number.columns.last + 1)

typealias Schematic = List<String>

fun Schematic.valueAt(number: NumberPosition): Int =
    this[number.row].substring(number.columns).toInt()

val Schematic.numbers: List<NumberPosition>
    get() = flatMapIndexed { rowIndex, line ->
        numberRegex.findAll(line).map { NumberPosition(rowIndex, it.range) }.toList()
    }

val Schematic.symbols: List<CharPosition>
    get() = findCharPositions { it.isSymbol() }

val Schematic.stars: List<CharPosition>
    get() = findCharPositions { it == '*' }

fun Schematic.findCharPositions(predicate: (Char) -> Boolean): List<CharPosition> =
    flatMapIndexed { rowIndex, line ->
        line.mapIndexedNotNull { columnIndex, char ->
            if (predicate(char)) CharPosition(rowIndex, columnIndex) else null
        }
    }

val Schematic.partNumbers: List<Int>
    get() = numbers
        .filter { number -> symbols.any { symbol -> symbol.isAdjecent(number) } }
        .map(::valueAt)

fun Schematic.adjecentNumbers(charPosition: CharPosition): List<NumberPosition> =
    numbers.filter { number -> charPosition.isAdjecent(number) }

val Schematic.gearRatios: List<Int>
    get() = stars
        .mapNotNull { star ->
            val adjecentNumbers = adjecentNumbers(star)
            if (adjecentNumbers.size == 2)
                valueAt(adjecentNumbers[0]) * valueAt(adjecentNumbers[1])
            else
                null
        }

fun Char.isSymbol(): Boolean =
    !(isDigit() || this == '.')

val numberRegex = """\d+""".toRegex()
