package com.agoda.boots

class BootException(reasons: Map<Key, Throwable>) : Throwable()

class CircularBootException(circuits: List<List<Key>>) : Throwable() {
    override val message = StringBuilder().apply {
        circuits.forEachIndexed { index, list ->
            append("Circuit: ")
            list.forEachIndexed { index, key ->
                append(key)
                if (index < list.size - 1) append(" -> ")
            }
            if (index < circuits.size - 1) appendln()
        }
    }.toString()
}
