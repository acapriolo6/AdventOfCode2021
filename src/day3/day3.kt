package day3

import common.readFileAsLinesUsingReadLines
import common.split

fun main(args: Array<String>) {
    val elements = readFileAsLinesUsingReadLines("src/day3/Input.txt")
//    println("Larger measurements: ${calculateDirection(elements)}")
//    println("Larger measurements 3 windows: ${calculatePositionWithAim(elements)}")
}
//
//fun calculateDirection(elements: List<String>): Int {
//    val direction = mutableMapOf(Pair("forward", 0), Pair("down", 0), Pair("up", 0))
//    elements.forEach {
//        val split = it.split()
//        direction[split[0].toLowerCase()] = direction[split[0].toLowerCase()]!! + split.get(1).toInt()
//    }
//    return direction["forward"]!! * (direction["down"]!! - direction["up"]!!)
//}
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