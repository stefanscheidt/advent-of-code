package day25

typealias Schematic = List<String>

typealias Profile = List<Int>

fun Profile.fits(other: Profile): Boolean = this.zip(other).map { (a, b) -> a + b }.all { it <= 5 }

fun Schematic.toProfile(): Profile {
  val profile = MutableList(5) { 0 }
  this.drop(1).forEach { line ->
    line.forEachIndexed { index, char -> profile[index] += if (char == '#') 1 else 0 }
  }
  return profile
}

fun String.toSchematics(): Pair<List<Schematic>, List<Schematic>> =
  split("\n\n").map(String::lines).partition { it.first() == "#####" }

fun String.toProfiles(): Pair<List<Profile>, List<Profile>> {
  val (keys, locks) = this.toSchematics()
  val keyProfiles = keys.map(Schematic::toProfile)
  val lockProfiles = locks.map(Schematic::reversed).map(Schematic::toProfile)
  return keyProfiles to lockProfiles
}

// Part 1

fun part1(input: String): String {
  val (keys, locks) = input.toProfiles()
  val fits = locks.sumOf { lock -> keys.count { key -> key.fits(lock) } }
  return fits.toString()
}
