import java.io.File

fun main(args: Array<String>) {
    val day = if (args.isNotEmpty()) args.first() else return
    println("Solving $day day!")
    val input = File("days/$day").readLines()
    println("solution is ${solve(day, input)}")
}

fun solve(task: String, input: List<String>): String? = solutions[task]?.invoke(input)

val solutions = mapOf<String, (input: List<String>) -> String>("1" to ::day1)

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