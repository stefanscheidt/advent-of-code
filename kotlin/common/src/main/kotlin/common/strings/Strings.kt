package common.strings

fun String.rows(): List<String> {
  val lines = lines()
  return (0 until lines.maxOf(String::length)).map { column ->
    lines.map { line -> line.getOrNull(column) ?: " " }.joinToString(separator = "")
  }
}

fun String.notBlankLines(): List<String> = lines().filterNot { it.isBlank() }

fun List<String>.notBlankLines(): List<String> = filterNot { it.isBlank() }
