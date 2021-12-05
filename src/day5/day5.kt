package day5

import common.readFileAsLinesUsingReadLines
import common.splitAndRemoveEmpty
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main(args: Array<String>) {
    val elements = readFileAsLinesUsingReadLines("src/day5/Input.txt")
    println("${calculateNumberOfOverlappedLinesUsingAMatrix(elements)}")
    println("========")
    println("${calculateNumberOfOverlappedLinesUsingAMatrix(elements, true)}")
    println("+++++++++")
    println("${calculateNumberOfOverlappedLinesUsingAMap(elements)}")
    println("========")
    println("${calculateNumberOfOverlappedLinesUsingAMap(elements, true)}")
}

fun calculateNumberOfOverlappedLinesUsingAMatrix(rows: List<String>, includeDiagonal: Boolean = false): Int {
    val maxNumber = 1000
    val coordinates = Array(maxNumber){ Array(maxNumber) {0} }

    for (row in rows) {
        val _coordinates = row.splitAndRemoveEmpty(" -> ")
        val firstCoordinate = _coordinates[0].splitAndRemoveEmpty(",")
        val secondCoordinate = _coordinates[1].splitAndRemoveEmpty(",")
        val x1 = firstCoordinate[0].toInt()
        val y1 = firstCoordinate[1].toInt()
        val x2 = secondCoordinate[0].toInt()
        val y2 = secondCoordinate[1].toInt()
        if (x1 == x2) {
            for (j in min(y1, y2)..max(y1, y2)) {
                coordinates[j][x1]++
            }
        } else if (y1 == y2) {
            for (i in min(x1, x2)..max(x1, x2)) {
                coordinates[y1][i]++
            }
        } else if (includeDiagonal && abs(x1 - x2) == abs(y1 -y2)) {
            for (i in 0..abs(x1 - x2)) {
                coordinates[y1 + calculateSign(y1, y2, i)][x1 + calculateSign(x1, x2, i)]++
            }
        }
    }
//    for (i in 0..maxNumber-1) {
//        for (j in 0..maxNumber-1) {
//            print(coordinates[i][j])
//        }
//        println()
//    }
    coordinates.map { it.count { el -> el > 2 } }.sum()
    return coordinates.map { it.count { el -> el >= 2 } }.sum()
}



fun calculateNumberOfOverlappedLinesUsingAMap(rows: List<String>, includeDiagonal: Boolean = false): Int {
    val coordinates = HashMap<String, Int>()

    for (row in rows) {
        val listOfCoordinates = row.splitAndRemoveEmpty(" -> ")
        val firstCoordinate = listOfCoordinates[0].splitAndRemoveEmpty(",")
        val secondCoordinate = listOfCoordinates[1].splitAndRemoveEmpty(",")
        val x1 = firstCoordinate[0].toInt()
        val y1 = firstCoordinate[1].toInt()
        val x2 = secondCoordinate[0].toInt()
        val y2 = secondCoordinate[1].toInt()
        if (x1 == x2) {
            for (j in min(y1, y2)..max(y1, y2)) {
                coordinates["${j},${x1}"] = coordinates["${j},${x1}"]?.plus(1) ?: 1
            }
        } else if (y1 == y2) {
            for (i in min(x1, x2)..max(x1, x2)) {
                coordinates["${y1},${i}"] = coordinates["${y1},${i}"]?.plus(1) ?: 1
            }
        } else if (includeDiagonal && abs(x1 - x2) == abs(y1 -y2)) {
            for (i in 0..abs(x1 - x2)) {
                coordinates["${y1 + calculateSign(y1, y2, i)},${x1 + calculateSign(x1, x2, i)}"] = coordinates["${y1 + calculateSign(y1, y2, i)},${x1 + calculateSign(x1, x2, i)}"]?.plus(1) ?: 1
            }
        }
    }
    return coordinates.values.count { it >= 2 }
}

fun calculateSign(a: Int, b: Int, i: Int): Int {
    return if (b-a > 0) i else (-1 * i)
}
