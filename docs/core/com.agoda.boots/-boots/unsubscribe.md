[core](../../index.md) / [com.agoda.boots](../index.md) / [Boots](index.md) / [unsubscribe](./unsubscribe.md)

# unsubscribe

`@JvmStatic fun unsubscribe(key: `[`Key`](../-key/index.md)`, listener: `[`Listener`](../-listener/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Removes given listener from event callbacks of a given key.

### Parameters

`key` - bootable/bootables identifier

`listener` - unsubscribing instance`@JvmStatic fun unsubscribe(listener: `[`Listener`](../-listener/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Removes given listener from all event callbacks completely.

### Parameters

`listener` - unsubscribing instance