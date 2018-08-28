package com.agoda.boots

/**
 * Callback class to listen results of a boot process.
 */
class Listener {
    /**
     * This function is invoked when boot process has finished successfully.
     */
    var onBoot: (Report) -> Unit = {}

    /**
     * This function is invoked when boot process hasn't finished successfully.
     */
    var onFailure: (Report) -> Unit = {}

    /**
     * Interface providing Java style means to easily instantiate [Listener] via
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
