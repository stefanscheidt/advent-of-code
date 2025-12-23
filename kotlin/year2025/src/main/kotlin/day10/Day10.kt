package day10

import common.collections.allSublists
import kotlin.math.min

// Domain

data class Wiring(val values: List<Int>) {
  val intValue: Int = values.fold(0) { acc, n -> acc + (1 shl n) }
}

data class IndicatorLights(val value: Int) {
  companion object {
    fun fromBits(bits: List<Boolean>): IndicatorLights =
      bits
        .map { bit -> if (bit) 1 else 0 }
        .foldIndexed(0) { idx, acc, b -> acc + (b shl idx) }
        .let(::IndicatorLights)
  }
}

@JvmInline
value class JoltageRequirement(val values: List<Int>) {

  fun allZero(): Boolean = values.all { it == 0 }

  fun toLights(): IndicatorLights = values.map { it % 2 != 0 }.let(IndicatorLights::fromBits)

  fun reduce(buttonPresses: List<Wiring>): JoltageRequirement? {
    val joltageAfterPresses = values.toMutableList()
    for (wiring in buttonPresses) {
      for (idx in wiring.values) {
        joltageAfterPresses[idx] = joltageAfterPresses[idx] - 1
      }
    }
    return if (joltageAfterPresses.any { it < 0 }) null else JoltageRequirement(joltageAfterPresses)
  }
}

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
  input.drop(1).dropLast(1).map { it == '#' }.let(IndicatorLights::fromBits)

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

fun part2(input: List<String>): String =
  input.map(::parseMachine).sumOf(::minimumPressesForJoltage).toString()

fun minimumPressesForJoltage(machine: Machine): Int {

  fun go(
    requirement: JoltageRequirement,
    possibleLights: Map<IndicatorLights, List<List<Wiring>>>,
  ): Int? {
    if (requirement.allZero()) return 0

    var result: Int? = null
    val candidatePresses = possibleLights[requirement.toLights()] ?: return null
    for (presses in candidatePresses) {
      val joltageAfterPresses = requirement.reduce(presses) ?: continue
      val halfTargetJoltage = joltageAfterPresses.values.map { it / 2 }.let(::JoltageRequirement)
      val minimumPressesForHalf = go(halfTargetJoltage, possibleLights) ?: continue
      val minimumPresses = presses.size + 2 * minimumPressesForHalf

      result = min(result ?: minimumPresses, minimumPresses)
    }

    return result
  }

  val possibleLights = possibleIndicatorLights(machine.wirings)
  return go(machine.targetJoltages, possibleLights) ?: 0
}
