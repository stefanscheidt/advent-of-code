package day10

import common.collections.allSublists
import kotlin.collections.drop
import kotlin.collections.dropLast
import kotlin.collections.map
import kotlin.text.drop

// Domain

@JvmInline
value class Wiring(val wiring: List<Int>) {
  val intValue: Int
    get() = wiring.fold(0) { acc, n -> acc + (1 shl n) }
}

@JvmInline value class IndicatorLights(val value: Int)

@JvmInline value class JoltageRequirement(val requirement: List<Int>)

data class Machine(
  val wirings: List<Wiring>,
  val targetLights: IndicatorLights,
  val targetJoltages: JoltageRequirement,
)

fun pressButtons(wirings: List<Wiring>): IndicatorLights =
  if (wirings.isEmpty()) IndicatorLights(0)
  else wirings.map(Wiring::intValue).reduce { acc, n -> acc xor n }.let(::IndicatorLights)

fun possibleIndicatorLights(wirings: List<Wiring>): Map<IndicatorLights, List<List<Wiring>>> =
  wirings.allSublists().groupBy(::pressButtons)

// Parsing

private fun parseWiring(input: String): Wiring =
  input.drop(1).dropLast(1).split(",").map(String::toInt).let(::Wiring)

fun parseIndicatorLights(input: String): IndicatorLights =
  input
    .drop(1)
    .dropLast(1)
    .map { (if (it == '#') 1 else 0) }
    .foldIndexed(0) { idx, acc, b -> acc + (b shl idx) }
    .let(::IndicatorLights)

fun parseJoltageRequirement(input: String): JoltageRequirement =
  input.drop(1).dropLast(1).split(",").map(String::toInt).let(::JoltageRequirement)

fun parseMachine(input: String): Machine {
  val parts = input.split(" ")
  val wirings = parts.drop(1).dropLast(1).map(::parseWiring)
  val targetLights = parseIndicatorLights(parts.first())
  val targetJoltageRequirement = parseJoltageRequirement(parts.last())
  return Machine(wirings, targetLights, targetJoltageRequirement)
}

// Part 1

fun part1(input: List<String>): String =
  input.map(::parseMachine).sumOf(::minimumPressesForLights).toString()

fun minimumPressesForLights(machine: Machine): Int {
  val possibleLights = possibleIndicatorLights(machine.wirings)
  return possibleLights[machine.targetLights]?.minOf(List<Wiring>::size) ?: 0
}

// Part 2

fun part2(input: List<String>): String {
  return "TODO2"
}
