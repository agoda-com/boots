package com.agoda.boots.impl

import com.agoda.boots.*
import com.agoda.boots.Key.*
import com.agoda.boots.Status.*
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

open class DefaultSequencer : Sequencer {

    override val boots = mutableMapOf<Key, Bootable>()
    protected val map = mutableMapOf<Key, Queue<Key>>()
    protected val tasks = mutableListOf<Queue<Key>>()

    override fun add(bootables: List<Bootable>) {
        super.add(bootables)
        verify()
    }

    override fun start(key: Key) {
        synchronized(boots) {
            map[key] = when (key) {
                is Single -> {
                    LinkedBlockingQueue<Key>(boots.size).apply {
                        addAll(resolve(critical()))
                        addAll(resolve(listOf(boots[key]!!)))
                    }
                }
                is Multiple -> {
                    LinkedBlockingQueue<Key>(boots.size).apply {
                        addAll(resolve(critical()))
                        addAll(resolve(multiple(key)))
                    }
                }
                is All -> {
                    LinkedBlockingQueue<Key>(boots.size).apply {
                        addAll(resolve(critical()))
                        addAll(resolve(all()))
                    }
                }
                is Critical -> resolve(critical())
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
                val bootable = boots[task.peek()]

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

        boots.forEach { visited[it.key] = false }
        bootables.forEach { if (visited[it.key] == false) visit(it.key, visited, queue) }

        return queue
    }

    protected fun visit(key: Key, visited: MutableMap<Key, Boolean>, queue: Queue<Key>) {
        visited[key] = true

        val boot = boots[key]!!
        val status = Boots.report(key).status

        if (status !is Booted) {
            if (boot.dependencies.isEmpty()) {
                queue.add(key)
            } else {
                boot.dependencies.forEach { if (visited[it] == false) visit(it, visited, queue) }
                queue.add(key)
            }
        }
    }

    protected fun update(report: Report) {
        if (report.status is Booted) {
            map.values.forEach { it.remove(report.key) }
        } else if (report.status is Failed) {
            val bootable = boots[report.key]!!

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
        val critical = bootable.isCritical
        val empty = bootable.dependencies.isEmpty()
        val booted = Boots.report(bootable.dependencies).status is Booted

        return if (critical) {
            empty or booted
        } else {
            val cb = Boots.report(Key.critical()).status is Booted
            cb and (empty or booted)
        }
    }

    private fun verify() {
        if (Boots.executor.isMainThreadSupported) {
            return
        }

        val results = mutableListOf<Pair<Key, Key>>()

        boots.filterValues {
            it.dependencies.isNotEmpty() && !it.isConcurrent
        }.forEach {
            it.value.dependencies.forEach { key ->
                if (boots[key]!!.isConcurrent) {
                    results.add(it.value.key to key)
                }
            }
        }

        if (results.isNotEmpty()) {
            throw IncorrectConnectedBootException(results)
        }
    }

}
