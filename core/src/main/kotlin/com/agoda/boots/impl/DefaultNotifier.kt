package com.agoda.boots.impl

import com.agoda.boots.*
import com.agoda.boots.Logger.Level.*
import com.agoda.boots.Status.Booted
import com.agoda.boots.Status.Failed

open class DefaultNotifier : Notifier {

    override val boots: MutableMap<Key, Bootable> = mutableMapOf()
    override var logger: Logger? = null

    protected val listeners = mutableMapOf<Key, MutableList<Listener>>()

    override fun add(key: Key, listener: Listener) {
        synchronized(listeners) {
            if (!listeners.contains(key)) {
                listeners[key] = mutableListOf()
            }

            listeners[key]!!.add(listener)
        }
    }

    override fun notify(key: Key.Single, report: Report) {
        logger?.log(DEBUG, "Got report from ${report.key}, looking for listeners to invoke...")

        synchronized(listeners) {
            listeners[key]?.let {
                logger?.log(DEBUG, "Listeners of ${report.key} are ready to be notified, invoking...")
                notify(report, it)
            }

            listeners.forEach { k, listeners ->
                when (k) {
                    is Key.Multiple -> if (k.contains(key)) check(k, listeners)
                    is Key.Critical -> if (boots[key]!!.isCritical) check(k, listeners)
                    is Key.All -> check(k, listeners)
                }
            }
        }
    }

    private fun notify(report: Report, listeners: MutableList<Listener>) {
        when (report.status) {
            is Booted -> listeners.forEach { it.onBoot(report) }
            is Failed -> listeners.forEach { it.onFailure(report) }
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
