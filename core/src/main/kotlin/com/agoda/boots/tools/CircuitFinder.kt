package com.agoda.boots.tools

import com.agoda.boots.Bootable
import com.agoda.boots.Key
import java.util.*
import kotlin.math.min

class CircuitFinder(private val boots: Set<Bootable>) {
    private val nodes = boots.map { it.key }.toTypedArray()

    private val cycles = mutableListOf<List<Key>>()
    private val matrix = Array(nodes.size) { Array(nodes.size) { 0 } }
    private val blocked = Array(nodes.size) { false }
    private val blists = Array(nodes.size) { mutableListOf<Int>() }
    private val stack = mutableListOf<Int>()
    private var counter = 0

    private val subMatrix = Array(nodes.size) { Array(nodes.size) { 0 } }
    private val visited = Array(nodes.size) { false }
    private val lowlink = Array(nodes.size) { 0 }
    private val help = Array(nodes.size) { 0 }
    private val sccs = mutableListOf<List<Int>>()
    private val sccStack = Stack<Int>()
    private var sccCounter = 0


    init {
        nodes.forEachIndexed { i, k ->
            val bootable = boots.find { it.key == k }!!

            nodes.forEachIndexed { j, m ->
                if (bootable.dependencies.contains(m)) {
                    matrix[i][j]++
                }
            }
        }
    }

    fun find(): List<List<Key>> {
        while (true) {
            val result = adjacency(counter)

            if (result == null || result.second.isEmpty()) {
                return cycles
            }

            val scc = result.second
            counter = result.first

            for (i in 0 until scc.size) {
                if (scc[i].isNotEmpty()) {
                    blocked[i] = false
                    blists[i] = mutableListOf()
                }
            }

            find(counter, counter, scc)
            counter++
        }
    }

    private fun find(node: Int, start: Int, scc: Array<List<Int>>): Boolean {
        var found = false

        stack.add(node)
        blocked[node] = true

        for (i in 0 until scc[node].size) {
            val dep = scc[node][i]

            if (dep == start) {
                found = true
                val cycle = mutableListOf<Key>()

                for (j in 0 until stack.size) {
                    cycle.add(nodes[stack[j]])
                }

                cycles.add(cycle)
            } else if (!blocked[dep]) {
                found = find(dep, start, scc)
            }
        }

        if (found) {
            unblock(node)
        } else {
            for (i in 0 until scc[node].size) {
                val dep = scc[node][i]

                if (!blists[dep].contains(node)) {
                    blists[dep].add(node)
                }
            }
        }

        return found.also {
            stack.remove(node)
        }
    }

    private fun unblock(node: Int) {
        blocked[node] = false
        val bnode = blists[node]

        while (bnode.isNotEmpty()) {
            val dep = bnode.removeAt(0)

            if (blocked[dep]) {
                unblock(dep)
            }
        }
    }

    private fun adjacency(node: Int): Pair<Int, Array<List<Int>>>? {
        subMatrix(node)

        for (i in node until matrix.size) {
            if (!visited[i]) {
                scc(i)
                val lowest = lowest()

                if (lowest.isNotEmpty() && !lowest.contains(node) && !lowest.contains(node + 1)) {
                    return adjacency(node + 1)
                } else {
                    val sub = subAdjacency(lowest)

                    for (j in 0 until sub.size) {
                        if (sub[j].isNotEmpty()) {
                            return j to sub
                        }
                    }
                }
            }
        }

        return null
    }

    private fun subMatrix(node: Int) {
        subMatrix.clear()

        for (i in node until matrix.size) {
            for (j in 0 until matrix.size) {
                if (matrix[i][j] >= 0 && matrix[i][j] >= node) {
                    subMatrix[i][j]++
                }
            }
        }
    }

    private fun subAdjacency(nodes: List<Int>): Array<List<Int>> {
        val result = Array(nodes.size) { emptyList<Int>() }

        for (i in 0 until nodes.size) {
            val node = nodes[i]
            val list = mutableListOf<Int>()

            for (j in 0 until subMatrix[node].size) {
                val succ = subMatrix[node][j]
                if (nodes.contains(succ)) {
                    list.add(succ)
                }
            }

            result[node] = list
        }

        return result
    }

    private fun scc(node: Int) {
        visited[node] = true
        sccStack.add(node)

        sccCounter++
        lowlink[node] = sccCounter
        help[node] = sccCounter

        for (i in 0 until matrix.size) {
            val w = subMatrix[node][i]

            if (w > 0) {
                if (!visited[i]) {
                    scc(i)
                    lowlink[node] = min(lowlink[node], lowlink[i])
                } else if (help[i] < help[node]) {
                    if (sccStack.contains(i)) {
                        lowlink[node] = min(lowlink[node], help[i])
                    }
                }
            }
        }

        if (lowlink[node] == help[node] && sccStack.isNotEmpty()) {
            var next: Int
            val scc = mutableListOf<Int>()

            do {
                next = sccStack.pop()
                scc.add(next)
            } while (help[next] > help[node])

            if (scc.isNotEmpty()) {
                sccs.add(scc)
            }
        }
    }

    private fun lowest(): List<Int> {
        var min = nodes.size
        var scc = listOf<Int>()

        for (i in 0..sccs.size) {
            for (j in 0..sccs[i].size) {
                if (sccs[i][j] < min) {
                    scc = sccs[i]
                    min = sccs[i][j]
                }
            }
        }

        return scc
    }

    private fun Array<Array<Int>>.clear() {
        for (i in 0 until size) {
            for (j in 0 until this[i].size) {
                this[i][j] = 0
            }
        }
    }
}