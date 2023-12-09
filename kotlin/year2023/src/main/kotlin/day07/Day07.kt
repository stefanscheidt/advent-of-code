package day07

import common.io.inputFile
import day07.HandType.FH
import day07.HandType.HC
import day07.HandType.K3
import day07.HandType.K4
import day07.HandType.K5
import day07.HandType.P1
import day07.HandType.P2
import java.io.File


val file = inputFile("day07.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<String, String> {
    val hands = parseHands(file.readText().lines().filter(String::isNotBlank))
    val solution1 = solveIt(hands, handComparator)
    val solution2 = solveIt(hands, handComparatorWithJoker)
    return Pair("$solution1", "$solution2")
}

fun parseHands(lines: List<String>): Map<String, Int> =
    lines.associate { line ->
        val (hand, bet) = line.split(" ")
        hand to bet.toInt()
    }

fun solveIt(hands: Map<String, Int>, comparator: Comparator<Hand>): Int =
    hands.entries
        .asSequence()
        .sortedWith { e1, e2 -> comparator.compare(e1.key, e2.key) }
        .mapIndexed { index, entry -> (index + 1) * entry.value }
        .sum()

const val Joker = 'J'

val cardStrengths = "23456789TJQKA".toCharArray()
    .mapIndexed { index, char -> char to index + 2 }
    .toMap()

val Char.strength: Int
    get() = cardStrengths[this] ?: 0

val cardStrengthsWithJoker = "J23456789TQKA".toCharArray()
    .mapIndexed { index, char -> char to index + 1 }
    .toMap()

val Char.strengthWithJoker: Int
    get() = cardStrengthsWithJoker[this] ?: 0

typealias Hand = String

enum class HandType(val strength: Int) {
    HC(0), P1(1), P2(2), K3(3), FH(4), K4(5), K5(6)
}

fun Hand.type(): HandType {
    val counts = groupingBy { it }.eachCount().values.sortedDescending()
    return when (counts) {
        listOf(5) -> K5
        listOf(4, 1) -> K4
        listOf(3, 2) -> FH
        listOf(3, 1, 1) -> K3
        listOf(2, 2, 1) -> P2
        listOf(2, 1, 1, 1) -> P1
        listOf(1, 1, 1, 1, 1) -> HC
        else -> error("invalid hand $this")
    }
}

fun Hand.typeWithJoker(): HandType {
    if (!contains(Joker)) return type()

    val counts = groupingBy { it }.eachCount().values.sortedDescending()
    return when (counts) {
        listOf(5) -> K5
        listOf(4, 1) -> K5
        listOf(3, 2) -> K5
        listOf(3, 1, 1) -> K4
        listOf(2, 2, 1) -> if (count { it == Joker } == 2) K4 else FH
        listOf(2, 1, 1, 1) -> K3
        listOf(1, 1, 1, 1, 1) -> P1
        else -> error("invalid hand $this")
    }
}

val handComparator: Comparator<Hand> =
    Comparator { h1, h2 ->
        val s1 = h1.type().strength
        val s2 = h2.type().strength
        if (s1 != s2) {
            s1.compareTo(s2)
        } else {
            h1.zip(h2)
                .find { it.first != it.second }
                ?.let { it.first.strength.compareTo(it.second.strength) }
                ?: 0
        }
    }

val handComparatorWithJoker: Comparator<Hand> =
    Comparator { h1, h2 ->
        val s1 = h1.typeWithJoker().strength
        val s2 = h2.typeWithJoker().strength
        if (s1 != s2) {
            s1.compareTo(s2)
        } else {
            h1.zip(h2)
                .find { it.first != it.second }
                ?.let { it.first.strengthWithJoker.compareTo(it.second.strengthWithJoker) }
                ?: 0
        }
    }
