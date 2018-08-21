package com.agoda.boots.executor

import com.agoda.boots.Executor
import com.agoda.boots.impl.DefaultExecutor
import rx.Completable
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers

class RxAndroidExecutor(
        private val scheduler: Scheduler,
        override val capacity: Int = DefaultExecutor.DEFAULT_CAPACITY
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
