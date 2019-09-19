[boots](../../index.md) / [com.agoda.boots.impl](../index.md) / [DefaultSequencer](index.md) / [visit](./visit.md)

# visit

`protected fun visit(key: `[`Key`](../../com.agoda.boots/-key/index.md)`, visited: `[`MutableMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)`<`[`Key`](../../com.agoda.boots/-key/index.md)`, `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>, list: `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`Key`](../../com.agoda.boots/-key/index.md)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Checks the bootable dependencies in a recursive manner and adds them before
adding bootable itself to the task.

### Parameters

`key` - identifier of bootable

`visited` - map with already checked flags

`list` - task where keys will be added