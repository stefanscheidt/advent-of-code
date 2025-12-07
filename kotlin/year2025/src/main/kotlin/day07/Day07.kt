package day07

// Part 1

fun part1(input: List<String>): String {
  val startX = findStart(input)
  val splitters = findSplitters(input)

  val initial = Pair(setOf(startX), 0)
  val splits =
    splitters
      .fold(initial) { (currentBeamXs, currentSplits), line ->
        val splitterXs = findSplitterXs(line)
        val newSplits = currentBeamXs.count { it in splitterXs }
        val nextBeamXs = splitBeams(currentBeamXs, splitterXs)
        Pair(nextBeamXs, currentSplits + newSplits)
      }
      .second

  return splits.toString()
}

// Part 2

fun part2(input: List<String>): String {
  val startX = findStart(input)
  val splitters = findSplitters(input)

  val particleXs =
    splitters.fold(mapOf(startX to 1L)) { acc, line ->
      buildMap {
        acc.keys.forEach { x ->
          val splitCount = acc.getValue(x)
          if (line[x] == '^') {
            put(x - 1, getOrDefault(x - 1, 0L) + splitCount)
            put(x + 1, getOrDefault(x + 1, 0L) + splitCount)
          } else {
            put(x, getOrDefault(x, 0L) + splitCount)
          }
        }
      }
    }

  return particleXs.values.sum().toString()
}

private fun findStart(input: List<String>): Int = input.first().indexOf('S')

private fun findSplitters(input: List<String>): List<String> =
  input.drop(1).filter { it.contains('^') }

private fun findSplitterXs(line: String): List<Int> =
  line.mapIndexedNotNull { i, c -> if (c == '^') i else null }

private fun splitBeams(currentBeamXs: Set<Int>, splitterXs: List<Int>): Set<Int> = buildSet {
  currentBeamXs.forEach { x ->
    if (x in splitterXs) {
      addAll(setOf(x - 1, x + 1))
    } else {
      add(x)
    }
  }
}
