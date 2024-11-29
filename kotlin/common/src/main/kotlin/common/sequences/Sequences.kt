package common.sequences

fun <T> List<T>.repeat(): Sequence<T> = sequence {
  if (isEmpty()) return@sequence
  while (true) yieldAll(this@repeat)
}

fun <T> Sequence<T>.findFirstRepeatedOrNull(): T? {
  val seen = mutableSetOf<T>()
  val xs = iterator()

  var x = xs.next()
  while (seen.add(x)) {
    x = xs.next()
  }

  return x
}
