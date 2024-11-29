package day24

import common.geom.Point3D
import common.geom.p3
import common.io.inputFile
import java.io.File

val file = inputFile("day24.txt")

fun main() {
  val solution = solvePuzzle(file)

  println("1: ${solution.first}")
  println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<String, String> {
  val hailstones = file.readLines().filter(String::isNotBlank).map(::parseHailstone)
  val range = (200000000000000L..400000000000000L)

  return Pair("${solvePartOne(hailstones, range)}", "")
}

fun solvePartOne(hailstones: List<Hailstone>, range: LongRange): Int {
  var count = 0
  for (i in (hailstones.indices.first until hailstones.indices.last)) {
    for (j in (i + 1..hailstones.indices.last)) {
      val p1 = hailstones[i].p
      val v1 = hailstones[i].v
      val p2 = hailstones[j].p
      val v2 = hailstones[j].v

      if (v1.x * v2.y == v1.y * v2.x) continue

      val l1 =
        (v2.y * (p1.x - p2.x) - v2.x * (p1.y - p2.y)).toDouble() /
          (v1.y * v2.x - v1.x * v2.y).toDouble()
      val l2 =
        (v1.y * (p2.x - p1.x) - v1.x * (p2.y - p1.y)).toDouble() /
          (v1.x * v2.y - v1.y * v2.x).toDouble()

      val ix = p1.x.toDouble() + l1 * v1.x.toDouble()
      val iy = p1.y.toDouble() + l1 * v1.y.toDouble()

      if (l1 < 0 || l2 < 0) continue

      if (ix inLongRange range && iy inLongRange range) count++
    }
  }

  return count
}

fun parseHailstone(input: String): Hailstone {
  val (p, v) = input.split(" @ ").map { pv -> pv.split(",").map { xyz -> xyz.trim().toLong() } }
  return Hailstone(p3(p[0], p[1], p[2]), p3(v[0], v[1], v[2]))
}

data class Hailstone(val p: Point3D, val v: Point3D)

infix fun Double.inLongRange(range: LongRange): Boolean = this >= range.first && this <= range.last
