package com.agoda.boots

import com.agoda.boots.Logger.Level.*
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
    private var isStrictMode = true

    internal var logger: Logger? = null
        private set

    internal var isMainThreadSupported = false
        get() = executor.isMainThreadSupported
        private set

    private val boots = mutableListOf<Bootable>()
    private val lock = Any()

    private var capacity: Int = -1

    fun add(bootables: List<Bootable>) {
        synchronized(boots) {
            logger?.log(INFO, "Trying to add bootables: $bootables")

            verify(boots.plus(bootables))

            boots.addAll(bootables)

            reporter.add(bootables)
            notifier.add(bootables)
            sequencer.add(bootables)

            logger?.log(INFO, "Bootables added!")
        }
    }

    fun configure(configuration: Configuration) {
        logger?.log(INFO, "Configuration started...")

        with (configuration) {
            logger?.let {
                logger?.log(DEBUG, "Setting custom logger: $it")
                Boots.logger = it
            }

            executor?.let {
                logger?.log(DEBUG, "Setting custom executor: $it")
                Boots.executor = it
            }

            reporter?.let {
                logger?.log(DEBUG, "Setting custom reporter: $it")
                Boots.reporter = it
            }

            notifier?.let {
                logger?.log(DEBUG, "Setting custom notifier: $it")
                Boots.notifier = it
            }

            sequencer?.let {
                logger?.log(DEBUG, "Setting custom sequencer: $it")
                Boots.sequencer = it
            }

            isStrictMode?.let {
                logger?.log(DEBUG, "Overriding strict mode: $it")
                Boots.isStrictMode = it
            }
        }

        logger?.log(INFO, "Configuration finished!")
    }

    fun configure(configuration: Configuration.() -> Unit) {
        configure(Configuration().apply(configuration))
    }

    fun boot(key: Key, listener: Listener) {
        synchronized(lock) {
            observe(key, listener)
            sequencer.start(key)
            boot(null)
        }
    }

    fun boot(key: Key, listener: Listener.() -> Unit) {
        boot(key, Listener().apply(listener))
    }

    fun observe(key: Key, listener: Listener) {
        logger?.log(INFO, "Listener for $key has been added")
        notifier.add(key, listener)
    }

    fun observe(key: Key, listener: Listener.() -> Unit) {
        observe(key, Listener().apply(listener))
    }

    fun report(key: Key): Report {
        logger?.log(INFO, "Report for $key has been requested")
        return reporter.get(key)
    }

    operator fun invoke(tail: Boots.() -> Unit) {
        tail(this)
    }

    internal fun reset () {
        logger?.log(DEBUG, "reset() has been invoked! This function is for testing purposes only!")

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
        logger?.log(INFO, "Verification started!")
        logger?.log(DEBUG, "Trying to find incorrect connected components...")

        val iccs = IccFinder(bootables).find()

        if (iccs.isNotEmpty()) {
            val exception = IncorrectConnectedBootException(iccs)

            if (isStrictMode) {
                throw exception
            } else {
                logger?.log(WARNING, "Problems in bootables detected!", exception)
            }
        }

        logger?.log(DEBUG, "Trying to find strong connected components...")

        val sccs = SccFinder(bootables).find()

        if (sccs.isNotEmpty()) {
            throw StrongConnectedBootException(sccs)
        }

        logger?.log(INFO, "Verification completed!")
    }

}



