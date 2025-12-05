package day05

import kotlin.math.max

// Part 1

fun part1(input: String): String {
  val (freshIdRangesInput, availableIdsInput) = splitInput(input)
  val freshIdRanges = parseRanges(freshIdRangesInput.lines())
  val availableIds = availableIdsInput.lines().map(String::toLong)
  return availableIds.count { id -> isFresh(id, freshIdRanges) }.toString()
}

private fun isFresh(id: Long, freshIdRanges: List<LongRange>): Boolean =
  freshIdRanges.any { range -> range.contains(id) }

// Part 2

fun part2(input: String): String {
  val freshIdRangesInput = splitInput(input).first()
  val freshIdRanges = parseRanges(freshIdRangesInput.lines())
  return freshIdRanges.merged().sumOf { it.last - it.first + 1 }.toString()
}

private fun List<LongRange>.merged(): List<LongRange> {
  val merged = mutableListOf<LongRange>()
  forEach { range ->
    if (merged.isEmpty()) {
      merged.add(range)
    } else {
      val lastMergedRange = merged.removeLast()
      merged.addAll(lastMergedRange.mergeWith(range))
    }
  }
  return merged
}

private fun LongRange.mergeWith(other: LongRange): List<LongRange> =
  if (last < other.first) {
    listOf(this, other)
  } else {
    listOf(first..max(last, other.last))
  }

// Helper

private fun splitInput(input: String): List<String> = input.trimEnd().split("\n\n")

private fun parseRanges(input: List<String>): List<LongRange> =
  input
    .map {
      val (start, end) = it.split("-").map(String::toLong)
      start..end
    }
    .sortedBy { it.first }
