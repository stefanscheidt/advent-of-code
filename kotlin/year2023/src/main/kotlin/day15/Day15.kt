package day15

import common.io.inputFile
import java.io.File

val file = inputFile("day15.txt")

fun main() {
  val solution = solvePuzzle(file)

  println("1: ${solution.first}")
  println("2: ${solution.second}")
}

fun parseInput(input: String): List<String> = input.replace("\n", "").trim(',').split(',')

fun solvePuzzle(file: File): Pair<String, String> {
  val input = parseInput(file.readText().trim())
  val commands = input.mapNotNull(::parseCommand)
  return Pair("${solvePartOne(input)}", "${solvePartTwo(commands)}")
}

fun solvePartOne(input: List<String>): Int = input.sumOf { it.holidayHashCode() }

fun solvePartTwo(commands: List<Command>): Int {
  val boxes = List(256) { _ -> mutableListOf<Lens>() }
  boxes.apply(commands)
  return boxes.focusingPower
}

fun String.holidayHashCode(): Int = fold(0) { acc, c -> (acc + c.code) * 17 % 256 }

sealed interface Command {
  val label: String
}

data class AddOrReplace(override val label: String, val focalLength: Int) : Command

data class Remove(override val label: String) : Command

fun parseCommand(input: String): Command? {
  val commandRegex = """(?<label>\w+)(?<operation>[-=])(?<focalLength>\d)?""".toRegex()
  val match = commandRegex.matchEntire(input) ?: return null
  val label = match.groups["label"]?.value ?: return null
  val operation = match.groups["operation"]?.value ?: return null
  val focalLength = match.groups["focalLength"]?.value?.toInt()
  return when (operation) {
    "=" -> if (focalLength != null) AddOrReplace(label, focalLength) else null
    "-" -> Remove(label)
    else -> null
  }
}

data class Lens(val label: String, val focalLength: Int)

typealias Box = MutableList<Lens>

fun Box.apply(command: Command) {
  when (command) {
    is AddOrReplace -> {
      val index = indexOfFirst { it.label == command.label }
      val lens = Lens(command.label, command.focalLength)
      if (index == -1) {
        this.add(lens)
      } else {
        this[index] = lens
      }
    }
    is Remove -> {
      val index = indexOfFirst { it.label == command.label }
      if (index != -1) removeAt(index)
    }
  }
}

fun List<Box>.apply(commands: List<Command>) {
  commands.forEach { command ->
    val box = this[command.label.holidayHashCode()]
    box.apply(command)
  }
}

val List<Box>.focusingPower: Int
  get() =
    flatMapIndexed { boxIndex, box ->
        box.mapIndexed { lensIndex, lens -> (boxIndex + 1) * (lensIndex + 1) * lens.focalLength }
      }
      .sum()
