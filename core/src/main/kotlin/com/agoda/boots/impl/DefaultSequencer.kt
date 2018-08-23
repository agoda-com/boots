package com.agoda.boots.impl

import com.agoda.boots.*
import com.agoda.boots.Key.*
import com.agoda.boots.Logger.Level.*
import com.agoda.boots.Status.*
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

open class DefaultSequencer : Sequencer {

    override val boots: MutableMap<Key, Bootable> = mutableMapOf()
    override var logger: Logger? = null

    protected val map = mutableMapOf<Key, Queue<Key>>()
    protected val tasks = mutableListOf<Queue<Key>>()

    override fun add(bootables: List<Bootable>) {
        verify(boots.values.plus(bootables))
        super.add(bootables)
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

            logger?.let {
                val queue = map[key]!!
                val sb = StringBuilder()

                queue.forEachIndexed { i, key ->
                    sb.append(key)
                    if (i < queue.size - 1) sb.append(" -> ")
                }

                it.log(DEBUG, "Resolved: $sb")
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
            logger?.log(DEBUG, "Picking next bootable...")

            finished?.let { update(it) }

            for (task in tasks) {
                val bootable = boots[task.peek()]

                if (bootable != null) {
                    logger?.log(DEBUG, "Checking ${bootable.key}...")

                    if (check(bootable)) {
                        return bootable.also {
                            logger?.log(DEBUG, "Check of ${it.key} has passed!")
                            task.poll()
                            if (task.isEmpty()) tasks.remove(task)
                        }
                    } else {
                        logger?.log(DEBUG, "Check hasn't passed, moving next...")
                    }
                }
            }

            logger?.log(DEBUG, "Nothing found!")
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

    private fun verify(bootables: List<Bootable>) {
        if (Boots.isMainThreadSupported) {
            return
        }

        logger?.log(INFO, "Verifying that non-concurrent bootables are not dependent on concurrent ones")

        val results = mutableListOf<Pair<Key, Key>>()

        bootables.filter {
            it.dependencies.isNotEmpty() && !it.isConcurrent
        }.forEach { boot ->
            boot.dependencies.forEach { key ->
                if (bootables.find { it.key == key }!!.isConcurrent) {
                    results.add(boot.key to key)
                }
            }
        }

        if (results.isNotEmpty()) {
            throw IncorrectConnectedBootException(results)
        }

        logger?.log(INFO, "Verification complete!")
    }

}
