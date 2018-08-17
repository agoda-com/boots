package com.agoda.boots

class BootException(reasons: Map<Key, Throwable>) : Throwable()

class StrongConnectedBootException(scc: Set<Set<Key>>) : Throwable() {
    override val message = StringBuilder().apply {
        scc.forEach {
            append("Strong connected component: ")
            it.forEachIndexed { i, key ->
                append(key)
                if (i < it.size - 1) append(" -> ")
            }
            appendln()
        }
    }.toString()
}

class IncorrectConnectedBootException(icc: Set<Pair<Key, Key>>) : Throwable() {
    override val message = StringBuilder().apply {
        icc.forEach {
            appendln("Incorrect connected component: ${it.first} -> ${it.second}")
        }
    }.toString()
}
