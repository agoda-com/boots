package com.agoda.boots.sample

import com.agoda.boots.Executor
import com.agoda.boots.executor.AndroidExecutor
import com.agoda.boots.executor.RxAndroidExecutor
import rx.schedulers.Schedulers

enum class ExecutorEnum(val executor: Executor) {
    NATIVE(AndroidExecutor()),
    RX(RxAndroidExecutor(Schedulers.io()));
}