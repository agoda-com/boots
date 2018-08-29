package com.agoda.boots

/**
 * Callback class to listen results of a boot process.
 * @property onBoot function that is invoked when boot process has finished successfully
 * @property onFailure function that is invoked when boot process hasn't finished successfully
 */
class Listener {
    var onBoot: (Report) -> Unit = {}
    var onFailure: (Report) -> Unit = {}

    /**
     * Interface providing Java style means to easily instantiate [listener][Listener] via
     * anonymous inner classes.
     */
    interface Builder {
        /**
         * This function is invoked when boot process has finished successfully.
         */
        fun onBoot(report: Report)

        /**
         * This function is invoked when boot process hasn't finished successfully.
         */
        fun onFailure(report: Report)
    }

}
