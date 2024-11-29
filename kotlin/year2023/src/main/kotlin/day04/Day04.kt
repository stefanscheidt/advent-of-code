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
  return Pair("${cards.sumOf(Card::points)}", "${scratchcardsWon(cards)}")
}

fun parseInput(input: String): List<Card> =
  input.lines().filter(String::isNotBlank).map(::parseCard)

fun parseCard(input: String): Card {
  val cardRegex = "Card\\s+(?<id>\\d+)".toRegex()
  val id =
    cardRegex.find(input.substringBefore(":"))?.groups?.get("id")?.value?.toInt()
      ?: error("invalid card [$input]")

  val numbers = input.substringAfter(":")
  val whitespaceRegex = "\\s+".toRegex()
  val winnig = numbers.substringBefore("|").trim().split(whitespaceRegex).map(String::toInt)
  val mine = numbers.substringAfter("|").trim().split(whitespaceRegex).map(String::toInt)

  return Card(id, winnig.toSet(), mine.toSet())
}

data class Card(val id: Int, val winning: Set<Int>, val mine: Set<Int>)

val Card.myWinning: List<Int>
  get() = mine.intersect(winning).toList()

val Card.points: Int
  get() = if (myWinning.isEmpty()) 0 else (2.0).pow((myWinning.size - 1).toDouble()).toInt()

fun scratchcardsWon(cards: List<Card>): Int {
  val cardsById = cards.associateBy { it.id }
  val quantitiesById = cardsById.keys.associateWith { 1 }.toMutableMap()
  cards
    .sortedBy { it.id }
    .forEach { card ->
      (card.id + 1..card.id + card.myWinning.size).forEach { id ->
        quantitiesById[id] = quantitiesById.getValue(id) + quantitiesById.getValue(card.id)
      }
    }

  return quantitiesById.values.sum()
}
