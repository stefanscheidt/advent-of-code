package day05

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class Day05Test {

    private val sample = """
        seeds: 79 14 55 13

        seed-to-soil map:
        50 98 2
        52 50 48

        soil-to-fertilizer map:
        0 15 37
        37 52 2
        39 0 15

        fertilizer-to-water map:
        49 53 8
        0 11 42
        42 0 7
        57 7 4

        water-to-light map:
        88 18 7
        18 25 70

        light-to-temperature map:
        45 77 23
        81 45 19
        68 64 13

        temperature-to-humidity map:
        0 69 1
        1 0 69

        humidity-to-location map:
        60 56 37
        56 93 4
    """.trimIndent()

    @Test
    fun `parse care instruction`() {
        val input = """
            seed-to-soil map:
            50 98 2
            52 50 48
        """.trimIndent()

        val careInstruction = parseCareInstruction(input)

        careInstruction shouldBe CareInstruction(
            src = "seed",
            dest = "soil",
            mappings = listOf(
                Mapping((50L until 98), 2),
                Mapping((98L until 100L), -48),
            )
        )
    }

    @Test
    fun `mapped value of care instruction`() {
        val careInstruction = CareInstruction(
            src = "seed",
            dest = "soil",
            mappings = listOf(
                Mapping((50L until 53), 2),
                Mapping((98L until 100L), -48),
            )
        )

        careInstruction.mappedValue(0) shouldBe 0

        careInstruction.mappedValue(49) shouldBe 49
        careInstruction.mappedValue(50) shouldBe 52
        careInstruction.mappedValue(52) shouldBe 54
        careInstruction.mappedValue(53) shouldBe 53

        careInstruction.mappedValue(97) shouldBe 97
        careInstruction.mappedValue(98) shouldBe 50
        careInstruction.mappedValue(99) shouldBe 51
        careInstruction.mappedValue(100) shouldBe 100
    }

    @Test
    fun `compute target value`() {
        val input = splitInput(sample)

        val instructions = parseCareInstructions(input.drop(1))

        instructions.mappedValue(79) shouldBe 82
        instructions.mappedValue(14) shouldBe 43
    }

    @Test
    fun `solve part one with sample input`() {
        val input = splitInput(sample)
        solvePartOne(input) shouldBe 35
    }

    @Test
    fun `solve part two with sample input`() {
        val input = splitInput(sample)
        solvePartTwo(input) shouldBe 46
    }

    @Test
    @Disabled
    fun `solve puzzle`() {
        val solution = solvePuzzle(file)

        solution.first shouldBe "51580674"
        solution.second shouldBe "99751240"
    }

}
