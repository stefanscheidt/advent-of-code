package day19

fun String.isPossibleArragement(patterns: List<String>): Boolean {
  if (isEmpty()) return true

  return patterns.filter { startsWith(it) }.any {
    removePrefix(it).isPossibleArragement(patterns)
  }
}

class Designer(private val patterns: List<String>) {
  private val cache = mutableMapOf<String, Long>()

  fun countArrangements(design: String): Long {
    return if (design.isEmpty()) {
      1
    } else {
      cache.getOrPut(design) {
        patterns.filter { pattern -> design.startsWith(pattern) }
          .sumOf { pattern -> countArrangements(design.removePrefix(pattern)) }
      }
    }
  }


}

// Part 1

fun part1(input: List<String>): String {
  val patterns = input.first().split(", ")
  val designs = input.drop(2)

  return designs.count { it.isPossibleArragement(patterns) }.toString()
}

// Part 2

fun part2(input: List<String>): String {
  val patterns = input.first().split(", ")
  val designer = Designer(patterns)
  val designs = input.drop(2)

  return designs.sumOf(designer::countArrangements).toString()
}
