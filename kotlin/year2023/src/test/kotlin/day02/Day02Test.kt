package day02

import day02.Color.Blue
import day02.Color.Green
import day02.Color.Red
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day02Test {

    private val sample = """
        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
    """.trimIndent()

    @Test
    fun `parse game`() {
        val line = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
        val game = Game(
            id = 1,
            hands = listOf(
                mapOf(Blue to 3, Red to 4),
                mapOf(Red to 1, Green to 2, Blue to 6),
                mapOf(Green to 2)
            )
        )
        parseGame(line) shouldBe game
    }

    @Test
    fun `compute game power`() {
        val game = parseGame("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green")

        game.power shouldBe 48
    }

    @Test
    fun `solve part one with sample input`() {
        val games = parseInput(sample)

        solvePart1(games) shouldBe 8
    }

    @Test
    fun `solve part two with sample input`() {
        val games = parseInput(sample)

        solvePart2(games) shouldBe 2286
    }

    @Test
    fun `solve puzzle`() {
        val solution = solvePuzzle(file)

        solution.first shouldBe "2149"
        solution.second shouldBe "71274"
    }

}
