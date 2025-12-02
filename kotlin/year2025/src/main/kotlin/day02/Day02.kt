package day02

import common.collections.allEqual

// Part 1

fun part1(input: List<String>): String = sumOfInvalidIds(input.first(), ::invalidIds1).toString()

fun invalidIds1(start: Long, end: Long): List<Long> = invalidIds(start, end, ::isInvalidId1)

fun isInvalidId1(id: String): Boolean {
  val length = id.length
  return length % 2 == 0 && id.take(length / 2) == id.substring(length / 2)
}

// Part 2

fun part2(input: List<String>): String = sumOfInvalidIds(input.first(), ::invalidIds2).toString()

fun invalidIds2(start: Long, end: Long): List<Long> = invalidIds(start, end, ::isInvalidId2)

fun isInvalidId2(id: String): Boolean =
  (1..id.length / 2).any { chunkSize -> id.chunked(chunkSize).allEqual() }

// Helper

fun sumOfInvalidIds(input: String, invalidIds: (Long, Long) -> List<Long>): Long =
  input
    .split(",")
    .flatMap {
      val (start, end) = it.split("-").map(String::toLong)
      invalidIds(start, end)
    }
    .sum()

fun invalidIds(start: Long, end: Long, isInvalidId: (String) -> Boolean): List<Long> =
  (start..end).map { "$it" }.filter(isInvalidId).map { it.toLong() }
