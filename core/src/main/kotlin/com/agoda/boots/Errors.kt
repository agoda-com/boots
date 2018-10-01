package com.agoda.boots

/**
 * Exception that aggregates the reasons of failure in case a [report][Report] has
 * a [failed][Status.Failed] state and is not with [single][Key.Single] key.
 * @param reasons map of throwables associated with keys of bootables that threw it.
 */
class BootException(reasons: Map<Key, Throwable>) : Throwable() {
    override val message = StringBuilder().apply {
        appendln("Problems were encountered while booting sequence!")
        for ((key, value) in reasons) { appendln("$key -> ${value.message}") }
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
        for (it in scc) {
            append("Components: ")
            for (i in 0 until it.size) {
                append(it[i])
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
        for (it in icc) { appendln("Components: ${it.first} -> ${it.second}") }
    }.toString()
}
