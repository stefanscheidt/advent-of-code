package day09

// Part 1

fun part1(input: String): String {
  val disk = buildList {
    input.chunked(2).forEachIndexed { index, pair ->
      val used = pair.firstOrNull()?.digitToInt() ?: 0
      val free = pair.drop(1).firstOrNull()?.digitToInt() ?: 0
      repeat(used) {
        add(index.toLong())
      }
      repeat(free) {
        add(null)
      }
    }
  }.toMutableList()

  val emptyBlocks = disk.indices.filter { disk[it] == null }.toMutableList()
  val checksum = disk.withIndex().reversed().sumOf { (index, value) ->
    if (value != null) {
      value * (emptyBlocks.removeFirstOrNull() ?: index)
    } else {
      emptyBlocks.removeLastOrNull()
      0
    }
  }

  return "$checksum"
}

// Part 2

fun part2(input: String): String {
  return "TODO2"
}
