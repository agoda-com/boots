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
    coroutineScope: CoroutineScope,
    dispatcher: CoroutineDispatcher,
    override val capacity: Int = DEFAULT_CAPACITY
) : CoroutineExecutor(coroutineScope, dispatcher, capacity) {

    init {
        coroutineScope.plus(Dispatchers.Main)
    }
}
