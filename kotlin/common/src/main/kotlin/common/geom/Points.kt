package common.geom

import kotlin.math.sqrt

data class Point2D(val x: Int, val y: Int)

operator fun Point2D.unaryMinus(): Point2D = copy(x = -x, y = -y)

operator fun Point2D.plus(other: Point2D): Point2D = copy(x = x + other.x, y = y + other.y)

operator fun Point2D.minus(other: Point2D): Point2D = plus(-other)

operator fun Int.times(point2D: Point2D): Point2D =
  point2D.copy(x = this * point2D.x, y = this * point2D.y)

fun p2(x: Int, y: Int): Point2D = Point2D(x, y)

val origin2D = Point2D(0, 0)

val cardinalDirections = listOf(Point2D(0, -1), Point2D(1, 0), Point2D(0, 1), Point2D(-1, 0))

val Point2D.cardinalNeighbors: List<Point2D>
  get() = cardinalDirections.map { this + it }

data class Point3D(val x: Long, val y: Long, val z: Long)

operator fun Point3D.unaryMinus(): Point3D = copy(x = -x, y = -y, z = -z)

operator fun Point3D.plus(other: Point3D): Point3D =
  copy(x = x + other.x, y = y + other.y, z = z + other.z)

operator fun Point3D.minus(other: Point3D): Point3D = plus(-other)

operator fun Int.times(point3D: Point3D): Point3D =
  point3D.copy(x = this * point3D.x, y = this * point3D.y, z = this * point3D.z)

val origin3D = Point3D(0L, 0L, 0L)

fun p3(x: Long, y: Long, z: Long): Point3D = Point3D(x, y, z)

fun euclideanDistance(p1: Point3D, p2: Point3D): Double {
  val dx = (p2.x - p1.x).toDouble()
  val dy = (p2.y - p1.y).toDouble()
  val dz = (p2.z - p1.z).toDouble()
  return sqrt(dx * dx + dy * dy + dz * dz)
}
