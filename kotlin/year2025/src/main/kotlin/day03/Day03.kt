package day03

// Part 1

fun part1(input: List<String>): String {
  return input.sumOf { joltage(it, 2) }.toString()
}

// Part 2

fun part2(input: List<String>): String {
  return input.sumOf { joltage(it, 12) }.toString()
}

fun joltage(bank: String, numberOfBatteries: Int): Long =
  buildString {
      // Current position in the bank string
      var currentIndex = 0

      (numberOfBatteries downTo 1).forEach { remainingDigits ->
        // Calculate the furthest index we can search while still leaving enough digits
        val searchEndIndex = bank.length - remainingDigits

        // Find the maximum digit in the valid search window
        var maxDigit = bank[currentIndex]
        var maxDigitIndex = currentIndex
        for (index in currentIndex..searchEndIndex) {
          // use that we can compare digits as chars here
          if (bank[index] > maxDigit) {
            maxDigit = bank[index]
            maxDigitIndex = index
          }
        }

        // Add the maximum digit to result
        this.append(maxDigit)

        // move past found max digit in the bank
        currentIndex = maxDigitIndex + 1
      }
    }
    .toLong()
