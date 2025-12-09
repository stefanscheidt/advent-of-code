package day08

import common.geom.p3
import common.io.inputFile
import common.io.readNonBlankLines
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day08Test {

  private val sample =
    """
      162,817,812
      57,618,57
      906,360,560
      592,479,940
      352,342,300
      466,668,158
      542,29,236
      431,825,988
      739,650,466
      52,470,668
      216,146,977
      819,987,18
      117,168,530
      805,96,715
      346,949,466
      970,615,88
      941,993,340
      862,61,35
      984,92,344
      425,690,689
    """
      .trimIndent()

  @Test
  fun `merge connection with empty set of circuits`() {
    merge(emptySet(), PointPair(p3(1, 1, 1), p3(2, 2, 2))) shouldBe
      setOf(setOf(p3(1, 1, 1), p3(2, 2, 2)))
  }

  @Test
  fun `merge connection with disjunct set of circuits`() {
    val circuits = setOf(setOf(p3(1, 1, 1), p3(2, 2, 2)), setOf(p3(3, 3, 3), p3(4, 4, 4)))
    val connection = PointPair(p3(5, 5, 5), p3(6, 6, 6))

    merge(circuits, connection) shouldBe
      setOf(
        setOf(p3(1, 1, 1), p3(2, 2, 2)),
        setOf(p3(3, 3, 3), p3(4, 4, 4)),
        setOf(p3(5, 5, 5), p3(6, 6, 6)),
      )
  }

  @Test
  fun `merge connection with set of circuits with one common point`() {
    val circuits = setOf(setOf(p3(1, 1, 1), p3(2, 2, 2)), setOf(p3(3, 3, 3), p3(4, 4, 4)))
    val connection = PointPair(p3(2, 2, 2), p3(5, 5, 5))

    merge(circuits, connection) shouldBe
      setOf(setOf(p3(1, 1, 1), p3(2, 2, 2), p3(5, 5, 5)), setOf(p3(3, 3, 3), p3(4, 4, 4)))
  }

  @Test
  fun `merge connection with set of circuits containing connection`() {
    val circuits =
      setOf(setOf(p3(1, 1, 1), p3(2, 2, 2), p3(3, 3, 3)), setOf(p3(4, 4, 4), p3(5, 5, 5)))
    val connection = PointPair(p3(1, 1, 1), p3(3, 3, 3))

    merge(circuits, connection) shouldBe
      setOf(setOf(p3(1, 1, 1), p3(2, 2, 2), p3(3, 3, 3)), setOf(p3(4, 4, 4), p3(5, 5, 5)))
  }

  @Test
  fun `merge connection connecting two circuits with set of circuits`() {
    val circuits = setOf(setOf(p3(1, 1, 1), p3(2, 2, 2)), setOf(p3(3, 3, 3), p3(4, 4, 4)))
    val connection = PointPair(p3(2, 2, 2), p3(3, 3, 3))

    merge(circuits, connection) shouldBe
      setOf(setOf(p3(1, 1, 1), p3(2, 2, 2), p3(3, 3, 3), p3(4, 4, 4)))
  }

  @Test
  fun `solve part one with sample input`() {
    part1(sample.lines(), numberOfConnections = 10) shouldBe "40"
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day08.txt").readNonBlankLines()
    part1(input) shouldBe "163548"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample.lines()) shouldBe "25272"
  }

  @Test
  fun `solve part two`() {
    val input = inputFile("day08.txt").readNonBlankLines()
    part2(input) shouldBe "772452514"
  }
}
