package com.agoda.boots.impl

import com.agoda.boots.*
import com.agoda.boots.Key.*
import com.agoda.boots.Status.Booted
import com.agoda.boots.Status.Booting
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

open class DefaultSequencer(val isMainThreadSupported: Boolean = false) : Sequencer {

    protected val boots = mutableListOf<Bootable>()
    protected val tasks = mutableMapOf<Key, Queue<Key>>()

    override fun add(bootables: Array<Bootable>) {
        synchronized(boots) {
            boots.addAll(bootables)
            verify()
        }
    }

    override fun start(key: Key) {
        synchronized(boots) {
            tasks[key] = when (key) {
                is Single -> {
                    LinkedBlockingQueue<Key>(boots.size).apply {
                        addAll(resolve(boots.filter { it.isCritical }))
                        addAll(resolve(listOf(boots.find { it.key == key }!!)))
                    }
                }
                is Multiple -> {
                    LinkedBlockingQueue<Key>(boots.size).apply {
                        addAll(resolve(boots.filter { it.isCritical }))
                        addAll(resolve(boots.filter { key.contains(it.key) }))
                    }
                }
                is All -> {
                    LinkedBlockingQueue<Key>(boots.size).apply {
                        addAll(resolve(boots.filter { it.isCritical }))
                        addAll(resolve(boots))
                    }
                }
                is Critical -> resolve(boots.filter { it.isCritical })
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

            val bootable = boots.find { it.key == tasks[key]?.peek() }

            return bootable?.let {
                if (check(it)) {
                    bootable.also { tasks[key]?.poll() }
                } else {
                    null
                }
            }
        }
    }

    override fun stop(key: Key) {
        synchronized(boots) {
            tasks[key]?.clear()
        }
    }

    protected fun resolve(bootables: List<Bootable>): Queue<Key> {
        val queue = LinkedBlockingQueue<Key>(boots.size)
        val visited = mutableMapOf<Key, Boolean>()

        bootables.forEach { if (visited[it.key] == false) visit(it.key, visited, queue) }

        return queue
    }

    protected fun visit(key: Key, visited: MutableMap<Key, Boolean>, queue: Queue<Key>) {
        visited[key] = true

        val boot = boots.find { it.key == key }!!
        val status = Boots.report(key).status

        if (status !is Booted) {
            if (boot.dependencies.isEmpty()) {
                queue.add(key)
            } else {
                boot.dependencies.forEach { if (visited[key] == false) visit(it, visited, queue) }
            }
        }
    }

    protected fun update(report: Report) {
        if (report.status is Booted) {
            tasks.values.forEach { it.remove(report.key) }
        }
    }

    protected fun check(bootable: Bootable): Boolean {
        val empty = bootable.dependencies.isEmpty()

        return if (!empty) {
            val report = Boots.report(bootable.dependencies)
            val booted = report.status is Booted

            if (!booted) {
                if (report.status is Status.Failed) {
                    var proceed = true

                    for (r in report.dependent) {
                        if (r.status is Status.Failed) {
                            if (boots.find { boot -> boot.key == r.key }!!.isCritical) {
                                proceed = false
                                break
                            }
                        }
                    }

                    proceed
                } else {
                    false
                }
            } else {
                true
            }
        } else {
            true
        }
    }

    private fun verify() {
        if (isMainThreadSupported) {
            return
        }

        val results = mutableListOf<Pair<Key, Key>>()

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
