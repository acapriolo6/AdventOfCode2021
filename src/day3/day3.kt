package day3

import common.readFileAsLinesUsingReadLines
import common.splitAndRemoveEmpty

fun main(args: Array<String>) {
    val elements = readFileAsLinesUsingReadLines("src/day3/Input.txt")
    println("${calculatePowerConsumption(elements)}")
    println("${calculateLifeSupportRating(elements)}")
}
//
fun calculatePowerConsumption(elements: List<String>): Int {
    val numberOfOne = IntArray(elements.get(0).length)
    elements.forEach {
        val digit = it.splitAndRemoveEmpty("")
        for (withIndex in digit.withIndex()) {
            if (withIndex.value == "1") {
                numberOfOne[withIndex.index] = numberOfOne[withIndex.index].or(0) + 1
            }
        }
    }
    val gamma = numberOfOne.map {
        if (it > (elements.size / 2)) {
            "1"
        } else
            "0"
    }.joinToString("")

    val epsilon = gamma.splitAndRemoveEmpty("").joinToString("") { if (it == "0") "1" else "0" }

    return gamma.toInt(2) * epsilon.toInt(2)
}


fun calculateLifeSupportRating(elements: List<String>): Int {
    val gamma = calculateGamma(elements)

    val epsilon = gamma.splitAndRemoveEmpty("").joinToString("") { if (it == "0") "1" else "0" }

    val oxygen: String = calculateOxygenGeneratorRating(0, gamma, elements.toMutableList())

    val CO2 = calculateC02GeneratorRating(0, epsilon, elements.toMutableList())
    return oxygen.toInt(2) * CO2.toInt(2)
}

fun calculateGamma(elements: List<String>): String {
    val numberOfOne = IntArray(elements.get(0).length)
    elements.forEach {
        val digit = it.splitAndRemoveEmpty("")
        for (withIndex in digit.withIndex()) {
            if (withIndex.value == "1") {
                numberOfOne[withIndex.index] = numberOfOne[withIndex.index].or(0) + 1
            }
        }
    }
    val gamma = numberOfOne.map {
        if (it >= (elements.size / 2) + (elements.size % 2)) {
            "1"
        } else
            "0"
    }.joinToString("")
    return gamma
}

fun calculateOxygenGeneratorRating(index:Int, epsilon: String, elements: List<String>): String {
    if (elements.size == 1) {
        return elements.first()
    }
    val filtered = elements.filter { it.splitAndRemoveEmpty("")[index] == epsilon.splitAndRemoveEmpty("")[index] }
    val gamma = calculateGamma(filtered)
    return calculateOxygenGeneratorRating(index+1, gamma, filtered.toMutableList())
}

fun calculateC02GeneratorRating(index:Int, epsilon: String, elements: List<String>): String {
    if (elements.size == 1) {
        return elements.first()
    }
    val filtered = elements.filter { it.splitAndRemoveEmpty("")[index] == epsilon.splitAndRemoveEmpty("")[index] }
    val gamma = calculateGamma(filtered).splitAndRemoveEmpty("").joinToString("") { if (it == "0") "1" else "0" }
    return calculateC02GeneratorRating(index+1, gamma, filtered.toMutableList())
}

//
//
//fun calculatePositionWithAim(elements: List<String>): Int {
//    var aim = 0
//    var depth = 0
//    var horizontal  = 0
//    elements.forEach {
//        val split = it.split()
//        with(split.get(1).toInt()) {
//            if ("down" == split.get(0).toLowerCase()) {
//                aim += this
//            }
//            if ("up" == split.get(0).toLowerCase()) {
//                aim -= this
//            }
//            if ("forward" == split.get(0).toLowerCase()) {
//                horizontal += this
//                depth += aim * this
//            }
//        }
//
//    }
//    return horizontal * depth
//}