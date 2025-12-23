package day11

// Part 1

fun part1(input: List<String>): String = parseDevices(input).numberOfPaths("you", "out").toString()

// Part 2

fun part2(input: List<String>): String {
  val devices = parseDevices(input)

  val svrToDac = devices.numberOfPaths("svr", "dac")
  val dacToFft = devices.numberOfPaths("dac", "fft")
  val fftToOut = devices.numberOfPaths("fft", "out")

  val svrToFft = devices.numberOfPaths("svr", "fft")
  val fftToDac = devices.numberOfPaths("fft", "dac")
  val dacToOut = devices.numberOfPaths("dac", "out")

  return ((svrToDac * dacToFft * fftToOut) + (svrToFft * fftToDac * dacToOut)).toString()
}

// Parsing

fun parseDevices(input: List<String>): Map<String, List<String>> =
  input.associate { line ->
    val device = line.substringBefore(": ")
    val outputs = line.substringAfter(": ").split(" ")
    device to outputs
  }

// Calculation

fun Map<String, List<String>>.numberOfPaths(
  source: String,
  target: String,
  cache: MutableMap<String, Long> = mutableMapOf(),
): Long =
  if (source == target) 1L
  else
    cache.getOrPut(source) {
      get(source)?.sumOf { next -> numberOfPaths(next, target, cache) } ?: 0L
    }
