package com.agoda.boots.impl

import com.agoda.boots.*
import com.agoda.boots.Logger.Level.*
import com.agoda.boots.Status.Booted
import com.agoda.boots.Status.Failed

/**
 * Default implementation of [Notifier].
 *
 * Implementation is very simple and straightforward. It stores all listeners
 * in a map, and on status change looks for single key match first, then checks
 * all other listeners if they can be invoked and if can, invoke and remove them.
 * All calls are synchronous.
 * @property listeners container of listeners
 */
open class DefaultNotifier : Notifier {

    override val boots: MutableMap<Key, Bootable> = mutableMapOf()
    override var logger: Logger? = null

    override lateinit var executor: Executor

    protected val listeners = mutableMapOf<Key, MutableList<Listener>>()

    override fun add(key: Key, listener: Listener) {
        synchronized(listeners) {
            listeners[key] = (listeners[key] ?: mutableListOf()).also { it.add(listener) }
            check(key, listeners[key]!!)
        }
    }

    override fun notify(key: Key.Single, report: Report) {
        synchronized(listeners) {
            logger?.log(DEBUG, "Got report from ${report.key}, looking for listeners to invoke...")

            listeners[key]?.let {
                logger?.log(DEBUG, "Listeners of ${report.key} are ready to be notified, invoking...")
                notify(report, it)
            }

            for ((k, v) in listeners) {
                when (k) {
                    is Key.Multiple -> if (k.contains(key)) check(k, v)
                    is Key.Excluding -> if (!k.contains(key)) check(k, v)
                    is Key.Critical -> if (boots[key]!!.isCritical) check(k, v)
                    is Key.All -> check(k, v)
                }
            }
        }
    }

    override fun remove(key: Key, listener: Listener) {
        synchronized(listeners) {
            listeners[key]?.remove(listener)
        }
    }

    override fun remove(listener: Listener) {
        synchronized(listeners) {
            for (it in listeners.values) { it.remove(listener) }
        }
    }

    private fun notify(report: Report, listeners: MutableList<Listener>) {
        when (report.status) {
            is Booted -> if (executor.isMainThreadSupported) {
                for (it in listeners) { executor.execute(false) { it.onBoot(report) } }
            } else {
                for (it in listeners) { it.onBoot(report) }
            }
            is Failed -> if (executor.isMainThreadSupported) {
                for (it in listeners) { executor.execute(false) { it.onFailure(report) } }
            } else {
                for (it in listeners) { it.onFailure(report) }
            }
        }

        listeners.clear()
    }

    private fun check(key: Key, listeners: MutableList<Listener>) {
        Boots.report(key).let {
            if (isNotifiable(it)) {
                logger?.log(DEBUG, "Listeners of $it are ready to be notified, invoking...")
                notify(it, listeners)
            }
        }
    }

    private fun isNotifiable(report: Report) = report.status is Booted || report.status is Failed

}
