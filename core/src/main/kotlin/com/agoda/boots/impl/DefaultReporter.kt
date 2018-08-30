package com.agoda.boots.impl

import com.agoda.boots.*
import com.agoda.boots.Key.*
import com.agoda.boots.Logger.Level.*
import com.agoda.boots.Status.Companion.booted
import com.agoda.boots.Status.Companion.booting
import com.agoda.boots.Status.Companion.failed
import com.agoda.boots.Status.Companion.idle
import com.agoda.boots.Status.Failed

/**
 * Default implementation of [Reporter]
 *
 * Implementation is very simple and straightforward. It stores all reports
 * for [single][Key.Single] bootables in a map and inserts/updates (by copy)
 * on every [set()][Reporter.set] invocation.
 *
 * When getting the report, it looks up the map if the requested key is [single][Key.Single],
 * otherwise it generates new combined report on every request.
 * @property reports container of [single][Key.Single] reports.
 */
open class DefaultReporter : Reporter {

    override val boots: MutableMap<Key, Bootable> = mutableMapOf()
    override var logger: Logger? = null

    override lateinit var executor: Executor

    protected val reports = mutableMapOf<Key.Single, Report>()

    override fun set(key: Key.Single, status: Status, start: Long, time: Long): Report {
        synchronized(reports) {
            logger?.log(DEBUG, "Report update for $key. Status: $status, start time: $start, time: $time")

            val report = if (!reports.contains(key)) {
                Report(key, status, start, time)
            } else {
                reports[key]!!.copy(key = key, status = status, start = start, time = time)
            }

            val bootable = boots[key]!!

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
        logger?.log(DEBUG, "Generating report for $key...")

        fun process(key: Key, boots: List<Bootable>): Report {
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
                boots.filter { it.dependencies.isEmpty() }.map { get(it.key) }
            } else {
                all
            }

            return Report(key, status, start, time, dependent)
        }

        synchronized(reports) {
            return reports[key]?.copy() ?: run {
                when (key) {
                    is Single -> Report(key, idle())
                    is Multiple -> process(key, multiple(key))
                    is Critical -> process(key, critical())
                    is All -> process(key, all())
                }
            }
        }
    }

}
