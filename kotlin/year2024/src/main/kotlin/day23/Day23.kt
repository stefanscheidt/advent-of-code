package day23

// Part 1

typealias Connection = Set<String>

fun List<String>.toConnections(): Set<Connection> =
  map { it.split("-").toSet() }.toSet()

fun Set<Connection>.triples(): Set<Set<String>> =
  flatMap { con ->
    val (a, b) = con.toList()
    this.asSequence()
      .filter { connection -> connection.contains(a) }
      .map { connection -> connection - a }
      .filter { singleElementSet -> this.contains(singleElementSet + b) }
      .map { singleElementSet -> singleElementSet + a + b }
      .toSet()
  }.toSet()

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
