package com.agoda.boots

import com.agoda.boots.Logger.Level.*
import com.agoda.boots.Status.Companion.booted
import com.agoda.boots.Status.Companion.booting
import com.agoda.boots.Status.Companion.failed
import com.agoda.boots.Status.Booted
import com.agoda.boots.Status.Booting
import com.agoda.boots.Status.Failed
import com.agoda.boots.impl.DefaultExecutor
import com.agoda.boots.impl.DefaultNotifier
import com.agoda.boots.impl.DefaultReporter
import com.agoda.boots.impl.DefaultSequencer
import com.agoda.boots.strict.IccFinder
import com.agoda.boots.strict.SccFinder

/**
 * Main controller object. Used to interact with the library.
 *
 * The default scenario of library's usage is as follows:
 * 1. Provide custom components if any via [configure()][configure] function
 * 2. Add bootables to work with via [add()][add] function
 * 3. Request to boot everything or some specific bootables with [boot()][boot] function
 * 4. Observe boot events with [subscripbe()][subscribe]/[unsubscribe()][unsubscribe]
 * 5. Get the current status with [report()][report] function
 *
 * Supports both Kotlin DSL and Java style.
 */
object Boots {

    private var executor: Executor = DefaultExecutor()
    private var reporter: Reporter = DefaultReporter()
    private var notifier: Notifier = DefaultNotifier()
    private var sequencer: Sequencer = DefaultSequencer()
    private var logger: Logger? = null
    private var isStrictMode = true

    private val boots = mutableListOf<Bootable>()

    private var capacity: Int = -1

    init {
        setExecutor()
    }

    /**
     * Adds given bootable to the system's pool and adds them
     * to components ([Reporter], [Notifier], [Sequencer]).
     *
     * Also runs verification check on every invocation trying to find
     * SCC (strong connected components) and ICC (incorrect connected components).
     * @param bootables bootables to add
     */
    @JvmStatic
    fun add(vararg bootables: Bootable) {
        add(bootables.toList())
    }

    /**
     * Adds given bootable to the system's pool and adds them
     * to components ([Reporter], [Notifier], [Sequencer]).
     *
     * Also runs verification check on every invocation trying to find
     * SCC (strong connected components) and ICC (incorrect connected components).
     * @param bootables bootables to add
     */
    @JvmStatic
    fun add(bootables: List<Bootable>) {
        synchronized(boots) {
            val toAdd = bootables.filter { !boots.contains(it) }

            logger?.log(INFO, "Trying to add bootables: $toAdd")

            verify(boots.plus(toAdd))

            boots.addAll(toAdd)

            toAdd.let {
                reporter.add(it)
                notifier.add(it)
                sequencer.add(it)
            }

            logger?.log(INFO, "Bootables added!")
        }
    }

    /**
     * Sets provided configuration and overrides current component
     * implementations with defined in the given configuration object.
     * @param configuration configuration to apply
     */
    @JvmStatic
    fun configure(configuration: Configuration) {
        synchronized(boots) {
            logger?.log(INFO, "Configuration started...")

            with(configuration) {
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

                setLogger()
                setExecutor()
            }

            logger?.log(INFO, "Configuration finished!")
        }
    }


    /**
     * Sets provided configuration and overrides current component
     * implementations with defined in the given configuration object.
     * @param configuration configuration receiver to apply
     */
    fun configure(configuration: Configuration.() -> Unit) {
        configure(Configuration().apply(configuration))
    }

    /**
     * Requests library to boot given bootable/bootables satisfying
     * it's dependencies and critical bootables before.
     *
     * It is library's responsibility to take care of boot order,
     * handling threading, measuring the performance.
     * @param key bootable/bootables identifier
     * @param listener subscribing instance
     */
    @JvmOverloads
    @JvmStatic
    fun boot(key: Key, listener: Listener? = null) {
        synchronized(boots) {
            listener?.let { subscribe(key, it) }

            logger?.log(INFO, "Building task for $key...")
            sequencer.start(key)

            logger?.log(INFO, "Task building for $key is complete!")
            logger?.log(INFO, "Starting boot process for $key...")

            boot(null)
        }
    }

    /**
     * Requests library to boot given bootable/bootables satisfying
     * it's dependencies and critical bootables before.
     *
     * It is library's responsibility to take care of boot order,
     * handling threading, measuring the performance.
     * @param key bootable/bootables identifier
     * @param listener receiving lambda of listener
     * @return created listener instance
     */
    fun boot(key: Key, listener: Listener.() -> Unit) = Listener().apply(listener).also {
        boot(key, it)
    }

    /**
     * Requests library to boot given bootable/bootables satisfying
     * it's dependencies and critical bootables before.
     *
     * It is library's responsibility to take care of boot order,
     * handling threading, measuring the performance.
     * @param key bootable/bootables identifier
     * @param listener builder instance
     * @return created listener instance
     */
    @JvmStatic
    fun boot(key: Key, listener: Listener.Builder) = boot(key) {
        onBoot = { listener.onBoot(it) }
        onFailure = { listener.onFailure(it) }
    }

    /**
     * Adds given listener to event callbacks of a given key.
     * @param key bootable/bootables identifier
     * @param listener subscribing instance
     */
    @JvmStatic
    fun subscribe(key: Key, listener: Listener) {
        logger?.log(INFO, "Listener for $key has been added")
        notifier.add(key, listener)
    }

    /**
     * Adds given listener to event callbacks of a given key.
     * @param key bootable/bootables identifier
     * @param listener receiving lambda of listener
     * @return created listener instance
     */
    fun subscribe(key: Key, listener: Listener.() -> Unit) = Listener().apply(listener).also {
        subscribe(key, it)
    }

    /**
     * Adds given listener to event callbacks of a given key.
     * @param key bootable/bootables identifier
     * @param listener builder instance
     * @return created listener instance
     */
    @JvmStatic
    fun subscribe(key: Key, listener: Listener.Builder) = subscribe(key) {
        onBoot = { listener.onBoot(it) }
        onFailure = { listener.onFailure(it) }
    }

    /**
     * Removes given listener from event callbacks of a given key.
     * @param key bootable/bootables identifier
     * @param listener unsubscribing instance
     */
    @JvmStatic
    fun unsubscribe(key: Key, listener: Listener) {
        logger?.log(INFO, "Listener $listener has been removed")
        notifier.remove(key, listener)
    }

    /**
     * Retrieves the [report][Report] for a given key.
     * @param key bootable/bootables identifier
     * @return report for a given key
     */
    @JvmStatic
    fun report(key: Key): Report {
        logger?.log(INFO, "Report for $key has been requested")
        return reporter.get(key)
    }

    /**
     * Dsl support operator function.
     * @param tail receiver lambda to invoke
     */
    operator fun invoke(tail: Boots.() -> Unit) {
        tail(this)
    }

    /**
     * Clears bootable list and sets all components to default instances.
     * Also sets [isStrictMode][Configuration.isStrictMode] to `true` and
     * sets [logger][Configuration.logger] to `null`.
     *
     * Please use with caution. It is not intended for regular use.
     */
    fun reset() {
        synchronized(boots) {
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
    }

    private fun boot(finished: Report?) {
        synchronized(boots) {
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
                    logger?.log(DEBUG, "Sequencer proposed to boot ${it.key}, checking...")

                    val st = reporter.get(it.key).status

                    if (st is Booting || st is Booted) {
                        return@let
                    }

                    logger?.log(DEBUG, "${it.key} can be executed, passing to executor...")

                    capacity--
                    reporter.set(it.key, booting())

                    executor.execute(it.isConcurrent) {
                        logger?.log(INFO, "Bootable ${it.key} is starting boot process...")

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

                        logger?.let { logger ->
                            if (status is Booted) {
                                logger.log(INFO, "Bootable ${it.key} booted successfully in $time ms!")
                            } else if (status is Failed) {
                                logger.log(if (it.isCritical) ERROR else WARNING,
                                        "Bootable ${it.key} failed to load due to: ${status.reason}")
                            }
                        }

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

        val iccs = IccFinder(bootables, executor.isMainThreadSupported).find()

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

    private fun setExecutor() {
        executor.let {
            reporter.executor = it
            notifier.executor = it
            sequencer.executor = it
        }
    }

    private fun setLogger() {
        logger.let {
            reporter.logger = it
            notifier.logger = it
            sequencer.logger = it
        }
    }

}
