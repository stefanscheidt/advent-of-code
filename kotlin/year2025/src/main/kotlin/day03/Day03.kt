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

/**
 * Computes the maximum joltage by selecting exactly [batteries] digits from [bank].
 *
 * Uses a greedy algorithm: for each position in the result, select the largest available
 * digit that still leaves enough remaining digits in the bank to complete the selection.
 *
 * Example: bank="234234234234278", batteries=12
 * - Position 0: Search indices 0-3 (need 12 more), find '4' at index 3 → result="4"
 * - Position 1: Search indices 4-5 (need 11 more), find '3' at index 4 → result="43"
 * - Continue until 12 digits selected → result="434234234278"
 */
fun joltage(bank: String, batteries: Int): Long {
  val result = StringBuilder()
  var currentIndex = 0 // Current position in the bank string

  repeat(batteries) { position ->
    // Calculate how many more digits we need after this one
    val remainingDigits = batteries - position
    // Calculate the furthest index we can search while still leaving enough digits
    // If we pick a digit at index i, we need at least (remainingDigits - 1) digits after it
    val searchEndIndex = bank.length - remainingDigits + 1

    // Find the maximum digit in the valid search window
    var maxDigit = bank[currentIndex]
    var maxDigitIndex = currentIndex

    for (i in currentIndex until searchEndIndex) {
      if (bank[i] > maxDigit) {
        maxDigit = bank[i]
        maxDigitIndex = i
      }
    }

    // Add the maximum digit to result and move past it in the bank
    result.append(maxDigit)
    currentIndex = maxDigitIndex + 1
  }

  return result.toString().toLong()
}

