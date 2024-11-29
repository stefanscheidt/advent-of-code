package day19

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class Day19Test {

  private val sample =
    """
        px{a<2006:qkq,m>2090:A,rfg}
        pv{a>1716:R,A}
        lnx{m>1548:A,A}
        rfg{s<537:gd,x>2440:R,A}
        qs{s>3448:A,lnx}
        qkq{x<1416:A,crn}
        crn{x>2662:A,R}
        in{s<1351:px,qqz}
        qqz{s>2770:qs,m<1801:hdj,R}
        gd{a>3333:R,R}
        hdj{m>838:A,pv}

        {x=787,m=2655,a=1222,s=2876}
        {x=1679,m=44,a=2067,s=496}
        {x=2036,m=264,a=79,s=2244}
        {x=2461,m=1339,a=466,s=291}
        {x=2127,m=1623,a=2188,s=1013}
    """
      .trimIndent()

  @Test
  fun `parse rule`() {
    val rule1 = parseRule("rfg")
    rule1(Rating()) shouldBe Reference("rfg")

    val rule2 = parseRule("m>2090:A")
    rule2(Rating(m = 2090)) shouldBe null
    rule2(Rating(m = 2091)) shouldBe Accepted

    val rule3 = parseRule("a<2006:qkq")
    rule3(Rating(a = 2005)) shouldBe Reference("qkq")
    rule3(Rating(a = 2006)) shouldBe null
  }

  @Test
  fun `assess rating`() {
    val workflow = parseWorkflow("px{a<2006:qkq,m>2090:A,rfg}")

    workflow.assess(Rating(a = 0, m = 0)) shouldBe Reference("qkq")
    workflow.assess(Rating(a = 2006, m = 2091)) shouldBe Accepted
    workflow.assess(Rating(a = 2006, m = 2090)) shouldBe Reference("rfg")
  }

  @Test
  fun `solve part one with sample input`() {
    val (workflows, ratings) = parseInput(sample)

    solvePartOne(workflows, ratings) shouldBe 19114
  }

  @Test
  @Disabled
  fun `solve part two with sample input`() {
    TODO()
  }

  @Test
  fun `solve puzzle`() {
    val solution = solvePuzzle(file)

    solution.first shouldBe "368964"
    solution.second shouldBe ""
  }
}
