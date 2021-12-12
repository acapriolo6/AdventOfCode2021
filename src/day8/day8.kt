package day8

import common.readFileAsLinesUsingReadLines
import common.sort
import common.splitAndRemoveEmpty

fun main(args: Array<String>) {
    val elements = readFileAsLinesUsingReadLines("src/day8/Input.txt")
    println("${segments(elements)}")
    println("========")
    println("${segmentsPart2(elements)}")

}

fun segments(rows: List<String>): Int {
    val map = mutableMapOf(  Pair( 2, "1"), Pair( 4, "4"), Pair( 3, "7"), Pair(7, "8")  )

    var count = 0
    for (row in rows) {
        val line = row.splitAndRemoveEmpty("|")
        val words = line[1].splitAndRemoveEmpty(" ")
        count += words.count { it -> map.contains(it.length) }
    }
    return count
}

fun segmentsPart2(rows: List<String>): Int {

    var count = 0
    for (row in rows) {
        val line = row.splitAndRemoveEmpty("|")
        val words = line[1].splitAndRemoveEmpty(" ")
        val codes = line[0].splitAndRemoveEmpty(" ").toMutableList()
        val knownElements = knownElements(codes)
        codes.removeAll(knownElements.values)
        findOthers(knownElements, codes)
        val reversed = knownElements.entries.associate{(k,v)-> v.sort() to k}
        var number = ""
        for (word in words) {
            val sorted = word.sort()
            val value = reversed[sorted]
            number += value
        }
        count += number.toInt()
    }
    return count
}

fun findOthers(knownElements: MutableMap<String, String>, codes: List<String>) {
    val up = diff(knownElements["7"]!!, knownElements["1"]!!)
    val `up+4` = knownElements["4"] + up
    val `6l` = codes.filter { it.length == 6 }
    val `9` = `6l`.first { diff(it, `up+4`).length == 1 }
    val leftDown = diff(knownElements["8"]!!, `9`)
    val `5l` = codes.filter { it.length == 5 }
    val `2` = `5l`.first { it.contains(leftDown) }
    val rightUp = `5l`.map { diff(`2`, it + leftDown) }.first { it.length == 1 }
    val `3` = `5l`.first { it != `2` && it.contains(rightUp) }
    val `5` = `5l`.first { !listOf(`2`, `3`).contains(it) }
    val `6` = `6l`.first { diff(it, `5` + leftDown).isEmpty() }
    val `0` = `6l`.first { !listOf(`6`, `9`).contains(it) }
    knownElements["0"] = `0`
    knownElements["2"] = `2`
    knownElements["3"] = `3`
    knownElements["5"] = `5`
    knownElements["6"] = `6`
    knownElements["9"] = `9`
}

fun diff(a: String , b: String): String {
    return a.filter { !b.contains(it) }
}

fun knownElements(codes: List<String>): MutableMap<String, String> {
    val map = mapOf(  2 to "1", 4 to "4", 3 to "7", 7 to "8"  )
    val result = mutableMapOf<String, String>()
    for (code in codes) {
        val value = map[code.length]
        if (value != null) {
            result[value] = code
        }
    }
    return result
}