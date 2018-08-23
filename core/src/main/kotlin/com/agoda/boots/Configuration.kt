package com.agoda.boots

class Configuration {

    var executor: Executor? = null
    var reporter: Reporter? = null
    var notifier: Notifier? = null
    var sequencer: Sequencer? = null
    var logger: Logger? = null
    var isStrictMode: Boolean? = null

    class Builder {
        private var executor: Executor? = null
        private var reporter: Reporter? = null
        private var notifier: Notifier? = null
        private var sequencer: Sequencer? = null
        private var logger: Logger? = null
        private var isStrictMode: Boolean? = null

        fun setExecutor(executor: Executor) = this.also {
            this.executor = executor
        }

        fun setReporter(reporter: Reporter) = this.also {
            this.reporter = reporter
        }

        fun setNotifier(notifier: Notifier) = this.also {
            this.notifier = notifier
        }

        fun setSequencer(sequencer: Sequencer) = this.also {
            this.sequencer = sequencer
        }

        fun setLogger(logger: Logger) = this.also {
            this.logger = logger
        }

        fun setStrictMode(isStrictMode: Boolean) = this.also {
            this.isStrictMode = isStrictMode
        }

        fun build() = Configuration().apply {
            executor = this@Builder.executor
            reporter = this@Builder.reporter
            notifier = this@Builder.notifier
            sequencer = this@Builder.sequencer
            logger = this@Builder.logger
            isStrictMode = this@Builder.isStrictMode
        }
    }

}
