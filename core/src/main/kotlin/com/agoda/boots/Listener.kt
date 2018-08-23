package com.agoda.boots

class Listener {
    var onBoot: (Report) -> Unit = {}
    var onFailure: (Report) -> Unit = {}

    class Builder {
        private var onBoot: (Report) -> Unit = {}
        private var onFailure: (Report) -> Unit = {}

        fun onBoot(func: (Report) -> Unit) = this.also {
            onBoot = func
        }

        fun onFailure(func: (Report) -> Unit) = this.also {
            onFailure = func
        }

        fun build() = Listener().apply {
            onBoot = this@Builder.onBoot
            onFailure = this@Builder.onFailure
        }
    }

}
