package com.agoda.boots.impl

import com.agoda.boots.*
import com.agoda.boots.Key.*
import com.agoda.boots.Status.Booted
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

open class DefaultSequencer(val isMainThreadSupported: Boolean = false) : Sequencer {

    protected val boots = mutableSetOf<Bootable>()
    protected val tasks = mutableMapOf<Key, Queue<Key>>()

    override fun add(bootables: Set<Bootable>) {
        synchronized(boots) {
            boots.addAll(bootables)
            verify()
        }
    }

    override fun start(key: Key) {
        synchronized(boots) {
            tasks[key] = when (key) {
                is Single -> resolve(setOf(boots.find { it.key == key }!!))
                is Multiple -> resolve(boots.filter { key.contains(it.key) }.toSet())
                is Critical -> resolve(boots.filter { it.isCritical }.toSet())
                is All -> resolve(boots)
            }
        }
    }

    override fun count(key: Key): Int {
        synchronized(boots) {
            return tasks[key]?.size ?: 0
        }
    }

    override fun next(key: Key, finished: Report?): Bootable? {
        synchronized(boots) {
            finished?.let { update(it) }
            return tasks[key]?.peek()?.let { next -> boots.find { it.key == next } }
        }
    }

    override fun stop(key: Key) {
        synchronized(boots) {
            tasks[key]?.clear()
        }
    }

    private fun resolve(bootables: Set<Bootable>): Queue<Key> {
        TODO("not implemented")
    }

    private fun update(report: Report) {
        if (report.status is Booted) {
            tasks.values.forEach { it.remove(report.key) }
        }
    }

    private fun verify() {
        if (isMainThreadSupported) {
            return
        }

        val results = mutableSetOf<Pair<Key, Key>>()

        boots.filter {
            it.dependencies.isNotEmpty() && !it.isConcurrent
        }.forEach { boot ->
            boot.dependencies.forEach { key ->
                if (boots.find { it.key == key }!!.isConcurrent) {
                    results.add(boot.key to key)
                }
            }
        }

        if (results.isNotEmpty()) {
            throw IncorrectConnectedBootException(results)
        }
    }

}
