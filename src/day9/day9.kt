package day9

import common.readFileAsLinesUsingReadLines
import common.splitAndRemoveEmpty

fun main(args: Array<String>) {
    val elements = readFileAsLinesUsingReadLines("src/day9/Input.txt")
    println("${riskLevel(elements)}")
    println("========")
    println("${riskLevelPart2(elements)}")

}

fun riskLevel(rows: List<String>): Int {
    val map = mutableListOf<List<Int>>()

    for (row in rows) {
        map.add(row.splitAndRemoveEmpty("").map { it.toInt() })
    }

    var risk = 0

    for (i in 0 until map.size) {
        for (j in map[i].indices) {
            val adjacentElements = calculateAdjacentElements(i, j, map)
            val value = map[i][j]
            val lowerElements = adjacentElements.filter { it <= value }
            if (lowerElements.isEmpty()) {
                risk += value + 1
            }
        }
    }
    return risk
}

fun riskLevelPart2(rows: List<String>): Int {
    val map = mutableListOf<List<Int>>()
    val basins = mutableMapOf<String, String>()

    for (row in rows) {
        map.add(row.splitAndRemoveEmpty("").map { it.toInt() })
    }

    var risk = 0

    for (i in 0 until map.size) {
        for (j in map[i].indices) {
            val adjacentElements = calculateAdjacentElements(i, j, map)
            val value = map[i][j]
            val lowerElements = adjacentElements.filter { it <= value }
            if (lowerElements.isEmpty()) {
                risk += value + 1
                val currentBasin = mutableSetOf<String>()
                val basin = basins(i, j, map, basins.keys.toSet(), currentBasin)
                basin.forEach { basins[it] = "${i}${j}" }
            }
        }
    }
    val eachCount = basins.entries.groupingBy { it.value }.eachCount()
    val sortedDescending = eachCount.values.sortedDescending()
    return sortedDescending[0] * sortedDescending[1] * sortedDescending[2]
}

fun basins(x: Int, y: Int, map: MutableList<List<Int>>, basins: Set<String>, currentBasin: MutableSet<String>): Set<String> {
    if (x < 0 || x >= map.size){
        return emptySet()
    }
    if (y < 0 || y >= map[x].size ) {
        return emptySet()
    }
    if (map[x][y] == 9 || basins.contains("${x}${y}") || currentBasin.contains("${x}${y}")) {
        return emptySet()
    }
    currentBasin.add("${x}${y}")
    currentBasin.addAll(
        basins(x-1, y,map, basins, currentBasin) +
                basins(x+1, y, map, basins, currentBasin) +
                basins(x, y-1, map, basins, currentBasin) +
                basins(x, y+1, map, basins, currentBasin)
    )
    return currentBasin


}


fun calculateAdjacentElements(i: Int, j: Int, map: List<List<Int>>): List<Int> {
    val element = mutableListOf<Int>()
    if (i - 1 >= 0 ) {
        element.add(map[i-1][j])
    }
    if (i + 1 < map.size ) {
        element.add(map[i+1][j])
    }
    if (j - 1 >= 0 ) {
        element.add(map[i][j-1])
    }
    if (j + 1 < map[i].size ) {
        element.add(map[i][j+1])
    }
    return element
}
