package day25

import common.io.inputFile
import java.io.File


val file = inputFile("day25.txt")

fun main() {
    println("1: ${solvePuzzle(file)}")
}

fun solvePuzzle(file: File): String {
    val input = file.readLines()
    val sum = input.sumOf { snafuToDecimal(it) }
    return decimalToSnafu(sum)
}

typealias Snafu = String

fun snafuDigitToDecimal(digit: Char): Long =
    when(digit) {
        '=' -> -2L
        '-' -> -1L
        '0' -> 0L
        '1' -> 1L
        '2' -> 2L
        else -> error("invald SNAFU digit $digit")
    }

fun snafuToDecimal(snafu: Snafu): Long =
    snafu.fold(0) { acc, c -> acc * 5 + snafuDigitToDecimal(c) }

fun decimalDigitToSnafu(digit: Long): Snafu =
    when(digit) {
        0L -> "0"
        1L -> "1"
        2L -> "2"
        3L -> "="
        4L -> "-"
        else -> error("invald decimal digit $digit")
    }

fun decimalToSnafu(long: Long): Snafu =
    generateSequence(long) { (it + 2) / 5 }
        .takeWhile { it != 0L }
        .map { it % 5 }
        .map(::decimalDigitToSnafu)
        .joinToString("")
        .reversed()
