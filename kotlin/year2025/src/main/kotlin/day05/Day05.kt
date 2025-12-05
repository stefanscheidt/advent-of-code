package day05

import kotlin.math.max

// Part 1

fun part1(input: String): String {
  val (freshIdRangesIn, availableIdsIn) = input.trimEnd().split("\n\n")
  val freshIdRanges = parseRanges(freshIdRangesIn.lines())
  val availableIds = availableIdsIn.lines().map(String::toLong)
  val freshIngrediensCount =
    availableIds.count { id -> freshIdRanges.any { range -> range.contains(id) } }

  return freshIngrediensCount.toString()
}

// Part 2

fun part2(input: String): String {
  val freshIdRangesIn = input.trimEnd().split("\n\n").first()
  val freshIdRanges = parseRanges(freshIdRangesIn.lines())
  val mergedRanges = buildList {
    freshIdRanges.forEach { range ->
      if (isEmpty()) add(range) else addAll(removeLast().mergeWith(range))
    }
  }

  return mergedRanges.sumOf { it.last - it.first + 1 }.toString()
}

// Helper

private fun parseRanges(input: List<String>): List<LongRange> =
  input
    .map {
      val (start, end) = it.split("-").map(String::toLong)
      start..end
    }
    .sortedBy { it.first }

private fun LongRange.mergeWith(other: LongRange): List<LongRange> =
  if (last < other.first) {
    listOf(this, other)
  } else {
    listOf(first..max(last, other.last))
  }
