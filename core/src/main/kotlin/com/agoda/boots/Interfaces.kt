package com.agoda.boots

/**
 * Executor is responsible for schedule, executions and switching thread context
 * (not necessary) for invocation of [boot()][Bootable.boot] function.
 * @property capacity maximum concurrent threads capacity
 * @property isMainThreadSupported true if executor can execute given function on main thread (switch context)
 */
interface Executor {
    val capacity: Int
    val isMainThreadSupported: Boolean

    /**
     * Schedules and/or executes given executable on a main/separate thread.
     * @param isConcurrent true if executable needs to be invoked on separate thread
     * @param executable function to invoke
     */
    fun execute(isConcurrent: Boolean, executable: () -> Unit)

    companion object {
        const val DEFAULT_CAPACITY = 4
    }
}

/**
 * Reported is responsible for storing, retrieving and generating [reports][Report].
 *
 * For example, if report's key is [All][Key.All], then report represent
 * shared status of all bootables and time it took to boot all of them.
 * [dependent][Report.dependent] property in that case will contain reports for every single
 * [bootable][Bootable] without dependencies, which in return a list of reports on [bootables][Bootable]
 * that were declaring dependency on it.
 *
 * Default rules for the calculation of common status are as follows:
 * - if there is any [bootable][Bootable] in the report failed to boot, status will be [failed][Status.Failed]
 * - if all of [bootables][Bootable] in the report succeeded to boot, status will be [booted][Status.Booted]
 * - if all of [bootables][Bootable] in the report are idle, status will be [idle][Status.Idle]
 * - if none of the above conditions are satisfied, status will be [booting][Status.Booting]
 */
interface Reporter : Holder {
    /**
     * Saves the report data from a controller [object][Boots].
     * @param key identifier of the bootable
     * @param status current status of the bootable
     * @param start boot start time, -1 if [idle][Status.Idle]
     * @param time boot time, -1 if not [booted][Status.Booted] or [failed][Status.Failed]
     * @return updated [Report] instance
     */
    fun set(key: Key.Single, status: Status, start: Long = -1L, time: Long = -1L): Report

    /**
     * Retrieves the [report][Report] for a given key. In case where [key] is not [single][Key.Single],
     * reporter generates combined report based on the type of the [key].
     * @param key identifier of the requested report
     * @return retrieved/generated [Report] instance
     */
    fun get(key: Key): Report
}

/**
 * Notifier is responsible for managing listeners, invoking them at appropriate
 * moments and switch context (if possible).
 *
 * By default, notifier is holding strong references to the provided listeners, so
 * keep in mind that you need to manually remove them is your outer class instance (if any)
 * is going to be garbage collected.
 */
interface Notifier : Holder {
    /**
     * Enables given listener to receive event callbacks for given key.
     * @param key identifier of bootable/bootables to listen for
     * @param listener listener to be invoked on such events
     */
    fun add(key: Key, listener: Listener)

    /**
     * Informs the notifier about bootable state change in the boot system
     * so that it can check if there is any listener that it need to call back
     * and invoke any found ones.
     *
     * By default, any listener that has been invoked is being removed.
     * If you have same listener added for different keys, it will be completely
     * removed from notifier only when all of the key callbacks has been satisfied.
     * @param key identifier of bootable that has changed state
     * @param report most recent report for given key
     */
    fun notify(key: Key.Single, report: Report)

    /**
     * Disables given listener to receive event callbacks for given key.
     * @param key identifier of bootable/bootables to stop listen for
     * @param listener listener to not be invoked on such events
     */
    fun remove(key: Key, listener: Listener)
}

/**
 * Sequencer is responsible for deciding the order of bootable invocation based on the
 * user's request.
 *
 * By default, sequencer for every boot request creates a task with [critical][Bootable.isCritical] bootables
 * put in all cases. Then sequencer provide bootables that are ready to be booted on requests from controller
 * [object][Boots] in the order decided by sequencer.
 */
interface Sequencer : Holder {
    /**
     * Request to prepare and calculate boot sequence (task) for the given key.
     * @param key identifier of required bootable/bootables
     */
    fun start(key: Key)

    /**
     * Amount of bootables, that are awaiting to be invoked by the system.
     * @return non-booted bootables count
     */
    fun count(): Int

    /**
     * Request to retrieve bootable which should be invoked next.
     * It is sequencer's responsibility to prioritize between tasks and
     * verify that all dependencies for chosen bootable has been resolved.
     * @return instance of bootable to be invoked or `null` if there is no bootable
     *         left or if there is no bootables ready to boot.
     */
    fun next(finished: Report?): Bootable?
}

/**
 * Holder interface is used to propagate all components with bootable instances
 * provided to the system as well as providing access to [Executor] and [Logger]
 * without exposing them through controller [object][Boots] and make components
 * invoke it in cycle manner.
 *
 * Controller [object][Boots] takes care of providing values to the properties of
 * the holders.
 * @property boots map of bootables added to the system
 * @property executor instance of [Executor] used by the system
 * @property logger instance of [Logger] used by the system
 */
interface Holder {
    val boots: MutableMap<Key, Bootable>
    var executor: Executor
    var logger: Logger?

    /**
     * Adds bootables to the storage and maps it with corresponding keys for
     * easier access.
     * @param bootables list of bootables to add
     */
    fun add(bootables: List<Bootable>) {
        synchronized(boots) {
            boots.putAll(bootables.map { it.key to it }.toMap())
        }
    }

    /**
     * Retrieves instances of bootables that are included in the given multiple key.
     * @param key identifier of bootable list
     * @return list of bootables corresponding to the given key
     */
    fun multiple(key: Key.Multiple) = boots.values.filter { key.contains(it.key) }

    /**
     * Retrieves instances of bootables that have thier [isCritical][Bootable.isCritical] flag
     * set to `true`.
     * @return list of critical bootables
     */
    fun critical() = boots.values.filter { it.isCritical }

    /**
     * Retrieves all available bootables to the system.
     * @return list of all bootables
     */
    fun all() = boots.values.toList()
}

/**
 * Simple logger interface.
 * There is no default implementation provided with the core module.
 */
interface Logger {
    fun log(level: Level, message: String)
    fun log(level: Level, message: String, throwable: Throwable)

    enum class Level {
        VERBOSE,
        DEBUG,
        INFO,
        WARNING,
        ERROR
    }
}
