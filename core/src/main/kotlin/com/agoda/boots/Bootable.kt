package com.agoda.boots

abstract class Bootable {

    abstract val key: Key.Single

    open val dependencies: Key.Multiple = multiple()

    open val isConcurrent: Boolean = true
    open val isCritical: Boolean = false

    @Throws(Throwable::class)
    abstract fun boot()

    override fun hashCode() = key.hashCode()
    override fun equals(other: Any?) = key == other

}
