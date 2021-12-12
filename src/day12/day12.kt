package day12

import common.readFileAsLinesUsingReadLines
import common.splitAndRemoveEmpty

fun main(args: Array<String>) {
    val elements = readFileAsLinesUsingReadLines("src/day12/Input.txt")
    countPaths(elements)
}

fun countPaths(elements: List<String>) {
    val connectionList = elements.map { it.splitAndRemoveEmpty("-")}

    val graph = mutableSetOf<Node>()

    for (connections in connectionList) {
        val nodeA = graph.find(connections[0]) ?: Node(connections[0])
        val nodeB = graph.find(connections[1]) ?: Node(connections[1])
        val startNode = graph.find("start") ?: Node("start")
        if (nodeA == startNode) {
            nodeA.linkedNodes.add(nodeB.name)
        } else if (nodeB == startNode) {
            nodeB.linkedNodes.add(nodeA.name)
        } else {
            nodeA.linkedNodes.add(nodeB.name)
            nodeB.linkedNodes.add(nodeA.name)
        }
        graph.addAll(listOf(nodeA, nodeB, startNode))
    }


    val findPaths = findPaths(graph.find("start"), Node("end"), graph, mutableSetOf())
    val findPathsPart2 = findPathsPart2(graph.find("start"), Node("end"), graph, mutableMapOf(), 2)
    println("${findPaths}")
    println("========")
    println("${findPathsPart2}")
}

fun findPaths(currentNode: Node?, endNode: Node, graph: MutableSet<Node>, paths: MutableSet<String>, maxVisitLowerCase: Int = 1): Int {
    if (currentNode == null) {
        return 0
    }
    if (endNode.name == currentNode.name) {
        return 1
    }
    var path = 0
    for (node in currentNode.linkedNodes) {
        val numberOfVisits = paths.count { it == node && it != "end" }
        paths.add(node)
        if (node.isUpperCase()) {
            path += findPaths(graph.find(node), endNode, graph, paths)
        }
        if (node.isLowerCase() && numberOfVisits < maxVisitLowerCase) {
            path += findPaths(graph.find(node), endNode, graph, paths)
            paths.remove(node)
        }
    }
    return path
}


fun findPathsPart2(currentNode: Node?, endNode: Node, graph: MutableSet<Node>, paths: MutableMap<String, Int>, maxVisitLowerCase: Int = 1): Int {
    if (currentNode == null) {
        return 0
    }
    if (endNode.name == currentNode.name) {
        return 1
    }
    var path = 0
    for (node in currentNode.linkedNodes) {
        val numberOfVisits = if (node == "end") 0 else  paths[node] ?: 0
        val max = if (paths.filter { it.key != "start" && it.key != endNode.name && it.key.isLowerCase() }.map { it.value }.max() == maxVisitLowerCase) maxVisitLowerCase -1 else maxVisitLowerCase
        if (node.isUpperCase()) {
            path += (findPathsPart2(graph.find(node), endNode, graph, paths, max))
        }
        if (node == endNode.name || (node.isLowerCase() && numberOfVisits < max)) {
            paths[node] = numberOfVisits + 1
            path += (findPathsPart2(graph.find(node), endNode, graph, paths, max))
            paths[node] = (paths[node] ?: 0) -1
        }
    }
    return path
}

fun String.isLowerCase(): Boolean = this == this.toLowerCase()

fun String.isUpperCase(): Boolean = this != this.toLowerCase()


class Node (
    val name: String,
    var linkedNodes: MutableSet<String> = mutableSetOf()
) {
    override fun equals(other: Any?): Boolean {
        if (other is Node) {
            return this.name == other.name
        }
        return false
    }

    override fun hashCode(): Int {
        return this.name.hashCode()
    }
}

fun MutableSet<Node>.find(name: String): Node? = this.find { it.name == name }
