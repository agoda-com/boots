package com.agoda.boots

import com.agoda.boots.Key.Multiple
import com.agoda.boots.Key.Single
import com.agoda.boots.impl.DefaultExecutor
import com.agoda.boots.impl.DefaultNotifier
import com.agoda.boots.impl.DefaultReporter
import com.agoda.boots.impl.DefaultSequencer
import com.agoda.boots.strict.IccFinder
import com.agoda.boots.strict.SccFinder

object Boots {

    var executor: Executor = DefaultExecutor()
    var reporter: Reporter = DefaultReporter()
    var notifier: Notifier = DefaultNotifier()
    var sequencer: Sequencer = DefaultSequencer()
    var logger: Logger? = null

    var isStrictMode = true

    private val boots = mutableListOf<Bootable>()
    private val lock = Any()

    fun add(bootables: Array<Bootable>) {
        synchronized(boots) {
            boots.addAll(bootables)
            sequencer.add(bootables)
            verify()
        }
    }

    fun single(key: Single): Bootable {
        synchronized(boots) {
            return boots.find { it.key == key }
                    ?: throw IllegalArgumentException("There is no bootable for $key key!")
        }
    }

    fun multiple(key: Multiple): List<Bootable> {
        synchronized(boots) {
            val list = mutableListOf<Bootable>()
            key.forEach { list.add(single(it)) }
            return list
        }
    }

    fun critical(): List<Bootable> {
        synchronized(boots) {
            return boots.filter { it.isCritical }
        }
    }

    fun all(): List<Bootable> {
        synchronized(boots) {
            return boots
        }
    }

    fun boot(key: Key, listener: Listener) {
        synchronized(lock) {
            observe(key, listener)
            sequencer.start(key)
            boot(key)
        }
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

    private fun boot(key: Key) {
        synchronized(lock) {
            if (sequencer.count(key) > 0) {

            }
        }
    }

    private fun verify() {
        val iccs = IccFinder(boots).find()

        if (iccs.isNotEmpty()) {
            val exception = IncorrectConnectedBootException(iccs)

            if (isStrictMode) {
                throw exception
            } else {
                logger?.log(Logger.Level.ERROR, "Problems in bootables detected!", exception)
            }
        }

        val sccs = SccFinder(boots).find()

        if (sccs.isNotEmpty()) {
            throw StrongConnectedBootException(sccs)
        }
    }

}



