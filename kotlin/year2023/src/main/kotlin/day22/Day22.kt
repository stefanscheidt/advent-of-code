package day22

import common.io.inputFile
import java.io.File

val file = inputFile("day22.txt")

fun main() {
  val solution = solvePuzzle(file)

  println("1: ${solution.first}")
  println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<String, String> {
  val bricks = parseBricks(file.readLines().filter(String::isNotBlank))
  return Pair("${solvePartOne(bricks)}", "${solvePartTwo(bricks)}")
}

fun solvePartOne(bricks: List<Brick>): Int = bricks.settled().count { it.isSafeToDesintegrate() }

fun solvePartTwo(bricks: List<Brick>): Int {
  val settled = bricks.settled()
  return settled
    .filter { !it.isSafeToDesintegrate() }
    .parallelStream()
    .map { it.disintegrate(settled).size }
    .reduce(0, Int::plus)
}

fun parseBrick(input: String): Brick {
  val (fst, snd) = input.split("~").map { it.split(",").map(String::toInt) }
  return Brick(xs = (fst[0]..snd[0]), ys = (fst[1]..snd[1]), zs = (fst[2]..snd[2]))
}

fun parseBricks(input: List<String>): List<Brick> = input.map(::parseBrick).sortedBy { it.zs.first }

data class Brick(val xs: IntRange, val ys: IntRange, val zs: IntRange) {

  val supporting = mutableSetOf<Brick>()
  val supportedBy = mutableSetOf<Brick>()

  infix fun willSupport(other: Brick): Boolean =
    this overlapps other && zs.last < other.zs.first - 1

  infix fun supports(other: Brick): Boolean = this overlapps other && zs.last == other.zs.first - 1

  fun moveTo(z: Int): Brick = copy(zs = zs.moveTo(z))

  fun addSupporter(brick: Brick) {
    if (brick supports this) {
      brick.supporting.add(this)
      supportedBy.add(brick)
    }
  }

  fun isSafeToDesintegrate(): Boolean = supporting.all { it.supportedBy.size > 1 }

  /** @return Subset of passed in bricks that will fall when this gets disintegrated */
  fun disintegrate(bricks: List<Brick>): Set<Brick> {
    tailrec fun go(fallingBricks: Set<Brick>, otherBricks: Set<Brick>): Set<Brick> {
      val unsupportedBricks =
        otherBricks.filter { brick -> brick.zs.first > 1 && otherBricks.none { it supports brick } }
      return if (unsupportedBricks.isEmpty()) fallingBricks
      else go(fallingBricks + unsupportedBricks, otherBricks - fallingBricks)
    }

    return go(emptySet(), bricks.toSet() - this)
  }

  private infix fun overlapps(other: Brick): Boolean =
    xs overlapps other.xs && ys overlapps other.ys
}

fun List<Brick>.settled(): List<Brick> {
  val settledBricks = this.filter { it.zs.first == 1 }.toMutableList()
  val fallingBricks = this.filter { it.zs.first != 1 }

  fallingBricks.forEach { brick ->
    val settledBrick =
      if (settledBricks.any { it supports brick }) {
        brick
      } else {
        val maxZBelow =
          settledBricks.filter { it willSupport brick }.maxOfOrNull { it.zs.last } ?: 0
        brick.moveTo(maxZBelow + 1)
      }
    settledBricks.filter { it supports settledBrick }.forEach { settledBrick.addSupporter(it) }
    settledBricks.add(settledBrick)
  }

  return settledBricks
}

infix fun IntRange.overlapps(other: IntRange): Boolean = other.last >= first && other.first <= last

val IntRange.width: Int
  get() = last - first

fun IntRange.moveTo(x: Int) = (x..x + width)
