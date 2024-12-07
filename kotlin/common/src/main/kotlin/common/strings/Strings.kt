package common.strings

fun String.rows(): List<String> {
  val lines = lines()
  return (0 until lines.maxOf(String::length)).map { column ->
    lines.map { line -> line.getOrNull(column) ?: " " }.joinToString(separator = "")
  }
}

fun String.nonBlankLines(): List<String> = lines().filterNot { it.isBlank() }

fun List<String>.nonBlankLines(): List<String> = filterNot { it.isBlank() }
