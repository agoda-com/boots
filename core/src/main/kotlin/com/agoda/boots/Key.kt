@file:JvmName("Key")

package com.agoda.boots

sealed class Key {

    class Single(val id: String) : Key() {
        override fun hashCode() = id.hashCode()
        override fun equals(other: Any?) = if (other is Single) id == other.id else false
        override fun toString() = id
    }

    class Multiple(private val keys: Set<Single>) : Key(), Set<Single> {
        override val size = keys.size

        override fun isEmpty() = keys.isEmpty()
        override fun contains(element: Single) = keys.contains(element)
        override fun containsAll(elements: Collection<Single>) = keys.containsAll(elements)
        override fun iterator() = keys.iterator()
        override fun hashCode() = keys.hashCode()
        override fun equals(other: Any?) = if (other is Multiple) keys == other.keys else false

        override fun toString() = StringBuilder().apply {
            append("{")
            keys.forEachIndexed { index, key ->
                if (index > 0) append(" ")
                append(key)
                if (index < size - 1) append(",")
            }
            append("}")
        }.toString()

        fun isNotEmpty() = keys.isNotEmpty()
    }

    class Critical : Key() {
        override fun hashCode() = "CRITICAL".hashCode()
        override fun equals(other: Any?) = other is Critical
        override fun toString() = "CRITICAL"
    }

    class All : Key() {
        override fun hashCode() = "ALL".hashCode()
        override fun equals(other: Any?) = other is All
        override fun toString() = "ALL"
    }

}

fun single(id: String = "") = Key.Single(id)
fun multiple(vararg ids: String = emptyArray()) = Key.Multiple(ids.map { single(it) }.toSet())
fun critical() = Key.Critical()
fun all() = Key.All()
