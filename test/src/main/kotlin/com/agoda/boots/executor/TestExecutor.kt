package com.agoda.boots.executor

import com.agoda.boots.Executor

/**
 * Implementation of [Executor] inteded for testing purposes.
 *
 * It executes all [bootables][com.agoda.boots.Bootable] in a blocking manner.
 */
class TestExecutor : Executor {

    override val capacity = Int.MAX_VALUE
    override val isMainThreadSupported = true

    override fun execute(isConcurrent: Boolean, executable: () -> Unit) {
        executable()
    }

}
