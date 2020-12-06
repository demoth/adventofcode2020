import java.io.File

fun main(args: Array<String>) {
    val day = if (args.isNotEmpty()) args.first() else return
    println("Solving $day day!")
    val input = File("days/$day").readLines()
    println("solution is ${solve(day, input)}")
}

fun solve(task: String, input: List<String>): Pair<String, String>? {
    return solutions[task]?.run {
        Pair(this.first.invoke(input), this.second.invoke(input))
    }
}
typealias Day = (input: List<String>) -> String

val solutions = mapOf<String, Pair<Day, Day>>(
    "1" to (::day1 to ::day1Part2),
    "2" to (::day2 to ::day2Part2),
    "3" to (::day3 to ::day3Part2),
    "4" to (::day4 to ::day4Part2),
    "5" to (::day5 to ::day5Part2),
    "6" to (::day6 to ::day6Part2)
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
    password[first] == letter && password[second] != letter
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

/*
    1 Right 1, down 1.
    2 Right 3, down 1. (This is the slope you already checked.)
    3 Right 5, down 1.
    4 Right 7, down 1.
    5 Right 1, down 2.
 */
fun day3Part2(input: List<String>): String {
    var trees1 = 0L
    var x1 = 0
    var trees2 = 0L
    var x2 = 0
    var trees3 = 0L
    var x3 = 0
    var trees4 = 0L
    var x4 = 0
    var trees5 = 0L
    var x5 = 1

    for (y in 1 until input.size) {
        x1 += 1
        x2 += 3
        x3 += 5
        x4 += 7

        if (input[y][x1 % input[y].length] == '#')
            trees1++
        if (input[y][x2 % input[y].length] == '#')
            trees2++
        if (input[y][x3 % input[y].length] == '#')
            trees3++
        if (input[y][x4 % input[y].length] == '#')
            trees4++
        if (y % 2 == 0) {
            if (input[y][x5 % input[y].length] == '#') {
                trees5++
            }
            x5 += 1
        }
    }
    return (trees1 * trees2 * trees3 * trees4 * trees5).toString()
}

fun day4(input: List<String>): String {
    var valid = 0
    val requiredFields = setOf("ecl", "pid", "eyr", "hcl", "byr", "iyr", "hgt")
    val passportFields = mutableSetOf<String>()
    input.forEach { line ->
        if (line.isBlank()) {
            if (passportFields.containsAll(requiredFields))
                valid++
            passportFields.clear()
        } else {
            line.split(" ").forEach { field ->
                passportFields.add(field.split(":").first())
            }
        }
    }
    return valid.toString()
}

fun day4Part2(input: List<String>): String {
    var valid = 0
    val requiredFields = setOf("ecl", "pid", "eyr", "hcl", "byr", "iyr", "hgt")
    val eyeColors = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    val passportFields = mutableSetOf<String>()
    input.forEach { line ->
        if (line.isBlank()) {
            if (passportFields.containsAll(requiredFields))
                valid++
            passportFields.clear()
        } else {
            line.split(" ").forEach { field ->
                val (key, value) = field.split(":")
                val fieldIsValid: Boolean = when (key) {
                    "byr" -> {
                        value.toInt() in 1920..2002
                    }
                    "iyr" -> {
                        value.toInt() in 2010..2020
                    }
                    "eyr" -> {
                        value.toInt() in 2020..2030
                    }
                    "hgt" -> {
                        value.endsWith("in") && value.takeWhile { it.isDigit() }.toInt() in 59..76
                                || value.endsWith("cm") && value.takeWhile { it.isDigit() }.toInt() in 150..193
                    }
                    "hcl" -> value.matches("#.{6}".toRegex())
                    "ecl" -> eyeColors.contains(value)
                    "pid" -> value.matches("\\d{9}".toRegex())
                    else -> false
                }

                if (fieldIsValid) {
                    passportFields.add(key)
                }
            }
        }
    }
    return valid.toString()

}

fun day5(input: List<String>): String {
    return input.map { seat ->

        val row: Int = seat.substring(0, 7)
            .replace("F", "0")
            .replace("B", "1")
            .toInt(2)
        val col = seat.substring(7)
            .replace("L", "0")
            .replace("R", "1")
            .toInt(2)
        row * 8 + col
    }.maxOrNull().toString()
}

fun day5Part2(input: List<String>): String {
    val ids = input.map { seat ->

        val row: Int = seat.substring(0, 7)
            .replace("F", "0")
            .replace("B", "1")
            .toInt(2)
        val col = seat.substring(7)
            .replace("L", "0")
            .replace("R", "1")
            .toInt(2)
        row * 8 + col
    }.sorted()
    ids.forEachIndexed { index, seat ->
        if (index != 0 && index != ids.size - 1 && ids[index - 1] + 1 != ids[index])
            return (ids[index - 1] + 1).toString()
    }
    return "NaN"

}

fun day6(input: List<String>): String {

    val group = hashSetOf<Char>()
    var total = 0
    input.forEach { line ->
        if (line.isBlank()) {
            total += group.size
            group.clear()
        } else {
            line.forEach { group.add(it) }
        }
    }
    total += group.size

    return total.toString()
}

fun day6Part2(input: List<String>): String {
    val group = hashSetOf<Char>()
    var total = 0
    var newGroup = true
    input.forEach { line ->
        if (line.isBlank()) {
            total += group.size
            group.clear()
            newGroup = true
        } else {
            if (newGroup) {
                group.addAll(line.map { it })
                newGroup = false
            } else {
                group.retainAll(line.map { it })
            }
        }
    }
    total += group.size

    return total.toString()
}
