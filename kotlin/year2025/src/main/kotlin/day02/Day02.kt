package day02

// Part 1

fun part1(input: List<String>): String {
  return input
    .first()
    .split(",")
    .flatMap {
      val (start, end) = it.split("-").map(String::toLong)
      invalidIds(start, end)
    }
    .sum()
    .toString()
}

// Part 2

fun part2(input: List<String>): String {
  return "TODO2"
}

fun invalidIds(start: Long, end: Long): List<Long> =
  (start..end)
    .map { "$it" }
    .mapNotNull { id ->
      val length = id.length
      if (length % 2 == 0 && id.take(length / 2) == id.substring(length / 2)) id else null
    }
    .map { it.toLong() }
