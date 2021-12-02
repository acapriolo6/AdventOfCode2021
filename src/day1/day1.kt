package day1

import common.readFileAsLinesUsingReadLines

fun main(args: Array<String>) {
    val elements = readFileAsLinesUsingReadLines("src/day1/InputDay1.txt")
    println("Larger measurements: ${countLargerElements(elements)}")
    println("Larger measurements 3 windows: ${countLargerElementsThreeWindow(elements)}")
}

fun countLargerElements(elements: List<String>): Int {
    if (elements.size < 0) return 0
    var counter = 0
    var previousElement = elements.first()
    elements.forEach {
        if (it.toInt() > previousElement.toInt()) {
            counter++
        }
        previousElement = it
    }
    return counter

}

fun countLargerElementsThreeWindow(elements: List<String>): Int {
    val windowSize = 3
    var counter = 0
    if (elements.size < windowSize) return 0
    for (i in elements.withIndex()) {
        if (i.index - windowSize < 0) {
            continue
        }
        val previousValue = elements[i.index - windowSize].toInt()
        val currentValue = i.value.toInt()

        if (currentValue > previousValue) {
            counter++
        }
    }
    return counter
}