package common.strings

fun String.rows(): List<String> {
  val lines = lines()
  return (0 until lines.maxOf(String::length)).map { column ->
    lines.map { line -> line.getOrNull(column) ?: " " }.joinToString(separator = "")
  }
}

fun String.notEmptyLines(): List<String> = lines().filterNot { it.isBlank() }
