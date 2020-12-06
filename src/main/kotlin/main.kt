import java.io.File

fun main(args: Array<String>) {
    val day = if (args.isNotEmpty()) args.first() else return
    println("Solving $day day!")
    val input = File("days/$day").readLines()
    println("solution is ${solve(day, input)}")
}

fun solve(task: String, input: List<String>): String? = solutions[task]?.invoke(input)

val solutions = mapOf<String, (input: List<String>) -> String>(
    "1" to ::day1,
    "1.5" to ::day1Part2,
    "2" to ::day2,
    "2.5" to ::day2Part2,
    "3" to ::day3
)

fun day1(input: List<String>): String {

    val sorted = input.map { it.toInt() }.sorted()
    for (i in sorted.indices) {
        for (j in sorted.indices.reversed()) {
            if (i >= j)
                continue
            if (sorted[j] > 2019)
                continue
            if (sorted[j] + sorted[i] == 2020)
                return (sorted[j] * sorted[i]).toString()

        }
    }

    return "NaN"
}

fun day1Part2(input: List<String>): String {

    val sorted = input.map { it.toInt() }.sorted()
    val set = input.map { it.toInt() }.toHashSet()
    for (left in sorted.indices) {
        for (right in sorted.indices.reversed()) {
            if (left >= right)
                break
            if (sorted[right] > 2018)
                continue
            val sum = sorted[right] + sorted[left]
            val remainder = 2020 - sum
            if (set.contains(remainder))
                return (sorted[right] * sorted[left] * remainder).toString()

        }
    }

    return "NaN"
}

fun day2(input: List<String>): String = input.filter { l ->
    val (rules, password) = l.split(":")
    val (occurrences, letter) = rules.split(" ")
    val (min, max) = occurrences.split("-").map { it.toInt() }
    password.count { it == letter[0] } in min..max
}.size.toString()

fun day2Part2(input: List<String>): String = input.filter { l ->
    val (rules, password) = l.split(":")
    val (positions, letterS) = rules.split(" ")
    val (first, second) = positions.split("-").map { it.toInt() }
    val letter = letterS[0]
    password[first] ==  letter && password[second] != letter
            || password[first] != letter && password[second] == letter
}.size.toString()

fun day3(input: List<String>): String {
    var trees = 0
    var x = 0
    for (y in 1 until input.size) {
        x += 3
        if (input[y][x % input[y].length] == '#')
            trees++
    }
    return trees.toString()
}