[boots](../../index.md) / [com.agoda.boots](../index.md) / [Bootable](./index.md)

# Bootable

`abstract class Bootable`

Main boot component. It is a piece of code that executes
any logic required to boot the system properly.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `Bootable()`<br>Main boot component. It is a piece of code that executes any logic required to boot the system properly. |

### Properties

| Name | Summary |
|---|---|
| [dependencies](dependencies.md) | `open val dependencies: `[`Key.Multiple`](../-key/-multiple/index.md)<br>dependencies of the bootable. System will not proceed with     invoking the bootable until all bootables with given keys     have successfully executed. |
| [isConcurrent](is-concurrent.md) | `open val isConcurrent: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>flag that indicates that this bootable can be executed on     a separate thread. |
| [isCritical](is-critical.md) | `open val isCritical: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Flag that indicated that this bootable is critical to the boot     process and should be started ASAP at all times. That means that     even if you request some other bootable to be executed, the system     will boot all critical bootables before actually start any     non-critical bootable.     Also, if critical bootable fails to boot, all boot tasks will stop     immediately and all listeners will be called back with failure. |
| [key](key.md) | `abstract val key: `[`Key.Single`](../-key/-single/index.md)<br>unique identifier of the bootable |

### Functions

| Name | Summary |
|---|---|
| [boot](boot.md) | `abstract fun boot(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Main function that is intended to execute all necessary boot logic. |
| [equals](equals.md) | `open fun equals(other: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](hash-code.md) | `open fun hashCode(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
