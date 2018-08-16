package com.agoda.boots

interface Listener {
    fun onBoot(report: Report)
    fun onFailure(report: Report)

    class Builder {
        var onBoot: (Report) -> Unit = {}
        var onFailure: (Report) -> Unit = {}
    }
}
