[core](../../index.md) / [com.agoda.boots](../index.md) / [Bootable](index.md) / [isCritical](./is-critical.md)

# isCritical

`open val isCritical: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Flag that indicated that this bootable is critical to the boot
    process and should be started ASAP at all times. That means that
    even if you request some other bootable to be executed, the system
    will boot all critical bootables before actually start any
    non-critical bootable.
    Also, if critical bootable fails to boot, all boot tasks will stop
    immediately and all listeners will be called back with failure.

### Property

`isCritical` - Flag that indicated that this bootable is critical to the boot
    process and should be started ASAP at all times. That means that
    even if you request some other bootable to be executed, the system
    will boot all critical bootables before actually start any
    non-critical bootable.
    Also, if critical bootable fails to boot, all boot tasks will stop
    immediately and all listeners will be called back with failure.