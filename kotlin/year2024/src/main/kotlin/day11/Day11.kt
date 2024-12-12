package day11

// Stone

typealias Stone = Long

// Game

class Game(private val stones: List<Stone>) {
  private val cache = mutableMapOf<Pair<Stone, Int>, Long>()

  fun countAfter(blinks: Int): Long {
    return stones.sumOf { count(it, blinks) }
  }

  private fun count(stone: Stone, blinks: Int): Long {
    val countAfterBlinks = Pair(stone, blinks)
    return if (blinks == 0) {
      1L
    } else if (countAfterBlinks in cache) {
      cache.getValue(countAfterBlinks)
    } else {
      val result =
        when {
          stone == 0L -> count(1, blinks - 1)
          stone.numberOfDigits % 2 == 0 -> {
            val (left, right) = stone.splitInHalf()
            count(left, blinks - 1) + count(right, blinks - 1)
          }

          else -> count(stone * 2024, blinks - 1)
        }
      cache[countAfterBlinks] = result
      result
    }
  }
}

fun gameOf(stones: List<Stone>): Game = Game(stones)

// Part 1

fun part1(input: List<String>): String {
  val stones = input.first().split(" ").map(String::toLong)
  return gameOf(stones).countAfter(blinks = 25).toString()
}

// Part 2

fun part2(input: List<String>): String {
  val stones = input.first().split(" ").map(String::toLong)
  return gameOf(stones).countAfter(blinks = 75).toString()
}

// Utils

val Long.numberOfDigits: Int
  get() = toString().length

fun Long.splitInHalf(): Pair<Long, Long> {
  val str = toString()
  return Pair(
    str.substring(0, numberOfDigits / 2).toLong(),
    str.substring(numberOfDigits / 2, numberOfDigits).toLong(),
  )
}
