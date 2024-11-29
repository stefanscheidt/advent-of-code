package day11

import common.io.inputFile
import java.io.File

val file = inputFile("day11.txt")

fun main() {
  val solution = solvePuzzle(file)

  println("1: ${solution.first}")
  println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<Long, Long> {
  val monkeys1 = parseInput(file.readText())
  repeat(20) { monkeys1.tick(worryLevelManagement = ({ it / 3 })) }

  val monkeys2 = parseInput(file.readText())
  val lcd = monkeys2.lowestCommonTest()
  repeat(10_000) { monkeys2.tick(worryLevelManagement = ({ it % lcd })) }

  return Pair(monkeys1.business(), monkeys2.business())
}

class Monkey(
  val items: MutableList<Long>,
  val operation: (Long) -> Long,
  val test: Long,
  val ifTrue: Int,
  val ifFalse: Int,
) {
  var inspections: Long = 0

  fun inspectItems(manageWorryLevel: (Long) -> Long = ({ it / 3L })): List<Pair<Int, Long>> =
    buildList {
      items.forEach { item ->
        val worryLevel = manageWorryLevel(operation(item))
        val target = if (worryLevel % test == 0L) ifTrue else ifFalse
        add(target to worryLevel)
      }
      inspections += items.size
      items.clear()
    }
}

fun parseInput(input: String): List<Monkey> {
  val blocks = input.split("\n\n")
  return blocks.map { parseMonkey(it) }
}

fun parseMonkey(input: String): Monkey {
  val lines = input.lines()
  val items = lines[1].substringAfter(": ").split(", ").map { it.toLong() }
  val operation = parseOperation(lines[2])
  val test = lines[3].substringAfter("by ").toLong()
  val ifTrue = lines[4].substringAfter("monkey ").toInt()
  val ifFalse = lines[5].substringAfter("monkey ").toInt()
  return Monkey(items.toMutableList(), operation, test, ifTrue, ifFalse)
}

fun parseOperation(input: String): (Long) -> Long {
  val operation = input.substringAfter(": ")
  return when {
    operation == "new = old * old" -> ({ it * it })
    operation.startsWith("new = old + ") -> {
      val operand = operation.substringAfter("+ ").toLong()
      ({ it + operand })
    }
    operation.startsWith("new = old * ") -> {
      val operand = operation.substringAfter("* ").toLong()
      ({ it * operand })
    }
    else -> throw IllegalArgumentException("invalid operation $operation")
  }
}

fun List<Monkey>.tick(worryLevelManagement: (Long) -> Long = ({ it / 3L })) {
  forEach { monkey ->
    monkey.inspectItems(worryLevelManagement).forEach {
      val (target, worryLevel) = it
      this[target].items.add(worryLevel)
    }
  }
}

fun List<Monkey>.lowestCommonTest(): Long = map { it.test }.reduce(Long::times)

fun List<Monkey>.business(): Long {
  val (inspections1, inspection2) =
    sortedByDescending { it.inspections }.map { it.inspections }.take(2)
  return inspections1 * inspection2
}
