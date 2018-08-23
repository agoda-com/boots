package com.agoda.boots

interface Executor {
    val capacity: Int
    val isMainThreadSupported: Boolean
    fun execute(isConcurrent: Boolean, executable: () -> Unit)
}

interface Reporter : Holder {
    fun set(key: Key.Single, status: Status, start: Long = -1L, time: Long = -1L): Report
    fun get(key: Key): Report
}

interface Notifier : Holder {
    fun add(key: Key, listener: Listener)
    fun notify(key: Key.Single, report: Report)
}

interface Sequencer : Holder {
    fun start(key: Key)
    fun count(): Int
    fun next(finished: Report?): Bootable?
}

interface Holder {
    val boots: MutableMap<Key, Bootable>
    var logger: Logger?

    fun add(bootables: List<Bootable>) {
        synchronized(boots) {
            boots.putAll(bootables.map { it.key to it }.toMap())
        }
    }

    fun multiple(key: Key.Multiple) = boots.values.filter { key.contains(it.key) }
    fun critical() = boots.values.filter { it.isCritical }
    fun all() = boots.values.toList()

}

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
