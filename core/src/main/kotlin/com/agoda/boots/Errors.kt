package com.agoda.boots

class BootException(reasons: Map<Key, Throwable>) : Throwable() {
    override val message = StringBuilder().apply {
        appendln("Problems were encountered while booting sequence!")
        reasons.forEach { key, value ->
            appendln("$key -> ${value.message}")
        }
    }.toString()
}

class StrongConnectedBootException(scc: List<List<Key>>) : Throwable() {
    override val message = StringBuilder().apply {
        appendln("Strong connected components are not allowed!")
        scc.forEach {
            append("Components: ")
            it.forEachIndexed { i, key ->
                append(key)
                if (i < it.size - 1) append(" -> ")
            }
            appendln()
        }
    }.toString()
}

class IncorrectConnectedBootException(icc: List<Pair<Key, Key>>) : Throwable() {
    override val message = StringBuilder().apply {
        appendln("Incorrect connected components are not allowed!")
        icc.forEach {
            appendln("Components: ${it.first} -> ${it.second}")
        }
    }.toString()
}
