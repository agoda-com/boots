package com.agoda.boots.impl

import com.agoda.boots.*
import com.agoda.boots.Status.Booted
import com.agoda.boots.Status.Failed

open class DefaultNotifier : Notifier {

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
        synchronized(listeners) {
            listeners[key]?.let {
                notify(report, it)
            }

            listeners.forEach { k, listeners ->
                when (k) {
                    is Key.Multiple -> if (k.contains(key)) check(k, listeners)
                    is Key.Critical -> if (Boots.single(key).isCritical) check(k, listeners)
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
                notify(it, listeners)
            }
        }
    }

    private fun isNotifiable(report: Report) = report.status is Booted || report.status is Failed

}
