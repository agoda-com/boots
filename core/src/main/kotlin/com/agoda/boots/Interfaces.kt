package com.agoda.boots

interface Executor {
    val capacity: Int
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
    fun add(bootables: Array<Bootable>)
    fun start(key: Key)
    fun count(): Int
    fun next(finished: Report?): Bootable?
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
