package day01

// Part 1

fun part1(input: List<String>): String {
  val fold = input.runningFold(50) { acc, line ->
    val direction = line.first()
    val value = line.drop(1).toInt()
    val update = when (direction) {
      'L' -> -value
      'R' -> value
      else -> 0
    }
    (acc + update + 100) % 100
  }
  val password = fold.count { it == 0 }
  
  return "$password"
}

// Part 2

fun part2(input: List<String>): String {
  val fold = input.runningFold(Pair(50, 0)) { acc, line ->
    val position = acc.first
    val direction = line.first()
    val value = line.drop(1).toInt()
    val update = when (direction) {
      'L' -> -value
      'R' -> value
      else -> 0
    }
    
    val passes = if (direction == 'L') {
      (position + update) / 100
    } else {
      0
    }
    
    Pair((position + update + 100) % 100, passes)
  }.also { println(it) }
  
  val password = fold.sumOf { it.second }

  return "$password"
}
