package day14

import common.geom.p2
import common.io.inputFile
import common.io.readNonBlankLines
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day14Test {

  private val sample =
    """
    p=0,4 v=3,-3
    p=6,3 v=-1,-3
    p=10,3 v=-1,2
    p=2,0 v=2,-1
    p=0,0 v=1,3
    p=3,0 v=-2,-2
    p=7,6 v=-1,-3
    p=3,0 v=-1,-2
    p=9,3 v=2,3
    p=7,3 v=-1,2
    p=2,4 v=2,-3
    p=9,5 v=-3,-3
    """
      .trimIndent()

  @Test
  fun `parse robot`() {
    val robot = "p=0,4 v=3,-3".toRobot()
    robot shouldBe Robot(p2(0, 4), p2(3, -3))
  }

  @Test
  fun `move robot`() {
    val robot = Robot(p2(2, 4), p2(2, -3))
    robot.move(times = 5, width = 11, hight = 7) shouldBe Robot(p2(1, 3), p2(2, -3))
  }

  @Test
  fun `solve part one with sample input`() {
    part1(input = sample.lines(), width = 11, hight = 7) shouldBe "12"
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day14.txt").readNonBlankLines()
    part1(input) shouldBe "228690000"
  }
}
