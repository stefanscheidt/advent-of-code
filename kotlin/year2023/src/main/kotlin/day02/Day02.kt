package day02

import common.io.inputFile
import day02.Color.Blue
import day02.Color.Green
import day02.Color.Red
import java.io.File


val file = inputFile("day02.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<String, String> {
    val games = parseInput(file.readText())
    return Pair(solvePart1(games).toString(), solvePart2(games).toString())
}

fun parseInput(input: String): List<Game> =
    input.lines().filterNot(String::isBlank).map(::parseGame)

// example input:
// Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
fun parseGame(input: String): Game {
    val gameRegex = """^Game (\d+)$""".toRegex()
    val (id) = gameRegex.find(input.substringBefore(":"))?.destructured ?: error("invalid game [$input]")
    val hands = parseHands(input.substringAfter(":"))
    return Game(id.toInt(), hands)
}

// example input:
// 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
fun parseHands(input: String): List<Hand> =
    input.split(";").map(::parseHand)

// example input:
// 3 blue, 4 red
fun parseHand(input: String): Hand =
    buildMap {
        val cubesRegex = """^ (\d+) (red|green|blue)$""".toRegex()
        input.split(",").forEach { cubes ->
            val (amount, color) = cubesRegex.find(cubes)?.destructured ?: error("invald hand [$input]")
            put(colorOf(color), amount.toInt())
        }
    }

fun solvePart1(games: List<Game>): Int =
    games
        .filter { it.isPossible(red = 12, green = 13, blue = 14) }
        .sumOf { it.id }

fun solvePart2(games: List<Game>): Int =
    games.sumOf(Game::power)

enum class Color {
    Red, Green, Blue
}

fun colorOf(name: String): Color =
    when (name) {
        "red" -> Red
        "green" -> Green
        "blue" -> Blue
        else -> error("invalid color [$name]")
    }

typealias Hand = Map<Color, Int>

fun Hand.amountOf(color: Color): Int =
    this[color] ?: 0

fun List<Hand>.minOf(color: Color): Int =
    this.maxOf { it.amountOf(color) }

data class Game(val id: Int, val hands: List<Hand>) {

    fun isPossible(red: Int, green: Int, blue: Int): Boolean =
        hands.all { it.amountOf(Red) <= red && it.amountOf(Green) <= green && it.amountOf(Blue) <= blue }

    val power: Int
        get() = hands.minOf(Red) * hands.minOf(Green) * hands.minOf(Blue)

}
