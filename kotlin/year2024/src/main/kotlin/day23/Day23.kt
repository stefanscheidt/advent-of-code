package day23

// Part 1

typealias Connection = Set<String>

fun List<String>.toConnectionsSet(): Set<Connection> =
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
    .toConnectionsSet()
    .triples()
    .count { t -> t.any { c -> c.startsWith("t") } }
    .toString()

// Part 2

typealias Connections = Map<String, Set<String>>

fun List<String>.toConnectionsMap(): Connections =
  map { it.split("-") }
    .flatMap { (a, b) -> listOf(a to b, b to a) }
    .groupBy({ it.first }, { it.second })
    .mapValues { it.value.toSet() }

// https://en.wikipedia.org/wiki/Bron%E2%80%93Kerbosch_algorithm
fun Connections.largestClique(
  p: Set<String>,
  r: Set<String> = emptySet(),
  x: Set<String> = emptySet()
): Set<String> =
  if (p.isEmpty() && x.isEmpty()) {
    r
  } else {
    val nodeWithLargestNeighborhood = (p + x).maxBy { node -> getValue(node).size }
    val largestNeighborhood = getValue(nodeWithLargestNeighborhood)
    p.minus(largestNeighborhood)
      .map { node ->
        largestClique(
          p.intersect(getValue(node)),
          r + node,
          x.intersect(getValue(node)),
        )
      }
      .maxBy { it.size }
  }

fun part2(input: List<String>): String {
  val connections = input.toConnectionsMap()
  return connections.largestClique(connections.keys).sorted().joinToString(",")
}
