package day13

import com.github.h0tk3y.betterParse.combinators.and
import com.github.h0tk3y.betterParse.combinators.asJust
import com.github.h0tk3y.betterParse.combinators.map
import com.github.h0tk3y.betterParse.combinators.or
import com.github.h0tk3y.betterParse.combinators.separatedTerms
import com.github.h0tk3y.betterParse.combinators.skip
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.grammar.parseToEnd
import com.github.h0tk3y.betterParse.grammar.parser
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken
import com.github.h0tk3y.betterParse.parser.Parser
import common.io.inputFile
import java.io.File

val file = inputFile("day13.txt")

fun main() {
  val solution = solvePuzzle(file)

  println("1: ${solution.first}")
  println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<Int, Int> {
  val packets = parseInput(file.readLines())

  return Pair(solvePartOne(packets), solvePartTwo(packets))
}

fun solvePartOne(packets: List<Packet>): Int =
  packets.chunked(2).mapIndexed { index, chunk -> if (chunk[0] < chunk[1]) index + 1 else 0 }.sum()

fun solvePartTwo(packets: List<Packet>): Int {
  val divider2 = ListPacket(listOf(IntPacket(2).toListPacket()))
  val divider6 = ListPacket(listOf(IntPacket(6).toListPacket()))
  val sortedPackets = (packets + listOf(divider2, divider6)).sorted()
  return (sortedPackets.indexOf(divider2) + 1) * (sortedPackets.indexOf(divider6) + 1)
}

fun parseInput(input: List<String>): List<Packet> =
  input.filter { it.isNotEmpty() }.map(PacketGrammar::parseToEnd)

sealed class Packet : Comparable<Packet>

data class IntPacket(val value: Int) : Packet() {
  fun toListPacket(): ListPacket = ListPacket(listOf(this))

  override fun compareTo(other: Packet): Int =
    when (other) {
      is IntPacket -> value.compareTo(other.value)
      is ListPacket -> toListPacket().compareTo(other)
    }
}

data class ListPacket(val elements: List<Packet>) : Packet() {
  override fun compareTo(other: Packet): Int {
    return when (other) {
      is IntPacket -> compareTo(other.toListPacket())
      is ListPacket ->
        elements.zip(other.elements).map { it.first.compareTo(it.second) }.firstOrNull { it != 0 }
          ?: elements.size.compareTo(other.elements.size)
    }
  }
}

object PacketGrammar : Grammar<Packet>() {

  private val comma by literalToken(",")
  private val lbracket by literalToken("[")
  private val rbracket by literalToken("]")
  private val num by regexToken("\\d+")

  private val number by num map { IntPacket(it.text.toInt()) }
  private val emptyList by (lbracket and rbracket) asJust ListPacket(emptyList())
  private val nonEmptyList by
    (skip(lbracket) and
      separatedTerms(number or parser(this::rootParser), comma) and
      skip(rbracket)) map { ListPacket(it) }

  override val rootParser: Parser<Packet> by emptyList or nonEmptyList
}
