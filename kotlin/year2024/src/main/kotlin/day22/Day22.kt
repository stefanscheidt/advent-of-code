package day22

fun mix(secret: Long, value: Long): Long = secret xor value

fun prune(secret: Long): Long = secret.mod(16777216L)

fun nextSecret(secret: Long): Long =
  secret
    .let { prune(mix(it, it * 64)) }
    .let { prune(mix(it, it / 32)) }
    .let { prune(mix(it, it * 2048)) }

fun nextSecrets(seed: Long): Sequence<Long> = generateSequence(seed) { nextSecret(it) }

fun nthSecret(seed: Long, n: Int): Long = nextSecrets(seed).drop(n).first()

// Part 1

fun part1(input: List<String>): String =
  input.map { it.toLong() }.sumOf { nthSecret(it, 2000) }.toString()

// Part 2

fun part2(input: List<String>): String {
  val totalPricePerSequence: Map<List<Int>, Int> = buildMap {
    input
      .map { it.toLong() }
      .forEach { seed ->
        val prices = nextSecrets(seed).map { (it % 10L).toInt() }.take(2001).toList()
        val changes = prices.zipWithNext().map { (p1, p2) -> p2 - p1 }
        val sequences = changes.windowed(4)
        sequences
          .zip(prices.drop(4))
          .distinctBy { it.first }
          .forEach { (seq, price) -> this[seq] = this.getOrDefault(seq, 0) + price }
      }
  }

  return totalPricePerSequence.values.max().toString()
}
