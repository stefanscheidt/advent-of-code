package day10

// Part 1

fun part1(input: List<String>): String =
  input.mapNotNull(::processLine1).sumOf(List<Int>::size).toString()

fun processLine1(line: String): List<Int>? {
  val machine = line.split(" ")
  val target = targetAsBits(machine).foldIndexed(0) { idx, acc, b -> acc + (b shl idx) }
  // If we interpret the button schematics as indexes of bits with value one,
  // then each button has an Int value `n`. Pressing the button then means apply `xor n`
  // to the current value of indicator lights (also interpreted as a binary number).
  // As `xor` is commutative and associative and `n xor n == 0`, it's sufficient
  // to check only all sublists of the list of button values
  val buttonValues = buttonSchematics(machine).map(::foldToInt)
  val sublists = allSublists(buttonValues).sortedBy { it.size }
  return sublists.filter { it.isNotEmpty() }.find { pressButtons(it) == target }
}

private fun targetAsBits(machine: List<String>): List<Int> =
  machine.first().drop(1).takeWhile { it != ']' }.map { (if (it == '#') 1 else 0) }

private fun buttonSchematics(machine: List<String>): List<List<Int>> =
  machine.drop(1).dropLast(1).map { it.drop(1).dropLast(1).split(",").map(String::toInt) }

private fun foldToInt(ints: List<Int>): Int = ints.fold(0) { acc, n -> acc + (1 shl n) }

private fun pressButtons(buttonValues: List<Int>): Int = buttonValues.reduce { acc, n -> acc xor n }

// Part 2

fun part2(input: List<String>): String {
  return "TODO2"
}

// Helper

fun <T> allSublists(list: List<T>): List<List<T>> {
  if (list.isEmpty()) return listOf(emptyList())

  return buildList {
    val previous = allSublists(list.dropLast(1))
    val last = list.last()
    addAll(previous)
    addAll(previous.map { it + last })
  }
}
