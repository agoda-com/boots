package com.agoda.boots

sealed class Status {

    class Idle : Status()
    class Booting : Status()
    class Booted : Status()
    class Failed(val reason: Throwable) : Status()

    companion object {
        @JvmStatic fun idle(): Status = Status.Idle()
        @JvmStatic fun booting(): Status = Status.Booting()
        @JvmStatic fun booted(): Status = Status.Booted()
        @JvmStatic fun failed(throwable: Throwable): Status = Status.Failed(throwable)
    }

}
