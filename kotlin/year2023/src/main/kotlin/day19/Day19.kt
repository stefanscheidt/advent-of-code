package day19

import common.io.inputFile
import java.io.File

val file = inputFile("day19.txt")

fun main() {
  val solution = solvePuzzle(file)

  println("1: ${solution.first}")
  println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<String, String> {
  val (workflows, ratings) = parseInput(file.readText().trim())

  return Pair("${solvePartOne(workflows, ratings)}", "")
}

fun solvePartOne(workflows: Map<String, Workflow>, ratings: List<Rating>): Int =
  ratings
    .map { it to workflows.assess(it) }
    .filter { (_, assessment) -> assessment is Accepted }
    .sumOf { (rating, _) -> rating.a + rating.m + rating.s + rating.x }

fun parseInput(input: String): Pair<Map<String, Workflow>, List<Rating>> {
  val (workflowInput, ratingInput) = input.split("""\s+\n""".toRegex()).map(String::lines)

  val workflows = workflowInput.map(::parseWorkflow).associateBy { it.name }
  val ratings = ratingInput.map(::parseRating)

  return Pair(workflows, ratings)
}

val ratingRegex = """^\{x=(\d+),m=(\d+),a=(\d+),s=(\d+)}$""".toRegex()

fun parseRating(input: String): Rating {
  val (x, m, a, s) =
    ratingRegex.matchEntire(input)?.destructured ?: error("invalid rating [$input]")
  return Rating(x.toInt(), m.toInt(), a.toInt(), s.toInt())
}

val workflowRegex = """^(\w+)\{(\S*)}$""".toRegex()

fun parseWorkflow(input: String): Workflow {
  val (name, rulesInput) =
    workflowRegex.matchEntire(input)?.destructured ?: error("invalid workflow [$input]")
  return Workflow(name, rulesInput.split(",").map(::parseRule))
}

fun parseRule(input: String): Rule {
  if (':' !in input) return { _ -> input.toAssessment() }

  val (predicate, result) = input.split(":")
  val getter = predicate[0].toGetter()
  val comparator = predicate[1].toComparator()
  val value = predicate.drop(2).toInt()

  return { rating ->
    if (comparator.compare(getter(rating), value) > 0) result.toAssessment() else null
  }
}

fun Char.toGetter(): (Rating) -> Int =
  when (this) {
    'x' -> { r -> r.x }
    'm' -> { r -> r.m }
    'a' -> { r -> r.a }
    's' -> { r -> r.s }
    else -> error("invalid property name [$this]")
  }

fun Char.toComparator(): Comparator<Int> =
  when (this) {
    '>' -> Comparator { i1, i2 -> i1.compareTo(i2) }
    '<' -> Comparator { i1, i2 -> i2.compareTo(i1) }
    else -> error("invalid comparison operator [$this]")
  }

fun String.toAssessment(): Assessment =
  when (this) {
    "A" -> Accepted
    "R" -> Rejected
    else -> Reference(this)
  }

data class Rating(val x: Int = 0, val m: Int = 0, val a: Int = 0, val s: Int = 0)

sealed class Assessment

data class Reference(val name: String) : Assessment()

data object Accepted : Assessment()

data object Rejected : Assessment()

typealias Rule = (Rating) -> Assessment?

data class Workflow(val name: String, val rules: List<Rule>) {
  fun assess(rating: Rating): Assessment =
    rules.asSequence().map { it(rating) }.first { it != null } ?: Rejected
}

fun Map<String, Workflow>.assess(rating: Rating): Assessment {
  val start = get("in") ?: error("no workflow named 'in'")
  var assessment = start.assess(rating)
  while (assessment is Reference) {
    val workflow = get(assessment.name) ?: error("invalid workflow name [${assessment.name}]")
    assessment = workflow.assess(rating)
  }
  return assessment
}
