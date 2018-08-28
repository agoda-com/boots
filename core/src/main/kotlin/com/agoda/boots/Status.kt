package com.agoda.boots

/**
 * Represents the status of a given [Bootable] within the boot system.
 * Status is returned as a property of [Report] data class returned by the
 * [Boots.report].
 */
sealed class Status {

    /**
     * Idle status means that [Bootable] hasn't been touched yet by the boot system.
     * All freshly added [Bootable]s through [add()][Boots.add] get the idle status automatically.
     */
    class Idle : Status()

    /**
     * Booting status means that [Bootable] has been added to the [Executor]'s queue and will
     * start executing/executing right now.
     */
    class Booting : Status()

    /**
     * Booted status means that [Bootable] has finished it's [boot()][Bootable.boot] invocation
     * without throwing any exception, thus meaning that it has booted successfully.
     */
    class Booted : Status()

    /**
     * Failed status means that [Bootable] threw an exception during [boot()][Bootable.boot]
     * invocation, thus meaning that it hasn't booted successfully.
     *
     * @param reason exception that the [boot()][Bootable.boot] function has thrown.
     */
    class Failed(val reason: Throwable) : Status()

    companion object {
        /**
         * Creates an instance of [Idle] status
         * @return instance of [Idle]
         */
        @JvmStatic
        fun idle(): Status = Status.Idle()

        /**
         * Creates an instance of [Booting] status
         * @return instance of [Booting]
         */
        @JvmStatic
        fun booting(): Status = Status.Booting()

        /**
         * Creates an instance of [Booted] status
         * @return instance of [Booted]
         */
        @JvmStatic
        fun booted(): Status = Status.Booted()

        /**
         * Creates an instance of [Failed] status
         * @param reason reason of failure
         * @return instance of [Failed]
         */
        @JvmStatic
        fun failed(reason: Throwable): Status = Status.Failed(reason)
    }

}
