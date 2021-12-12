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
    val findPathsPart2 = findPathsPart2(graph.find("start"), Node("end"), graph, mutableMapOf())
    println("${findPaths}")
    println("========")
    println("${findPathsPart2}")
}

fun findPaths(currentNode: Node?, endNode: Node, graph: MutableSet<Node>, paths: MutableSet<String>): Int {
    if (endNode.name == currentNode?.name) {
        return 1
    }
    var path = 0
    for (node in currentNode?.linkedNodes ?: mutableSetOf()) {
        val numberOfVisits = if (paths.contains(node)) 1 else 0
        paths.add(node)
        if (node.isUpperCase()) {
            path += findPaths(graph.find(node), endNode, graph, paths)
        }
        if (node == endNode.name || (node.isLowerCase() && numberOfVisits < 1)) {
            path += findPaths(graph.find(node), endNode, graph, paths)
            paths.remove(node)
        }
    }
    return path
}


fun findPathsPart2(currentNode: Node?, endNode: Node, graph: MutableSet<Node>, paths: MutableMap<String, Int>, maxVisitLowerCase: Int = 2): Int {
    if (endNode.name == currentNode?.name) {
        return 1
    }
    var path = 0
    for (node in currentNode?.linkedNodes ?: mutableSetOf()) {
        val numberOfVisits = if (node == "end") 0 else  paths[node] ?: 0
        if (node.isUpperCase()) {
            path += findPathsPart2(graph.find(node), endNode, graph, paths, maxVisitLowerCase)
        }
        if (node == endNode.name || (node.isLowerCase() && numberOfVisits < maxVisitLowerCase)) {
            paths[node] = numberOfVisits + 1
            path += findPathsPart2(graph.find(node), endNode, graph, paths, if (paths[node]!! > 1 && paths[node] == maxVisitLowerCase) 1 else maxVisitLowerCase)
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
