package com.agoda.boots

/**
 * Exception that aggregates the reasons of failure in case a [report][Report] has
 * a [failed][Status.Failed] state and is not with [single][Key.Single] key.
 */
class BootException(reasons: Map<Key, Throwable>) : Throwable() {
    override val message = StringBuilder().apply {
        appendln("Problems were encountered while booting sequence!")
        reasons.forEach { key, value ->
            appendln("$key -> ${value.message}")
        }
    }.toString()
}

/**
 * Exception that indicates that strong connected components were found in
 * the [bootables][Bootable] graph.
 * @param scc list of strong connected components
 */
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

/**
 * Exception that indicates that incorrect connected components were found in
 * the [bootables][Bootable] graph.
 * @param icc list of incorrect connected components
 */
class IncorrectConnectedBootException(icc: List<Pair<Key, Key>>) : Throwable() {
    override val message = StringBuilder().apply {
        appendln("Incorrect connected components are not allowed!")
        icc.forEach {
            appendln("Components: ${it.first} -> ${it.second}")
        }
    }.toString()
}
