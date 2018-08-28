package com.agoda.boots.strict

import com.agoda.boots.Bootable
import com.agoda.boots.Key
import java.util.*

/**
 * This class is used to determine SCC (Strong connected components) in the
 * [Bootable]'s dependency tree every time you invoke [add()][com.agoda.boots.Boots.add].
 *
 * It's implementation is based on Tarjan algorithm of finding SCCs in directed graph.
 */
class SccFinder(private val boots: List<Bootable>) {

    private val nodes = boots.map { it.key }.toTypedArray()
    private val marked = Array(nodes.size) { false }
    private val low = Array(nodes.size) { 0 }
    private val stack = Stack<Int>()

    private var pre = 0

    private val scc = mutableListOf<List<Key>>()

    /**
     * Determines if given [Bootable] set contains SCC
     * through it's dependency tree.
     *
     * @return Set of SCCs each represented by Set<[Key]>
     */
    fun find(): List<List<Key>> {
        for (i in 0 until nodes.size) if (!marked[i]) dfs(i)
        return scc
    }

    private fun dfs(node: Int) {
        marked[node] = true
        stack.push(node)

        low[node] = pre++
        var min = low[node]

        boots.find { it.key == nodes[node] }!!.dependencies.forEach {
            val i = nodes.indexOf(it)
            if (!marked[i]) dfs(i)
            if (low[i] < min) min = low[i]
        }

        if (min < low[node]) {
            low[node] = min
            return
        }

        val sc = mutableListOf<Key>()

        do {
            val pop = stack.pop()
            sc.add(nodes[pop])
            low[pop] = nodes.size
        } while (pop != node)

        if (sc.size > 1) {
            scc.add(sc)
        }
    }
}
