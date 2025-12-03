package common.collections

fun <T> Collection<T>.allEqual(): Boolean = this.all { it == this.first() }
