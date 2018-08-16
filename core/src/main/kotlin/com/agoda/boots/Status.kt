@file:JvmName("Status")

package com.agoda.boots

sealed class Status {

    class Idle : Status()
    class Booting : Status()
    class Booted : Status()
    class Failed(val reason: Throwable) : Status()

}

fun idle(): Status = Status.Idle()
fun booting(): Status = Status.Booting()
fun booted(): Status = Status.Booted()
fun failed(throwable: Throwable): Status = Status.Failed(throwable)

