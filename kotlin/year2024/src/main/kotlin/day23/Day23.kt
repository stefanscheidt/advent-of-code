package day23

typealias Connection = Set<String>

fun List<String>.toConnections(): Set<Connection> =
  map { it.split("-").toSet() }.toSet()

fun Set<Connection>.triples(): Set<Connection> =
  flatMap { con ->
    val (a, b) = con.toList()
    this.asSequence()
      .filter { it.contains(a) }
      .map { it - a }
      .filter { contains(it + b) }
      .map { it + a + b }
      .toSet()
  }.toSet()

// Part 1

fun part1(input: List<String>): String =
  input
    .toConnections()
    .triples()
    .count { t -> t.any { c -> c.startsWith("t") } }
    .toString()

// Part 2

fun part2(input: List<String>): String {
  return "TODO2"
}
