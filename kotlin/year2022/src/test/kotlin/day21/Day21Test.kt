package day21

import day21.Operator.*
import io.kotest.matchers.shouldBe
import kotlin.test.fail
import org.junit.jupiter.api.Test

class Day21Test {

  private val sample =
    """
        root: pppw + sjmn
        dbpl: 5
        cczh: sllz + lgvd
        zczc: 2
        ptdq: humn - dvpt
        dvpt: 3
        lfqf: 4
        humn: 5
        ljgn: 2
        sjmn: drzm * dbpl
        sllz: 4
        pppw: cczh / lfqf
        lgvd: ljgn * ptdq
        drzm: hmdt - zczc
        hmdt: 32
    """
      .trimIndent()

  @Test
  fun `parse number`() {
    when (val expression = parseExpression("dbpl: 5")) {
      is Operation -> fail()
      is Number -> expression shouldBe Number("dbpl", 5)
    }
  }

  @Test
  fun `parse operation`() {
    when (val expression = parseExpression("root: pppw + sjmn")) {
      is Operation -> {
        expression.id shouldBe "root"
        expression.leftId shouldBe "pppw"
        expression.rightId shouldBe "sjmn"
        expression.operator shouldBe Plus
      }
      is Number -> fail()
    }
  }

  @Test
  fun `evaluate operation`() {
    val left = Number("one", 1)
    val right = Number("right", 2)
    val operation =
      Operation("op", "ignored", "ignored", Plus).apply {
        this.left = left
        this.right = right
      }

    operation.eval() shouldBe 3
  }

  @Test
  fun `solve part one with sample input`() {
    solvePartOne(parseInput(sample.lines())) shouldBe 152
  }

  @Test
  fun `solve part two with sample input`() {
    solvePartTwo(parseInput(sample.lines())) shouldBe 301
  }

  @Test
  fun `solve puzzle`() {
    val solution = solvePuzzle(file)

    solution.first shouldBe 353837700405464L
    solution.second shouldBe 3678125408017L
  }
}
