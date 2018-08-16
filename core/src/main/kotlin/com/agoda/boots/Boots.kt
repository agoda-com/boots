package com.agoda.boots

import com.agoda.boots.Key.Multiple
import com.agoda.boots.Key.Single
import com.agoda.boots.impl.DefaultExecutor
import com.agoda.boots.impl.DefaultNotifier
import com.agoda.boots.impl.DefaultReporter
import com.agoda.boots.impl.DefaultSequencer
import com.agoda.boots.tools.SccFinder

object Boots {

    var executor: Executor = DefaultExecutor()
    var reporter: Reporter = DefaultReporter()
    var notifier: Notifier = DefaultNotifier()
    var sequencer: Sequencer = DefaultSequencer()

    var isStrictMode = false

    private val boots = mutableSetOf<Bootable>()

    fun add(bootables: Set<Bootable>) {
        synchronized(boots) {
            boots.addAll(bootables)
            if (isStrictMode) verify()
        }
    }

    fun single(key: Single): Bootable {
        synchronized(boots) {
            return boots.find { it.key == key }
                    ?: throw IllegalArgumentException("There is no bootable for $key key!")
        }
    }

    fun multiple(key: Multiple): Set<Bootable> {
        synchronized(boots) {
            val set = mutableSetOf<Bootable>()
            key.forEach { set.add(single(it)) }
            return set
        }
    }

    fun critical(): Set<Bootable> {
        synchronized(boots) {
            return boots.filter { it.isCritical }.toSet()
        }
    }

    fun all(): Set<Bootable> {
        synchronized(boots) {
            return boots
        }
    }

    fun boot(key: Key, listener: Listener) {

    }

    fun boot(key: Key, listener: Listener.Builder.() -> Unit) {
        val builder = Listener.Builder().apply(listener)
        boot(key, object : Listener {
            override fun onBoot(report: Report) {
                builder.onBoot(report)
            }

            override fun onFailure(report: Report) {
                builder.onFailure(report)
            }
        })
    }

    fun observe(key: Key, listener: Listener) {
        notifier.add(key, listener)
    }

    fun observe(key: Key, listener: Listener.Builder.() -> Unit) {
        val builder = Listener.Builder().apply(listener)
        observe(key, object : Listener {
            override fun onBoot(report: Report) {
                builder.onBoot(report)
            }

            override fun onFailure(report: Report) {
                builder.onFailure(report)
            }
        })
    }

    fun report(key: Key): Report {
        return reporter.get(key)
    }

    operator fun invoke(tail: Boots.() -> Unit) {
        tail(this)
    }

    private fun verify() {
        val sccs = SccFinder(boots).find()

        if (sccs.isNotEmpty()) {
            throw RuntimeException(
                    "Strong connections in bootable dependencies detected!",
                    StrongConnectedBootException(sccs)
            )
        }
    }

}



