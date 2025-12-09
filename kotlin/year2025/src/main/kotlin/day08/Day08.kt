package day08

import common.collections.DisjointSet
import common.geom.Point3D
import common.geom.euclideanDistance
import common.geom.p3
import java.util.PriorityQueue

// Part 1

fun part1(input: List<String>, numberOfConnections: Int = 1000): String {
  val points = parseInput(input)
  val connections = connections(points).take(numberOfConnections)
  val circuits =
    connections
      .fold(emptySet<Set<Point3D>>()) { acc, connection -> merge(acc, connection) }
      .sortedByDescending { it.size }
      .take(3)

  return "${circuits.fold(1) { acc, circuit -> acc * circuit.size }}"
}

// Part 2

fun part2(input: List<String>): String {
  val points = parseInput(input)
  val circuits = DisjointSet<Point3D>().apply { addAll(points) }
  val connections = PriorityQueue<PointPair>().apply { addAll(connections(points)) }
  val finalConnection =
    generateSequence(connections.poll()) { connection ->
        circuits.connect(connection)
        connections.poll().takeIf { circuits.countSets() > 1 }
      }
      .last()

  return finalConnection.let { (p, q) -> p.x * q.x }.toString()
}

// Helper

fun parseInput(input: List<String>): List<Point3D> =
  input.map { line ->
    val (x, y, z) = line.split(",").map { it.toLong() }
    p3(x, y, z)
  }

data class PointPair(val p: Point3D, val q: Point3D) : Comparable<PointPair> {

  val distance = euclideanDistance(p, q)

  override fun compareTo(other: PointPair): Int = this.distance.compareTo(other.distance)
}

fun connections(points: List<Point3D>): List<PointPair> =
  buildList {
      for (i in points.indices) {
        for (j in i + 1 until points.size) {
          add(PointPair(points[i], points[j]))
        }
      }
    }
    .sorted()

fun merge(circuits: Set<Set<Point3D>>, connection: PointPair): Set<Set<Point3D>> = buildSet {
  val (containingConnectionEnds, notContainingConnectionEnds) =
    circuits.partition { connection.p in it || connection.q in it }
  addAll(notContainingConnectionEnds)
  add(
    buildSet {
      containingConnectionEnds.forEach(::addAll)
      add(connection.p)
      add(connection.q)
    }
  )
}

fun DisjointSet<Point3D>.connect(connection: PointPair) {
  union(connection.p, connection.q)
}
