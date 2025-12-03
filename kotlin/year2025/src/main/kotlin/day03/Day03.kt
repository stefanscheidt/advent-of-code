package day03

// Part 1

fun part1(input: List<String>): String {
  return input.sumOf(::maxJoltage1).toString()
}

// Part 2

fun part2(input: List<String>): String {
  return "TODO2"
}

fun maxJoltage1(bank: String): Int =
  (0..<bank.length - 1)
    .flatMap { firstIndex ->
      (firstIndex + 1..<bank.length).map { secondIndex ->
        val firstDigit = bank[firstIndex]
        val secondDigit = bank[secondIndex]
        "$firstDigit$secondDigit".toInt()
      }
    }
    .max()
