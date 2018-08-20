package com.agoda.boots

interface Executor {
    fun execute(isConcurrent: Boolean, executable: () -> Unit)
}

interface Reporter {
    fun set(key: Key.Single, status: Status, start: Long = -1L, time: Long = -1L): Report
    fun get(key: Key): Report
}

interface Notifier {
    fun add(key: Key, listener: Listener)
    fun notify(key: Key.Single, report: Report)
}

interface Sequencer {
    fun add(bootables: Set<Bootable>)
    fun start(key: Key)
    fun count(key: Key): Int
    fun next(key: Key, finished: Report?): Bootable?
    fun stop(key: Key)
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
