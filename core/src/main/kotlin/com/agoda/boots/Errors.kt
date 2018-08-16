package com.agoda.boots

class BootException(reasons: Map<Key, Throwable>) : Throwable()

class StrongConnectedBootException(scc: List<List<Key>>) : Throwable() {
    override val message = StringBuilder().apply {
        scc.forEachIndexed { i, list ->
            append("Strong Connected Component: ")
            list.forEachIndexed { j, key ->
                append(key)
                if (j < list.size - 1) append(" -> ")
            }
            if (i < scc.size - 1) appendln()
        }
    }.toString()
}
