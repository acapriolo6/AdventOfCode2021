package common

import java.io.File

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
        = File(fileName).readLines()

fun String.split() = this.split(" ")
fun String.splitAndRemoveEmpty(delimeter: String) = this.split(delimeter).filter { it.isNotEmpty() }
fun String.sort() = String(this.toCharArray().apply { sort() })