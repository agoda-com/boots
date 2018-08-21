package com.agoda.boots.impl

import com.agoda.boots.*
import com.agoda.boots.Key.*
import com.agoda.boots.Status.*
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

open class DefaultSequencer(val isMainThreadSupported: Boolean = false) : Sequencer {

    protected val boots = mutableListOf<Bootable>()
    protected val map = mutableMapOf<Key, Queue<Key>>()
    protected val tasks = mutableListOf<Queue<Key>>()

    override fun add(bootables: Array<Bootable>) {
        synchronized(boots) {
            boots.addAll(bootables)
            verify()
        }
    }

    override fun start(key: Key) {
        synchronized(boots) {
            map[key] = when (key) {
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

            tasks.add(map[key]!!)
        }
    }

    override fun count(): Int {
        synchronized(boots) {
            return tasks.sumBy { it.size }
        }
    }

    override fun next(finished: Report?): Bootable? {
        synchronized(boots) {
            finished?.let { update(it) }

            for (task in tasks) {
                val bootable = boots.find { it.key == task.peek() }

                if (bootable != null) {
                    if (check(bootable)) {
                        return bootable.also {
                            task.poll()
                            if (task.isEmpty()) tasks.remove(task)
                        }
                    }
                }
            }

            return null
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
            map.values.forEach { it.remove(report.key) }
        } else if (report.status is Failed) {
            val bootable = boots.find { it.key == report.key }!!

            if (bootable.isCritical) {
                map.values.forEach {
                    if (it.contains(report.key)) {
                        tasks.remove(it)
                        it.clear()
                    }
                }
            }
        }
    }

    protected fun check(bootable: Bootable): Boolean {
        val empty = bootable.dependencies.isEmpty()
        val booted = Boots.report(bootable.dependencies).status is Booted

        return empty || booted
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
