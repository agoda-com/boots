package com.agoda.boots.impl

import com.agoda.boots.*
import com.agoda.boots.Key.*
import com.agoda.boots.Status.Companion.booted
import com.agoda.boots.Status.Companion.booting
import com.agoda.boots.Status.Companion.failed
import com.agoda.boots.Status.Companion.idle
import com.agoda.boots.Status.Failed

open class DefaultReporter : Reporter {

    protected val reports = mutableMapOf<Key.Single, Report>()

    override fun set(key: Key.Single, status: Status, start: Long, time: Long): Report {
        synchronized(reports) {
            val report = if (!reports.contains(key)) {
                Report(key, status, start, time)
            } else {
                reports[key]!!.copy(key = key, status = status, start = start, time = time)
            }

            val bootable = Boots.single(key)

            bootable.dependencies.forEach {
                reports[it]!!.run {
                    var found = false

                    for (dep in dependent) {
                        if (dep.key == key) {
                            found = true
                            break
                        }
                    }

                    if (!found) {
                        reports[it] = reports[it]!!.copy(dependent = dependent.plus(report))
                    }
                }
            }

            return report.also { reports[key] = it }
        }
    }

    override fun get(key: Key): Report {
        fun process(key: Key, boots: Set<Bootable>): Report {
            val all = boots.map { get(it.key) }
            val start = all.filter { it.status !is Status.Idle }.minBy { it.start }?.start ?: -1
            val time = all.filter { it.status !is Status.Idle }.maxBy { it.start + it.time }?.let { it.start + it.time - start } ?: -1
            val status = when {
                all.any { it.status is Failed } -> failed(BootException(all.filter { it.status is Failed }.map { it.key to (it.status as Failed).reason }.toMap()))
                all.all { it.status is Status.Booted } -> booted()
                all.all { it.status is Status.Idle } -> idle()
                else -> booting()
            }

            val dependent = if (key is All) {
                boots.filter { it.dependencies.isEmpty() }.map { get(it.key) }.toSet()
            } else {
                all.toSet()
            }

            return Report(key, status, start, time, dependent)
        }

        synchronized(reports) {
            return reports[key]?.copy() ?: run {
                when (key) {
                    is Single -> Report(key, idle())
                    is Multiple -> process(key, Boots.multiple(key))
                    is Critical -> process(key, Boots.critical())
                    is All -> process(key, Boots.all())
                }
            }
        }
    }

}
