package com.agoda.boots.impl

import com.agoda.boots.Executor
import java.util.concurrent.*

/**
 * Default implementation of [Executor] for JVM.
 *
 * Implementation uses cached [ThreadPoolExecutor] under the hood and is not able
 * to forward non-concurrent bootables to the main thread, executing them on the thread
 * that has invoked it.
 *
 * Thus, it is not possible to build boot chain where non-concurrent bootables depends on
 * execution of concurrent ones.
 *
 * @see DefaultSequencer
 */
open class DefaultExecutor(override val capacity: Int = DEFAULT_CAPACITY) : Executor {

    override val isMainThreadSupported = false

    private val pool = Executors.newCachedThreadPool()

    override fun execute(isConcurrent: Boolean, executable: () -> Unit) {
        if (isConcurrent) {
            pool.execute(executable)
        } else {
            executable()
        }
    }

    companion object {
        const val DEFAULT_CAPACITY = 4
    }

}
