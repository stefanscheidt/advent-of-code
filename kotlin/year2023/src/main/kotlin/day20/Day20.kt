package day20

import common.io.inputFile
import day20.FlipFlop.State.Off
import day20.FlipFlop.State.On
import day20.Frequency.High
import day20.Frequency.Low
import java.io.File


val file = inputFile("day20.txt")

fun main() {
    val solution = solvePuzzle(file)

    println("1: ${solution.first}")
    println("2: ${solution.second}")
}

fun solvePuzzle(file: File): Pair<String, String> {
    val configuration = parseConfiguration(file.readLines().filter(String::isNotBlank))
    return Pair("${solvePartOne(configuration)}", "")
}

fun solvePartOne(configuration: Configuration): Long {
    val metrics = Metrics()
    repeat(1000) { configuration.pushButton(metrics) }
    return metrics.lowCount * metrics.highCount
}

fun parseConfiguration(input: List<String>): Configuration =
    input.mapNotNull(::parseModule)
        .associateBy { it.name }
        .also { it.configure() }

fun parseModule(input: String): CommModule? {
    val (from, to) = input.split(" -> ")
    val destinations = to.split(", ")

    if (from == "broadcaster") return Broadcaster(destinations)

    val type = from.first()
    val name = from.drop(1)
    return when (type) {
        '%' -> FlipFlop(name, destinations)
        '&' -> Conjunction(name, destinations)
        else -> null
    }
}

enum class Frequency {
    High, Low
}

data class Pulse(val src: String, val dest: String, val frequency: Frequency)

sealed class CommModule(val name: String, val destinations: List<String>) {

    open fun configure(configuration: Configuration) {}

    abstract fun receive(pulse: Pulse): List<Pulse>

    protected fun emit(frequency: Frequency): List<Pulse> =
        destinations.map { dest -> Pulse(src = name, dest = dest, frequency = frequency) }

    override fun toString(): String =
        "${this::class.simpleName}(name = $name, destinations = $destinations"
}

// There is a single broadcast module (named broadcaster).
// When it receives a pulse, it sends the same pulse to all of its destination modules.
class Broadcaster(destinations: List<String>) : CommModule("broadcaster", destinations) {

    override fun receive(pulse: Pulse): List<Pulse> =
        emit(pulse.frequency)

}

// Flip-flop modules (prefix %) are either on or off; they are initially off.
// If a flip-flop module receives a high pulse, it is ignored and nothing happens.
// However, if a flip-flop module receives a low pulse, it flips between on and off.
// If it was off, it turns on and sends a high pulse.
// If it was on, it turns off and sends a low pulse.
class FlipFlop(name: String, destinations: List<String>) : CommModule(name, destinations) {

    enum class State(val frequency: Frequency) { On(High), Off(Low) }

    private fun State.flip(): State =
        when (this) {
            On -> Off
            Off -> On
        }

    private var state = Off

    override fun receive(pulse: Pulse): List<Pulse> =
        if (pulse.frequency == High) {
            emptyList()
        } else {
            state = state.flip()
            emit(state.frequency)
        }

}

// Conjunction modules (prefix &) remember the type of the most recent pulse received from each of their connected input modules;
// they initially default to remembering a low pulse for each input.
// When a pulse is received, the conjunction module first updates its memory for that input.
// Then, if it remembers high pulses for all inputs, it sends a low pulse; otherwise, it sends a high pulse
class Conjunction(name: String, destinations: List<String>) : CommModule(name, destinations) {

    private val sources = mutableMapOf<String, Frequency>()

    override fun configure(configuration: Configuration) {
        configuration.values
            .filter { module -> name in module.destinations }
            .forEach { module -> sources[module.name] = Low }
    }

    override fun receive(pulse: Pulse): List<Pulse> {
        sources[pulse.src] = pulse.frequency
        val allHigh = sources.values.all { it == High }
        return if (allHigh) emit(Low) else emit(High)
    }
}

typealias Configuration = Map<String, CommModule>

fun Configuration.configure() {
    values.forEach { module -> module.configure(this) }
}

fun Configuration.receive(pulse: Pulse): List<Pulse> =
    get(pulse.dest)?.receive(pulse) ?: emptyList()

class Metrics {
    private var highCounter = 0L
    private var lowCounter = 0L

    fun count(frequency: Frequency) {
        when (frequency) {
            High -> highCounter++
            Low -> lowCounter++
        }
    }

    val highCount: Long
        get() = highCounter

    val lowCount: Long
        get() = lowCounter
}

// Here at Desert Machine Headquarters, there is a module with a single button on it called, aptly, the button module.
// When you push the button, a single low pulse is sent directly to the broadcaster module.
fun Configuration.pushButton(metrics: Metrics) {
    val pulses = ArrayDeque<Pulse>()
    pulses.add(Pulse("button", "broadcaster", Low))
    while (pulses.isNotEmpty()) {
        val pulse = pulses.removeFirst()
        metrics.count(pulse.frequency)
        pulses.addAll(receive(pulse))
    }
}
