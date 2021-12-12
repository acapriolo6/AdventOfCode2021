package day11

import common.readFileAsLinesUsingReadLines
import common.splitAndRemoveEmpty

fun main(args: Array<String>) {
    val elements = readFileAsLinesUsingReadLines("src/day11/Input.txt")
    println("${syntaxErrorScore(elements, 10)}")
    println("${syntaxErrorScore(elements, 100)}")
    println("========")
    println("${countFlashesPart2(elements)}")

}

fun syntaxErrorScore(elements: List<String>, steps: Int): Int {
    var flashes = 0
    val octopuses = elements.map { it.splitAndRemoveEmpty("").map { it.toInt() }.toMutableList() }.toMutableList()

    for (step in 1..steps) {
        val flashed = countFlashes(octopuses, 0,0, mutableListOf())
        flashes += flashed
        if (flashed == octopuses.size * octopuses.first().size)
        {
            println("2: ${step}")
        }
    }
    return flashes
}



fun countFlashesPart2(elements: List<String>): Int {
//    var flashes = 0
    val octopuses = elements.map { it.splitAndRemoveEmpty("").map { it.toInt() }.toMutableList() }.toMutableList()

    var allFlashed = false
    var step = 1
    while (!allFlashed) {
        val flashed = countFlashes(octopuses, 0,0, mutableListOf())
//        flashes += flashed
        allFlashed = flashed == octopuses.size * octopuses.first().size
        if (!allFlashed) step++
    }
    return step
}

fun countFlashes(octopuses: List<MutableList<Int>>, x: Int, y: Int, flashed: MutableList<String>): Int {
    if (x >= octopuses.size) {
        return 0
    }
    if (y >= octopuses[x].size) {
        return countFlashes(octopuses, x+1, 0, flashed)
    }
    if (octopuses[x][y] == 0 && flashed.contains("${x}${y}")){
        return countFlashes(octopuses, x, y+1, flashed)
    }
    if (octopuses[x][y] != 9) {
        octopuses[x][y]++
        return countFlashes(octopuses, x, y+1, flashed)
    }
    octopuses[x][y] = 0
    flashed.add("${x}${y}")
    return 1 +
            flash(octopuses, x, y-1, flashed) +
            flash(octopuses, x, y+1, flashed) +
            flash(octopuses, x-1, y, flashed) +
            flash(octopuses, x-1, y-1, flashed) +
            flash(octopuses, x-1, y+1, flashed) +
            flash(octopuses, x+1, y, flashed) +
            flash(octopuses, x+1, y-1, flashed) +
            flash(octopuses, x+1, y+1, flashed) +
            countFlashes(octopuses, x, y+1, flashed)
}

fun flash(octopuses: List<MutableList<Int>>, i: Int, j: Int, flashed: MutableList<String>): Int {
    if (i < 0  || i >= octopuses.size) {
        return 0
    }
    if (j < 0 || j >= octopuses[i].size) {
        return 0
    }
    if (octopuses[i][j] == 0 && flashed.contains("${i}${j}")){
        return 0
    }
    if (octopuses[i][j] != 9) {
        octopuses[i][j]++
        return 0
    }
    octopuses[i][j] = 0
    flashed.add("${i}${j}")
    return 1 + 
            flash(octopuses, i, j-1, flashed) +
            flash(octopuses, i, j+1, flashed) +
            flash(octopuses, i-1, j, flashed) +
            flash(octopuses, i-1, j-1, flashed) +
            flash(octopuses, i-1, j+1, flashed) +
            flash(octopuses, i+1, j, flashed) +
            flash(octopuses, i+1, j-1, flashed) +
            flash(octopuses, i+1, j+1, flashed) 
}
