package com.agoda.boots.sample

import com.agoda.boots.Executor
import com.agoda.boots.executor.AndroidExecutor
import com.agoda.boots.executor.CoroutineAndroidExecutor
import com.agoda.boots.executor.RxAndroidExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import rx.schedulers.Schedulers

enum class ExecutorEnum(val executor: Executor) {
    NATIVE(AndroidExecutor()),
    RX(RxAndroidExecutor(Schedulers.io())),
    @ExperimentalCoroutinesApi
    COROUTINE(CoroutineAndroidExecutor(MainScope(), Dispatchers.IO));
}