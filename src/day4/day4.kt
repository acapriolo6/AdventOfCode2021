package day4

import common.readFileAsLinesUsingReadLines
import common.splitAndRemoveEmpty

fun main(args: Array<String>) {
    val elements = readFileAsLinesUsingReadLines("src/day4/Input.txt")
    println("${calculateFirstFinalScore(elements)}")
    println("${calculateLastFinalScore(elements)}")
}

fun calculateFirstFinalScore(rows: List<String>): Int {
    val numbers = rows[0].splitAndRemoveEmpty(",").map { it.toInt() }
    val boards = rows.toMutableList()
    boards.removeIf { it.isEmpty() }

    var winNumber: Int? = null
    var boardSum = 0
    for (i in 1..boards.size - 5 step 5) {
        val currentBoard = calculateBoard(i, boards)
        val currentWinNumber = calculateWinNumber(numbers, currentBoard)
        if (currentWinNumber.first >= 0 && winNumber == null) {
            winNumber = currentWinNumber.first
            boardSum = currentWinNumber.second
        } else if (currentWinNumber.first >= 0 && numbers.indexOf(winNumber) > numbers.indexOf(currentWinNumber.first)) {
            winNumber = currentWinNumber.first
            boardSum = currentWinNumber.second
        }
    }

    return (winNumber ?: 0) * boardSum

}

fun calculateLastFinalScore(rows: List<String>): Int {
    val numbers = rows[0].splitAndRemoveEmpty(",").map { it.toInt() }
    val boards = rows.toMutableList()
    boards.removeIf { it.isEmpty() }

    var winNumber: Int? = null
    var boardSum = 0
    for (i in 1..boards.size - 5 step 5) {
        val currentBoard = calculateBoard(i, boards)
        val currentWinNumber = calculateWinNumber(numbers, currentBoard)
        if (currentWinNumber.first >= 0 && winNumber == null) {
            winNumber = currentWinNumber.first
            boardSum = currentWinNumber.second
        } else if (currentWinNumber.first >= 0 && numbers.indexOf(winNumber) < numbers.indexOf(currentWinNumber.first)) {
            winNumber = currentWinNumber.first
            boardSum = currentWinNumber.second
        }
    }

    return (winNumber ?: 0) * boardSum

}

fun calculateBoard(i: Int, rows: List<String>): List<List<Int>> {
    var c = 0
    val board = Array(5) { IntArray(5) }
    for (j in i..i + 4) {
        board[c++] = rows[j].splitAndRemoveEmpty(" ").map { it.toInt() }.toIntArray()
    }
    return board.map { it.toList() }.toList()
}

fun calculateWinNumber(numbers: List<Int>, board: List<List<Int>>): Pair<Int, Int> {
    var c = 4
    var match = false
    var currentNumbers: List<Int>? = null
    while (c < numbers.size && !match) {
        currentNumbers = numbers.slice(IntRange(0, c))
        var i = -1
        while (++i < 5 && !match) {
            match = currentNumbers.containsAll(board[i]) || currentNumbers.containsAll(getColumn(i, board))
        }
        c++
    }
    if (match) {
        c--
        return Pair(numbers[c], sumBoard(currentNumbers, board))
    }
    return Pair(-1, -1)
}

fun sumBoard(numbers: List<Int>?, board: List<List<Int>>): Int {
    var c = 0
    for (i in board.indices) {
        for (j in board[i].indices) {
            c += if (numbers?.contains(board[i][j])!!) 0 else board[i][j]
        }
    }
    return c
}

fun getColumn(j: Int, board: List<List<Int>>): List<Int> {
    val result = Array<Int>(5) { 0 }

    for (i in 0..4) {
        result[i] = board[i][j]
    }

    return result.toList()
}
