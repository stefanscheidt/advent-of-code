package day19

import common.io.inputFile
import day19.Mineral.Clay
import day19.Mineral.Geode
import day19.Mineral.Obsidian
import day19.Mineral.Ore
import java.io.File
import java.util.PriorityQueue
import kotlin.math.ceil


val file = inputFile("day19.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<Int, Int> {
    val blueprints = parseInput(file.readLines())
    return Pair(solvePartOne(blueprints), solvePartTwo(blueprints))
}

fun parseInput(input: List<String>): List<Blueprint> =
    input.map(::parseBlueprint)

fun parseBlueprint(input: String): Blueprint {
    val parts = input.split(" ")
    return Blueprint(
        mapOf(
            Ore to mapOf(Ore to parts[6].toInt()),
            Clay to mapOf(Ore to parts[12].toInt()),
            Obsidian to mapOf(Ore to parts[18].toInt(), Clay to parts[21].toInt()),
            Geode to mapOf(Ore to parts[27].toInt(), Obsidian to parts[30].toInt())
        )
    )
}

fun solvePartOne(blueprints: List<Blueprint>): Int =
    blueprints.mapIndexed { index, blueprint ->
        (index + 1) * blueprint.qualityLevel(targetMineral = Geode, timeLimit = 24)
    }.sum()

fun solvePartTwo(blueprints: List<Blueprint>): Int =
    blueprints.take(3).map {
        it.qualityLevel(targetMineral = Geode, timeLimit = 32)
    }.reduce(Int::times)

enum class Mineral {
    Ore, Clay, Obsidian, Geode
}

data class Blueprint(val costsForRobots: Map<Mineral, Map<Mineral, Int>>) {
    fun maxCost(mineral: Mineral): Int =
        costsForRobots.values.maxOf { it[mineral] ?: 0 }

    fun qualityLevel(targetMineral: Mineral, timeLimit: Int): Int {
        var qualityLevel = 0
        val queue = PriorityQueue<Stock>()
        queue.add(Stock(this, targetMineral))
        while (queue.isNotEmpty()) {
            val stock = queue.poll()
            qualityLevel = maxOf(qualityLevel, stock.amountOfMineral(targetMineral))
            if (stock.targetMineralUpperBound(timeLimit) > qualityLevel) {
                queue.addAll(stock.possibleFutureStocks(timeLimit))
            }
        }
        return qualityLevel
    }
}

data class Stock(
    val blueprint: Blueprint,
    val targetMineral: Mineral,
    val time: Int = 1,
    val robots: Map<Mineral, Int> = mapOf(Ore to 1, Clay to 0, Obsidian to 0, Geode to 0),
    val minerals: Map<Mineral, Int> = mapOf(Ore to 1, Clay to 0, Obsidian to 0, Geode to 0),
) : Comparable<Stock> {

    fun amountOfMineral(mineral: Mineral): Int =
        minerals[mineral] ?: 0

    fun targetMineralUpperBound(timeLimit: Int): Int {
        val timeLeft = timeLimit - time
        val numberOfRobots = robots[targetMineral] ?: 0
        val upperBoundForFutureProduction = (numberOfRobots..(numberOfRobots + timeLeft)).sum()
        return amountOfMineral(targetMineral) + upperBoundForFutureProduction
    }

    fun possibleFutureStocks(timeLimit: Int): List<Stock> {
        val result = mutableListOf<Stock>()
        if (time < timeLimit) {
            Mineral.values().forEach { mineralToCollect ->
                if (robotShouldBeBuilt(mineralToCollect)) {
                    val futureStock = stockAfterRobotWasBuilt(mineralToCollect)
                    if (futureStock.time <= timeLimit) result.add(futureStock)
                }
            }
        }
        return result
    }

    private fun robotShouldBeBuilt(mineralToCollect: Mineral): Boolean {
        return robotIsNeeded(mineralToCollect) && mineralsToBuildRobotAreOnStock(mineralToCollect)
    }

    private fun robotIsNeeded(mineralToCollect: Mineral): Boolean =
        mineralToCollect == targetMineral || (robots[mineralToCollect] ?: 0) < blueprint.maxCost(mineralToCollect)

    private fun mineralsToBuildRobotAreOnStock(mineralToCollect: Mineral): Boolean =
        blueprint.costsForRobots[mineralToCollect]
            ?.map { (mineral, _) -> amountOfMineral(mineral) }
            ?.all { it > 0 } ?: true

    private fun timeNeededToBuildRobot(mineralToCollect: Mineral): Int {
        val max = blueprint.costsForRobots[mineralToCollect]
            ?.maxOf { (mineral, cost) ->
                val amount = amountOfMineral(mineral)
                val numberOfRobots = robots[mineral] ?: 0
                if (amount >= cost) 0
                else ceil((cost - amount).toFloat() / numberOfRobots.toFloat()).toInt()
            } ?: 0
        return max + 1
    }

    private fun stockAfterRobotWasBuilt(mineralToCollect: Mineral): Stock {
        val buildTime = timeNeededToBuildRobot(mineralToCollect)
        val stock = Stock(
            blueprint = blueprint,
            targetMineral = targetMineral,
            time = time + buildTime,
            robots = robots.mapValues { (mineral, amount) ->
                if (mineral == mineralToCollect) amount + 1 else amount
            },
            minerals = minerals.mapValues { (mineral, amount) ->
                val cost = blueprint.costsForRobots[mineralToCollect]?.get(mineral) ?: 0
                amount - cost + buildTime * (robots[mineral] ?: 0)
            }
        )
        return stock
    }

    override fun compareTo(other: Stock): Int =
        other.amountOfMineral(other.targetMineral).compareTo(amountOfMineral(targetMineral))
}
