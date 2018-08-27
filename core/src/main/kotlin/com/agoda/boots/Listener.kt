package com.agoda.boots

class Listener {
    var onBoot: (Report) -> Unit = {}
    var onFailure: (Report) -> Unit = {}

    interface Builder {
        fun onBoot(report: Report)
        fun onFailure(report: Report)
    }

}
