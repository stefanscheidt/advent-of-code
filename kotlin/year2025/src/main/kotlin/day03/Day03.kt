package day03

// Part 1

fun part1(input: List<String>): String {
  return input.sumOf(::joltage1).toString()
}

// Part 2

fun part2(input: List<String>): String {
  return input.sumOf { joltage(it, 12) }.toString()
}

fun joltage1(bank: String): Int =
  (0..<bank.length - 1)
    .flatMap { firstIndex ->
      (firstIndex + 1..<bank.length).map { secondIndex ->
        val firstDigit = bank[firstIndex]
        val secondDigit = bank[secondIndex]
        "$firstDigit$secondDigit".toInt()
      }
    }
    .max()

fun joltage(bank: String, batteries: Int): Long {
  val result = StringBuilder()
  var currentIndex = 0

  repeat(batteries) { position ->
    val remainingDigits = batteries - position
    val searchEndIndex = bank.length - remainingDigits + 1

    // Find the maximum digit in the valid range
    var maxDigit = bank[currentIndex]
    var maxDigitIndex = currentIndex

    for (i in currentIndex until searchEndIndex) {
      if (bank[i] > maxDigit) {
        maxDigit = bank[i]
        maxDigitIndex = i
      }
    }

    result.append(maxDigit)
    currentIndex = maxDigitIndex + 1
  }

  return result.toString().toLong()
}

