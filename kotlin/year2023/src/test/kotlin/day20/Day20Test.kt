package day20

import day20.Frequency.High
import day20.Frequency.Low
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class Day20Test {

  private val sample1 =
    """
        broadcaster -> a, b, c
        %a -> b
        %b -> c
        %c -> inv
        &inv -> a
    """
      .trimIndent()

  private val sample2 =
    """
        broadcaster -> a
        %a -> inv, con
        &inv -> b
        %b -> con
        &con -> output
    """
      .trimIndent()

  @Test
  fun `parse broadcaster`() {
    val module = parseModule("broadcaster -> a, b, c")

    module.shouldBeInstanceOf<Broadcaster>()
    module.destinations shouldBe listOf("a", "b", "c")
  }

  @Test
  fun `parse flip-flop`() {
    val module = parseModule("%a -> inv, con")

    module.shouldBeInstanceOf<FlipFlop>()
    module.name shouldBe "a"
    module.destinations shouldBe listOf("inv", "con")
  }

  @Test
  fun `broadcaster receives pulse`() {
    val config = parseConfiguration(listOf("broadcaster -> a, b"))

    config.receive(Pulse(src = "test", dest = "broadcaster", High)) shouldBe
      listOf(Pulse("broadcaster", "a", High), Pulse("broadcaster", "b", High))
  }

  @Test
  fun `flip-flop receives pulse`() {
    val config = parseConfiguration(listOf("%a -> inv, con"))

    config.receive(Pulse(src = "test", dest = "a", High)) shouldBe emptyList()

    config.receive(Pulse(src = "test", dest = "a", Low)) shouldBe
      listOf(Pulse("a", "inv", High), Pulse("a", "con", High))

    config.receive(Pulse(src = "test", dest = "a", Low)) shouldBe
      listOf(Pulse("a", "inv", Low), Pulse("a", "con", Low))
  }

  @Test
  fun `solve part one with boring sample input`() {
    val config = parseConfiguration(sample1.lines())
    val metrics = Metrics()

    config.pushButton(metrics)

    metrics.highCount shouldBe 4
    metrics.lowCount shouldBe 8
  }

  @Test
  fun `solve part one with interesting sample input`() {
    val config = parseConfiguration(sample2.lines())

    solvePartOne(config) shouldBe 11687500
  }

  @Test
  @Disabled
  fun `solve part two with sample input`() {
    TODO()
  }

  @Test
  fun `solve puzzle`() {
    val solution = solvePuzzle(file)

    solution.first shouldBe "680278040"
    solution.second shouldBe ""
  }
}
