package com.agoda.boots.executor

import com.agoda.boots.Executor
import com.agoda.boots.Executor.Companion.DEFAULT_CAPACITY
import kotlinx.coroutines.*

/**
 * Coroutine Android implementation of [Executor].
 *
 * Can switch to MainThread Dispatcher to invoke listeners
 * and [non-concurrent][com.agoda.boots.Bootable.isConcurrent] bootables.
 */
class CoroutineAndroidExecutor @JvmOverloads constructor(
    private val coroutineScope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher,
    override val capacity: Int = DEFAULT_CAPACITY
) : CoroutineExecutor(coroutineScope, dispatcher, capacity) {

    init {
        coroutineScope.plus(Dispatchers.Main)
    }

    override val isMainThreadSupported = true

    override fun execute(isConcurrent: Boolean, executable: () -> Unit) {
        coroutineScope.launch {
            if (isConcurrent) {
                withContext(dispatcher) {
                    executable.invoke()
                }
            } else {
                executable.invoke()
            }
        }
    }
}
