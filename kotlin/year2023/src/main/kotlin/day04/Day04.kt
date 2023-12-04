package day04

import common.io.inputFile
import java.io.File
import kotlin.math.pow


val file = inputFile("day04.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<String, String> {
    val cards = parseInput(file.readText())
    return Pair("${cards.sumOf(Card::points)}", "")
}

fun parseInput(input: String): List<Card> =
    input.lines().filter(String::isNotBlank).map(::parseCard)

fun parseCard(input: String): Card {
    val numbers = input.substringAfter(":")
    val winnig = numbers.substringBefore("|").trim().split("\\s+".toRegex()).map(String::toInt)
    val mine = numbers.substringAfter("|").trim().split("\\s+".toRegex()).map(String::toInt)
    return Card(winnig, mine)
}

data class Card(val winning: List<Int>, val mine: List<Int>)

val Card.points: Int
    get() {
        val count = mine.count { it in winning }
        return if (count == 0) 0 else (2.0).pow((count - 1).toDouble()).toInt()
    }
