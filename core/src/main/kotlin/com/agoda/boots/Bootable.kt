package com.agoda.boots

import com.agoda.boots.Key.Companion.multiple

/**
 * Main boot component. It is a piece of code that executes
 * any logic required to boot the system properly.
 * @property key unique identifier of the bootable
 * @property dependencies dependencies of the bootable. System will not proceed with
 *                        invoking the bootable until all bootables with given keys
 *                        have successfully executed.
 * @property isConcurrent flag that indicates that this bootable can be executed on
 *                        a separate thread.
 * @property isCritical Flag that indicated that this bootable is critical to the boot
 *                      process and should be started ASAP at all times. That means that
 *                      even if you request some other bootable to be executed, the system
 *                      will boot all critical bootables before actually start any
 *                      non-critical bootable.
 *                      Also, if critical bootable fails to boot, all boot tasks will stop
 *                      immediately and all listeners will be called back with failure.
 */
abstract class Bootable {

    abstract val key: Key.Single

    open val dependencies: Key.Multiple = multiple()
    open val isConcurrent: Boolean = true
    open val isCritical: Boolean = false

    /**
     * Main function that is intended to execute all necessary boot logic.
     */
    @Throws(Throwable::class)
    abstract fun boot()

    override fun hashCode() = key.hashCode()
    override fun equals(other: Any?) = key == other

}
