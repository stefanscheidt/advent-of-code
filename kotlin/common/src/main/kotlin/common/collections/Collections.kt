package common.collections

fun <T> Collection<T>.allEqual(): Boolean = this.all { it == this.first() }

fun <T> List<T>.allSublists(): List<List<T>> {
  if (isEmpty()) return listOf(emptyList())

  val previous = dropLast(1).allSublists()
  val last = last()
  return mutableListOf<List<T>>().apply {
    addAll(previous)
    addAll(previous.map { it + last })
  }
}
