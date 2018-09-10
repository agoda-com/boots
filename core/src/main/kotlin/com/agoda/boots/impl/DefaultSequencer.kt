package com.agoda.boots.impl

import com.agoda.boots.*
import com.agoda.boots.Key.*
import com.agoda.boots.Logger.Level.*
import com.agoda.boots.Status.*

/**
 * Default implementation of [Sequencer].
 *
 * Implementation is very simple and straightforward. It calculates list of
 * bootables in a dependency-sorted order for every [start()][Sequencer.start]
 * request as a separate task. By default it adds all non-booted [critical][Bootable.isCritical]
 * bootables in front of any other to ensure that critical bootables are booted ASAP.
 *
 * On every [next()] invocation it goes through tasks lists and for every task
 * it tries to iterate through all of it's bootables to check if any of it is
 * qualified to be invoked. Boot qualification requirements can be found at [check()][check] function.
 *
 * Also it will verify all added bootables
 * @property map container of boot tasks
 * @property tasks container of boot tasks in FIFO ordering
 */
open class DefaultSequencer : Sequencer {

    override val boots: MutableMap<Key, Bootable> = mutableMapOf()
    override var logger: Logger? = null

    override lateinit var executor: Executor

    protected val map = mutableMapOf<Key, MutableList<Key>>()
    protected val tasks = mutableListOf<MutableList<Key>>()

    override fun start(key: Key) {
        synchronized(boots) {
            map[key] = when (key) {
                is Single -> mutableListOf<Key>().apply {
                    addAll(resolve(critical()))
                    addAll(resolve(listOf(boots[key]!!)))
                }
                is Multiple -> mutableListOf<Key>().apply {
                    addAll(resolve(critical()))
                    addAll(resolve(multiple(key)))
                }
                is Excluding -> mutableListOf<Key>().apply {
                    addAll(resolve(critical()))
                    addAll(resolve(excluding(key)))
                }
                is All -> mutableListOf<Key>().apply {
                    addAll(resolve(critical()))
                    addAll(resolve(all()))
                }
                is Critical -> resolve(critical())
            }

            logger?.let {
                val list = map[key]!!
                val sb = StringBuilder()

                list.forEachIndexed { i, key ->
                    sb.append(key)
                    if (i < list.size - 1) sb.append(" -> ")
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
                for (key in task) {
                    val bootable = boots[key]

                    if (bootable != null) {
                        logger?.log(DEBUG, "Checking ${bootable.key}...")

                        if (check(bootable)) {
                            return bootable.also {
                                logger?.log(DEBUG, "Check of ${it.key} has passed!")
                                task.remove(key)
                                if (task.isEmpty()) tasks.remove(task)
                            }
                        } else {
                            logger?.log(DEBUG, "Check hasn't passed, moving next...")
                        }
                    }
                }
            }

            logger?.log(DEBUG, "Nothing found!")
            return null
        }
    }

    /**
     * Builds the boot task based on the list of bootables that has to be booted.
     * Function resolves and adds required dependencies to the provided list.
     * @param bootables list of bootables that are required to boot
     * @return list of bootables that will satisfy the requirement
     */
    protected fun resolve(bootables: List<Bootable>): MutableList<Key> {
        val list = mutableListOf<Key>()
        val visited = mutableMapOf<Key, Boolean>()

        boots.forEach { visited[it.key] = false }
        bootables.forEach { if (visited[it.key] == false) visit(it.key, visited, list) }

        return list.toMutableList()
    }

    /**
     * Checks the bootable dependencies in a recursive manner and adds them before
     * adding bootable itself to the task.
     * @param key identifier of bootable
     * @param visited map with already checked flags
     * @param list task where keys will be added
     */
    protected fun visit(key: Key, visited: MutableMap<Key, Boolean>, list: MutableList<Key>) {
        visited[key] = true

        val boot = boots[key]!!
        val status = Boots.report(key).status

        if (status !is Booted) {
            if (boot.dependencies.isEmpty()) {
                if (!list.contains(key)) list.add(key)
            } else {
                boot.dependencies.forEach { if (visited[it] == false) visit(it, visited, list) }
                if (!list.contains(key)) list.add(key)
            }
        }
    }

    /**
     * Updates the tasks based on the information of incoming report.
     * If report says that some bootable is booted, that bootable is removed
     * from all tasks.
     * If report says that some bootable is failed and if that bootable is [critical][Bootable.isCritical]
     * then all tasks containing this bootable will be cleared and cancelled (all tasks as of now).
     * @param report updated report of [single][Key.Single] bootable
     */
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

    /**
     * To be qualified for boot, bootable should meet following requirements:
     * - if bootable is [critical][Bootable.isCritical]:
     *   it should not have dependencies or it's dependencies should have already [booted][Status.Booted]
     * - if bootable is not [critical][Bootable.isCritical]:
     *   critical bootables should be loaded `AND` it should not have dependencies or it's
     *   dependencies should have already [booted][Status.Booted]
     * @return `true` if bootable is qualified to be booted, `false` otherwise
     */
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

}
