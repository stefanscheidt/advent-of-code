package common.geom


data class Point2D(val x: Int, val y: Int)

operator fun Point2D.unaryMinus(): Point2D =
    copy(x = -x, y = -y)

operator fun Point2D.plus(other: Point2D): Point2D =
    copy(x = x + other.x, y = y + other.y)

operator fun Point2D.minus(other: Point2D): Point2D =
    plus(-other)

operator fun Int.times(point2D: Point2D): Point2D =
    point2D.copy(x = this * point2D.x, y = this *  point2D.y)

val origin2D = Point2D(0, 0)

fun p2(x: Int, y: Int): Point2D =
    Point2D(x, y)

data class Point3D(val x: Int, val y: Int, val z: Int)

operator fun Point3D.unaryMinus(): Point3D =
    copy(x = -x, y = -y, z = -z)

operator fun Point3D.plus(other: Point3D): Point3D =
    copy(x = x + other.x, y = y + other.y, z = z + other.z)

operator fun Point3D.minus(other: Point3D): Point3D =
    plus(-other)

operator fun Int.times(point3D: Point3D): Point3D =
    point3D.copy(x = this * point3D.x, y = this *  point3D.y, z = this * point3D.z)

val origin3D = Point3D(0, 0, 0)

fun p3(x: Int, y: Int, z:Int): Point3D =
    Point3D(x, y, z)
