package com.agoda

import android.os.Handler
import android.os.Looper
import com.agoda.boots.Executor
import com.agoda.boots.Executor.Companion.DEFAULT_CAPACITY
import java.util.concurrent.ExecutorService

/**
 * Native implementation of [Executor] with Android support.
 *
 * Can switch to Android's main thread context to invoke listeners
 * and [non-concurrent][com.agoda.boots.Bootable.isConcurrent] bootables.
 */
class NativeAndroidExecutor @JvmOverloads constructor(
    private val executor: ExecutorService,
    override val capacity: Int = DEFAULT_CAPACITY
) : Executor {

    private val handler = Handler(Looper.getMainLooper())
    override val isMainThreadSupported = true

    override fun execute(isConcurrent: Boolean, executable: () -> Unit) {
        if (isConcurrent) {
            executor.submit {
                executable()
            }
        } else {
            handler.post(executable)
        }
    }
}
