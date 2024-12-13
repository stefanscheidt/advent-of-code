package day13

// Point with Long

data class Point(val x: Long, val y: Long)

operator fun Point.unaryMinus(): Point = copy(x = -x, y = -y)

operator fun Point.plus(other: Point): Point = copy(x = x + other.x, y = y + other.y)

operator fun Point.minus(other: Point): Point = plus(-other)

operator fun Int.times(point: Point): Point =
  point.copy(x = this * point.x, y = this * point.y)

fun p(x: Long, y: Long): Point = Point(x, y)

// Regex

val buttonRegex = Regex("""Button [A|B]: X\+(\d+), Y\+(\d+)""")
val prizeRegex = Regex("""Prize: X=(\d+), Y=(\d+)""")

// Button Presses

data class ButtonPresses(val a: Long, val b: Long) {
  fun tokensNeeded(): Long = a * 3 + b
}


// Behavior

data class Behavior(val a: Point, val b: Point, val prize: Point) {
  fun findButtonPresses(): ButtonPresses? {
    // winning condition:
    // pressA * a.x + pressB * b.x = prizeX
    // pressA * a.y + pressB * b.y = prizeY

    // has only one solution:
    val pressA = (prize.x * b.y - prize.y * b.x) / (a.x * b.y - a.y * b.x)
    val pressB = (prize.x - a.x * pressA) / b.x

    // actually winning?
    val winning = pressA * a.x + pressB * b.x == prize.x && pressA * a.y + pressB * b.y == prize.y

    return if (winning) ButtonPresses(pressA, pressB) else null
  }

}

fun String.intoBehavior(): Behavior? {
  fun intoPoint(s: String, regex: Regex): Point? =
    regex.find(s)?.groupValues?.let { (_, x, y) -> p(x.toLong(), y.toLong()) }

  val (a, b, prize) = split("\n")
  val pA = intoPoint(a, buttonRegex) ?: return null
  val pB = intoPoint(b, buttonRegex) ?: return null
  val pPrize = intoPoint(prize, prizeRegex) ?: return null
  return Behavior(pA, pB, pPrize)
}

// Part 1

fun part1(input: String): String {
  val behaviors = input.split("\n\n").mapNotNull { it.intoBehavior() }
  return behaviors
    .mapNotNull(Behavior::findButtonPresses)
    .sumOf(ButtonPresses::tokensNeeded)
    .toString()
}

// Part 2

fun part2(input: String): String {
  val behaviors = input.split("\n\n").mapNotNull(String::intoBehavior)
  val prizeOffset = p(10000000000000L, 10000000000000L)
  return behaviors
    .map { it.copy(prize = it.prize + prizeOffset) }
    .mapNotNull(Behavior::findButtonPresses)
    .sumOf(ButtonPresses::tokensNeeded)
    .toString()
}
