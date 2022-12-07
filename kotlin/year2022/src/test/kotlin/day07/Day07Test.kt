package day07

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day07Test {

    private val sample = """
        $ cd /
        $ ls
        dir a
        14848514 b.txt
        8504156 c.dat
        dir d
        $ cd a
        $ ls
        dir e
        29116 f
        2557 g
        62596 h.lst
        $ cd e
        $ ls
        584 i
        $ cd ..
        $ cd ..
        $ cd d
        $ ls
        4060174 j
        8033020 d.log
        5626152 d.ext
        7214296 k
    """.trimIndent()

    @Test
    fun `parse sample input`() {
        parseInput(sample.lines()) shouldBe mapOf(
            "/a" to 94853,
            "/a/e" to 584,
            "/d" to 24933642,
            "/" to 48381165,
        )
    }

    @Test
    fun `solve part one with sample input`() {
        val dirs = parseInput(sample.lines())
        solvePartOne(dirs) shouldBe 95437
    }

    @Test
    fun `solve part two with sample input`() {
        val dirs = parseInput(sample.lines())
        solvePartTwo(dirs) shouldBe 24933642
    }

    @Test
    fun `solve puzzle`() {
        val solution = solvePuzzle(file)

        solution.first shouldBe 1141028
        solution.second shouldBe 8278005
    }

}
