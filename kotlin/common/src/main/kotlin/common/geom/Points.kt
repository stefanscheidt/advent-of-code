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
