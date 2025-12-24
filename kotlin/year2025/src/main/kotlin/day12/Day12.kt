package day12

// Domain

data class Region(val width: Int, val height: Int, val counts: List<Int>) {
  val size: Int = width * height
}

// Part 1

fun part1(input: String): String {
  val inputBlocks = input.split("\n\n")
  val inputShapes = inputBlocks.dropLast(1)
  val inputRegions = inputBlocks.last().lines()

  val shapes = inputShapes.map { inputShape -> inputShape.count { it == '#' } }

  val regions =
    inputRegions.map { line ->
      val (inputSize, inputCounts) = line.split(": ")
      val (width, height) = inputSize.split("x").map(String::toInt)
      val counts = inputCounts.split(" ").map(String::toInt)
      Region(width, height, counts)
    }

  return regions
    .count { region ->
      region.counts.zip(shapes).sumOf { (count, shape) -> count * shape } <= region.size
    }
    .toString()
}
