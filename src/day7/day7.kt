package day7

import common.readFileAsLinesUsingReadLines
import common.splitAndRemoveEmpty
import kotlin.math.abs

fun main(args: Array<String>) {
    val elements = readFileAsLinesUsingReadLines("src/day7/Input.txt")
    println("${minFuel(elements)}")
    println("========")
    println("${minFuel2(elements)}")

}

fun minFuel(rows: List<String>): Int {
    var elements = rows[0].splitAndRemoveEmpty(",").map { it.toInt() }

    var currentFuel = 0
    elements = elements.sorted()
    val median: Int

    if (elements.count() % 2 == 1) {
        median = elements[(elements.count()) / 2]
    } else {
        median = (elements[(elements.count()) / 2] + elements[(elements.count() - 1) / 2]) / 2
    }
    for (el in elements) {
        currentFuel += abs(el-median)
    }
    return currentFuel
}

fun minFuel2(rows: List<String>): Int {
    val lanternFishes = rows[0].splitAndRemoveEmpty(",").map { it.toInt() }.toMutableList()

    var minFuel = Int.MAX_VALUE

    for (i in 0..lanternFishes.max()!!) {
        val currentFuel = 0
        if (currentFuel < minFuel) {
            minFuel = currentFuel
        }
    }
    return minFuel
}
