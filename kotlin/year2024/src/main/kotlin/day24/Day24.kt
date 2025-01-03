package day24

import common.io.inputFile
import day24.Operation.AND
import day24.Operation.OR
import day24.Operation.XOR

// Operation

enum class Operation(val fn: (Int, Int) -> Int) {
  AND({ a, b -> a and b }),
  OR({ a, b -> a or b }),
  XOR({ a, b -> a xor b }),
}

// Gate

data class Gate(val operation: Operation, val wireA: String, val wireB: String) {
  fun eval(a: Int, b: Int): Int = operation.fn(a, b)
}

fun Map<String, Gate>.signalAt(wire: String, inputs: Map<String, Int>): Int =
  if (wire in inputs) {
    inputs[wire] ?: error("No input for wire $wire")
  } else {
    val gate = this[wire] ?: error("No gate for wire $wire")
    gate.eval(signalAt(gate.wireA, inputs), signalAt(gate.wireB, inputs))
  }

// Parsing

val gateRegex = Regex("""(\w+) (AND|OR|XOR) (\w+) -> (\w+)""")

fun parseInputSignal(input: String): Pair<String, Int> {
  val (wire, signal) = input.split(": ")
  return wire to signal.toInt()
}

fun parseGate(input: String): Pair<String, Gate> {
  val match = gateRegex.matchEntire(input) ?: error("Can't parse gate [$input]")
  val (a, opCode, b, out) = match.destructured
  val operation =
    when (opCode) {
      "AND" -> AND
      "OR" -> OR
      "XOR" -> XOR
      else -> error("Invalid operation: $opCode")
    }
  return out to Gate(operation, a, b)
}

fun parseInput(input: String): Pair<Map<String, Int>, Map<String, Gate>> {
  val (inputsStr, gatesStr) = input.split("\n\n").map(String::lines)
  val inputs = inputsStr.associate(::parseInputSignal)
  val gates = gatesStr.associate(::parseGate)
  return inputs to gates
}

// Part 1

fun part1(input: String): String {
  val (inputs, gates) = parseInput(input)
  val zValue =
    gates.keys
      .filter { it.startsWith("z") }
      .sorted()
      .map { wire -> gates.signalAt(wire, inputs) }
      .foldIndexed(0L) { index, acc, signal -> acc + signal * (1L shl index) }

  return zValue.toString()
}

// Part 2

fun part2(input: String): String {
  return "TODO2"
}

fun main() {
  val input = inputFile("day24.txt").readText().trimEnd()
  val (_, gates) = parseInput(input)
  val z = gates.map { it.key }.filter { it.startsWith("z") }.sorted().joinToString("->")
  val x = z.replace("z", "x")
  val y = z.replace("z", "y")

  println(
    """
        digraph G {
            subgraph {
               node [style=filled,color=green]
                $z
            }
            subgraph {
                node [style=filled,color=gray]
                $x
            }
            subgraph {
                node [style=filled,color=gray]
                $y
            }
            subgraph {
                node [style=filled,color=pink]
                ${gates.filter { (_, gate) -> gate.operation == AND }.keys.joinToString(" ")}
            }
            subgraph {
                node [style=filled,color=yellow];
                ${gates.filter { (_, gate) -> gate.operation == OR }.keys.joinToString(" ")}
            }
            subgraph {
                node [style=filled,color=lightblue];
                ${gates.filter { (_, gate) -> gate.operation == XOR }.keys.joinToString(" ")}
            }
            """
      .trimIndent()
  )
  gates.forEach { (out, gate) ->
    println("    ${gate.wireA} -> $out")
    println("    ${gate.wireB} -> $out")
  }
  println("}")
}
