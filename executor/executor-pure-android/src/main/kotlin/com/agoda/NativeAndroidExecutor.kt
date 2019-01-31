package com.agoda

import android.os.Handler
import android.os.Looper
import com.agoda.boots.Executor
import com.agoda.boots.Executor.Companion.DEFAULT_CAPACITY
import com.agoda.boots.impl.DefaultExecutor

/**
 * Native implementation of [Executor] with Android support.
 *
 * Can switch to Android's main thread context to invoke listeners
 * and [non-concurrent][com.agoda.boots.Bootable.isConcurrent] bootables.
 */
class AndroidExecutor @JvmOverloads constructor(
    override val capacity: Int = DEFAULT_CAPACITY
) : DefaultExecutor() {

    private val handler = Handler(Looper.getMainLooper())
    override val isMainThreadSupported = true

    override fun execute(isConcurrent: Boolean, executable: () -> Unit) {
        if (isConcurrent) {
            pool.submit(executable)
        } else {
            handler.post(executable)
        }
    }
}
