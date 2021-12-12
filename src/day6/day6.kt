package day6

import common.readFileAsLinesUsingReadLines
import common.splitAndRemoveEmpty
import java.math.BigDecimal

fun main(args: Array<String>) {
    val elements = readFileAsLinesUsingReadLines("src/day6/Input.txt")
    println("${countLanternFishes(elements)}")
    println("========")
    println("${countLanternFishesWithMap(elements, 256)}")
}

fun countLanternFishes(rows: List<String>, days: Int = 80): Int {
    val lanternFishes = rows[0].splitAndRemoveEmpty(",").map { it.toInt() }.toMutableList()

    for (i in 0 until days) {
        val elementToAdd = mutableListOf<Int>()
        for (element in lanternFishes.indices) {
            if (lanternFishes[element] == 0) {
                lanternFishes[element] = 6
                elementToAdd.add(8)
            } else {
                lanternFishes[element]--
            }
        }
        lanternFishes.addAll(elementToAdd)
    }
    return lanternFishes.count()
}

fun countLanternFishesWithMap(rows: List<String>, days: Int = 80): BigDecimal {
    val lanternFishes = rows[0].splitAndRemoveEmpty(",").map { it.toInt() }.toMutableList()
    val fishes = HashMap<Int, BigDecimal>()
    lanternFishes.forEach { fishes[it] = fishes.getOrDefault(it, BigDecimal.ZERO).add(BigDecimal.ONE) }

    for (i in 1..days) {
        val newFishes = fishes.getOrDefault(0, BigDecimal.ZERO)
        for (j in 0..7) {
            fishes[j] = fishes.getOrDefault(j+1, BigDecimal.ZERO)
        }
        fishes[8] = newFishes
        fishes[6] = fishes.getOrDefault(6, BigDecimal.ZERO).add(newFishes)

    }

    var new = BigDecimal(0)

    for (fish in fishes) {

        new = new.add(fish.value)
    }
    return new
}
