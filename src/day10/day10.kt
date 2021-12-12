package day10

import common.readFileAsLinesUsingReadLines

fun main(args: Array<String>) {
    val elements = readFileAsLinesUsingReadLines("src/day10/Input.txt")
    println("${syntaxErrorScore(elements)}")
    println("========")
    println("${syntaxErrorScorePart2(elements)}")

}

fun syntaxErrorScore(elements: List<String>): Int {
    var score = 0

    val config = mapOf(
        ")" to 3,
        "]" to 57,
        "}" to 1197,
        ">" to 25137
    )
    val configElement = mapOf(
        ")" to "(",
        "]" to "[",
        "}" to "{",
        ">" to "<"
    )

    for (line in elements) {
        var correct = true
        var i = 0
        val openElements = mutableListOf<String>()
        while (i < line.length && correct) {
            val element = line[i].toString()
            if (!configElement.keys.contains(element)) {
                openElements.add(element)
            } else {
                if (openElements.size > 0
                    && openElements.last() != configElement[element]) {
                    correct = false
                    score += config[element]!!
                } else {
                    openElements.removeAt(openElements.lastIndex)
                }
            }
            i++
        }
    }
    return score
}

fun syntaxErrorScorePart2(elements: List<String>): Long {
    val scores = mutableListOf<Long>()

    val config = mapOf(
        ")" to 1,
        "]" to 2,
        "}" to 3,
        ">" to 4
    )
    val configElement = mapOf(
        ")" to "(",
        "]" to "[",
        "}" to "{",
        ">" to "<"
    )
    val reversed = configElement.entries.associateBy({ it.value }) { it.key }

    for (line in elements) {
        var correct = true
        var i = 0
        val openElements = mutableListOf<String>()
        while (i < line.length && correct) {
            val element = line[i].toString()
            if (!configElement.keys.contains(element)) {
                openElements.add(element)
            } else {
                if (openElements.size > 0
                    && openElements.last() != configElement[element] && i != line.length-1) {
                    correct = false
//                    score += config[element]!!
                } else {
                    openElements.removeAt(openElements.lastIndex)
                }
            }
            i++
        }
        if (correct && i == line.length) {

                var score : Long = 0
                for (missingElement in openElements.reversed()) {
                    score = (score * 5) + config[reversed[missingElement]]!!
                }
                scores.add(score)
        }
    }
    return scores.sortedDescending()[scores.size / 2]

}

//fun isCorrect(line: String): Pair<Boolean, String> {
//    val configElement = mapOf(
//        "(" to ")",
//        "[" to "]",
//        "{" to "}",
//        "<" to ">"
//    )
//    if (line.length == 0) {
//        return Pair(true, "")
//    }
//    if (line.length == 1) {
//        return Pair(false, line)
//    }
//    if (line.length == 2) {
//        val closeCharacter = configElement[line[0].toString()]
//        return Pair(closeCharacter == line[1].toString(), line[1].toString())
//    }
//
//}
