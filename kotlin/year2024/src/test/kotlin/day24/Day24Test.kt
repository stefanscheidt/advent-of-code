package day24

import common.io.inputFile
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day24Test {

  private val sample =
    """
    x00: 1
    x01: 0
    x02: 1
    x03: 1
    x04: 0
    y00: 1
    y01: 1
    y02: 1
    y03: 1
    y04: 1

    ntg XOR fgs -> mjb
    y02 OR x01 -> tnw
    kwq OR kpj -> z05
    x00 OR x03 -> fst
    tgd XOR rvg -> z01
    vdt OR tnw -> bfw
    bfw AND frj -> z10
    ffh OR nrd -> bqk
    y00 AND y03 -> djm
    y03 OR y00 -> psh
    bqk OR frj -> z08
    tnw OR fst -> frj
    gnj AND tgd -> z11
    bfw XOR mjb -> z00
    x03 OR x00 -> vdt
    gnj AND wpb -> z02
    x04 AND y00 -> kjc
    djm OR pbm -> qhw
    nrd AND vdt -> hwm
    kjc AND fst -> rvg
    y04 OR y02 -> fgs
    y01 AND x02 -> pbm
    ntg OR kjc -> kwq
    psh XOR fgs -> tgd
    qhw XOR tgd -> z09
    pbm OR djm -> kpj
    x03 XOR y03 -> ffh
    x00 XOR y04 -> ntg
    bfw OR bqk -> z06
    nrd XOR fgs -> wpb
    frj XOR qhw -> z04
    bqk OR frj -> z07
    y03 OR x01 -> nrd
    hwm AND bqk -> z03
    tgd XOR rvg -> z12
    tnw OR pbm -> gnj
    """
      .trimIndent()

  @Test
  fun `compute signal at wire`() {
    val (inputs, gates) = parseInput(sample)

    gates.signalAt("z00", inputs) shouldBe 0
    gates.signalAt("z01", inputs) shouldBe 0
    gates.signalAt("z02", inputs) shouldBe 0
    gates.signalAt("z03", inputs) shouldBe 1
    gates.signalAt("z04", inputs) shouldBe 0
    gates.signalAt("z05", inputs) shouldBe 1
    gates.signalAt("z06", inputs) shouldBe 1
    gates.signalAt("z07", inputs) shouldBe 1
    gates.signalAt("z08", inputs) shouldBe 1
    gates.signalAt("z09", inputs) shouldBe 1
    gates.signalAt("z10", inputs) shouldBe 1
    gates.signalAt("z11", inputs) shouldBe 0
    gates.signalAt("z12", inputs) shouldBe 0
  }

  @Test
  fun `solve part one with sample input`() {
    part1(sample) shouldBe "2024"
  }

  @Test
  fun `solve part one`() {
    val input = inputFile("day24.txt").readText().trimEnd()
    part1(input) shouldBe "51107420031718"
  }

  @Test
  fun `solve part two with sample input`() {
    part2(sample) shouldBe "ANSWER2"
  }

  @Test
  fun `solve part two`() {
    val input = inputFile("day24.txt").readText().trimEnd()
    part2(input) shouldBe "ANSWER2"
  }
}
