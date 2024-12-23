package day23

import common.io.inputFile
import common.io.readNonBlankLines
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day23Test {

  private val sample =
    """
    kh-tc
    qp-kh
    de-cg
    ka-co
    yn-aq
    qp-ub
    cg-tb
    vc-aq
    tb-ka
    wh-tc
    yn-cg
    kh-ub
    ta-co
    de-co
    tc-td
    tb-wq
    wh-td
    ta-ka
    td-qp
    aq-cg
    wq-ub
    ub-vc
    de-ta
    wq-aq
    wq-vc
    wh-yn
    ka-de
    kh-ta
    co-tc
    wh-qp
    tb-vc
    td-yn
    """
      .trimIndent()

  @Test
  fun `compute triples`() {
    val connections = sample.lines().toConnections()
    val expectedTriples = """
      aq,cg,yn
      aq,vc,wq
      co,de,ka
      co,de,ta
      co,ka,ta
      de,ka,ta
      kh,qp,ub
      qp,td,wh
      tb,vc,wq
      tc,td,wh
      td,wh,yn
      ub,vc,wq
    """.trimIndent().lines().map { it.split(",").toSet() }.toSet()

    connections.triples() shouldBe expectedTriples
  }

  @Test
  fun `solve part one with sample input`() {
    part1(sample.lines()) shouldBe "7"
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day23.txt").readNonBlankLines()
    part1(input) shouldBe "1062"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample.lines()) shouldBe "co,de,ka,ta"
  }

  @Test
  fun `solve part two`() {
    val input = inputFile("day23.txt").readNonBlankLines()
    part2(input) shouldBe "ANSWER2"
  }

}
