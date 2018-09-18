[core](../../index.md) / [com.agoda.boots.impl](../index.md) / [DefaultSequencer](index.md) / [resolve](./resolve.md)

# resolve

`protected fun resolve(bootables: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Bootable`](../../com.agoda.boots/-bootable/index.md)`>): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`Key`](../../com.agoda.boots/-key/index.md)`>`

Builds the boot task based on the list of bootables that has to be booted.
Function resolves and adds required dependencies to the provided list.

### Parameters

`bootables` - list of bootables that are required to boot

**Return**
list of bootables that will satisfy the requirement

