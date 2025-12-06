package day06

// Part 1

fun part1(input: List<String>): String {
  val operations = input.last().trim().split("\\s+".toRegex())
  val values =
    input.dropLast(1).map { line -> line.trim().split("\\s+".toRegex()).map(String::toLong) }
  val initial = operations.map { op -> if (op == "*") 1L else 0L }
  val fold =
    values.fold(initial) { acc, row ->
      acc.mapIndexed { index, n ->
        if (operations[index] == "*") n * row[index] else n + row[index]
      }
    }

  return fold.sum().toString()
}

// Part 2

fun part2(input: List<String>): String {
  val results = mutableListOf<Long>()
  val operands = mutableListOf<Long>()
  for (column in input.first().length - 1 downTo 0) {
    val operand = operand(input, column) ?: continue
    operands.add(operand)

    when (operation(input, column)) {
      '+' -> {
        results.add(operands.sum())
        operands.clear()
      }
      '*' -> {
        results.add(operands.reduce { acc, operand -> acc * operand })
        operands.clear()
      }
    }
  }

  return results.sum().toString()
}

private fun operand(input: List<String>, column: Int): Long? =
  buildString {
      for (row in input.indices) {
        val c = input[row][column]
        when {
          c.isDigit() -> append(c)
        }
      }
    }
    .takeIf { it.isNotEmpty() }
    ?.toLong()

private fun operation(input: List<String>, column: Int): Char = input.last()[column]
