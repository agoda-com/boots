package com.agoda.boots

import com.agoda.boots.Status.Companion.booted
import com.agoda.boots.Status.Companion.booting
import com.agoda.boots.Status.Companion.failed
import com.agoda.boots.Status.Booted
import com.agoda.boots.Status.Booting
import com.agoda.boots.impl.DefaultExecutor
import com.agoda.boots.impl.DefaultNotifier
import com.agoda.boots.impl.DefaultReporter
import com.agoda.boots.impl.DefaultSequencer
import com.agoda.boots.strict.IccFinder
import com.agoda.boots.strict.SccFinder

object Boots {

    private var executor: Executor = DefaultExecutor()
    private var reporter: Reporter = DefaultReporter()
    private var notifier: Notifier = DefaultNotifier()
    private var sequencer: Sequencer = DefaultSequencer()
    private var logger: Logger? = null
    private var isStrictMode = true

    internal var isMainThreadSupported = false
        get() = executor.isMainThreadSupported
        private set

    private val boots = mutableListOf<Bootable>()
    private val lock = Any()

    private var capacity: Int = -1

    fun add(bootables: List<Bootable>) {
        synchronized(boots) {
            verify(boots.plus(bootables))

            boots.addAll(bootables)

            reporter.add(bootables)
            notifier.add(bootables)
            sequencer.add(bootables)
        }
    }

    fun boot(key: Key, listener: Listener) {
        synchronized(lock) {
            observe(key, listener)
            sequencer.start(key)
            boot(null)
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

    internal fun reset () {
        executor = DefaultExecutor()
        reporter = DefaultReporter()
        notifier = DefaultNotifier()
        sequencer = DefaultSequencer()
        logger = null
        isStrictMode = true

        boots.clear()
        capacity = -1
    }

    private fun boot(finished: Report?) {
        synchronized(lock) {
            if (capacity == -1) {
                capacity = executor.capacity
            } else {
                finished?.let { capacity++ }
            }

            while (true) {
                if (capacity == 0 || sequencer.count() == 0) {
                    break
                }

                sequencer.next(finished)?.let {
                    val st = reporter.get(it.key).status

                    if (st is Booting || st is Booted) {
                        return@let
                    }

                    capacity--

                    executor.execute(it.isConcurrent) {
                        val start = System.currentTimeMillis()
                        var status = booted()

                        reporter.set(it.key, booting(), start)

                        try {
                            it.boot()
                        } catch (t: Throwable) {
                            status = failed(t)
                        }

                        val time = System.currentTimeMillis() - start
                        val report = reporter.set(it.key, status, start, time)

                        notifier.notify(it.key, report)

                        boot(report)
                    }
                } ?: break
            }
        }
    }

    private fun verify(bootables: List<Bootable>) {
        val iccs = IccFinder(bootables).find()

        if (iccs.isNotEmpty()) {
            val exception = IncorrectConnectedBootException(iccs)

            if (isStrictMode) {
                throw exception
            } else {
                logger?.log(Logger.Level.ERROR, "Problems in bootables detected!", exception)
            }
        }

        val sccs = SccFinder(bootables).find()

        if (sccs.isNotEmpty()) {
            throw StrongConnectedBootException(sccs)
        }
    }

    fun configure(tail: Configurator.() -> Unit) {
        Configurator().apply(tail).configure()
    }

    class Configurator {
        var executor: Executor? = null
        var reporter: Reporter? = null
        var notifier: Notifier? = null
        var sequencer: Sequencer? = null
        var logger: Logger? = null
        var isStrictMode: Boolean? = null

        fun configure() {
            executor?.let { Boots.executor = it }
            reporter?.let { Boots.reporter = it }
            notifier?.let { Boots.notifier = it }
            sequencer?.let { Boots.sequencer = it }
            logger?.let { Boots.logger = it }
            isStrictMode?.let { Boots.isStrictMode = it }
        }
    }

}



