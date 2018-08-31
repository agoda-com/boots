package com.agoda.boots.executor

import com.agoda.boots.Executor
import com.agoda.boots.Executor.Companion.DEFAULT_CAPACITY
import rx.Completable
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers

/**
 * Rx implementation of [Executor] with Android support.
 *
 * Can switch to Android's main thread context to invoke listeners
 * and [non-concurrent][com.agoda.boots.Bootable.isConcurrent] bootables.
 */
class RxAndroidExecutor @JvmOverloads constructor(
        private val scheduler: Scheduler,
        override val capacity: Int = DEFAULT_CAPACITY
) : Executor {

    override val isMainThreadSupported = true

    override fun execute(isConcurrent: Boolean, executable: () -> Unit) {
        val completable = Completable.create {
            executable()
            it.onCompleted()
        }

        val scheduler = if (isConcurrent) {
            this.scheduler
        } else {
            AndroidSchedulers.mainThread()
        }

        completable
                .subscribeOn(scheduler)
                .observeOn(scheduler)
                .subscribe()
    }

}
