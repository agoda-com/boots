[boots](../../index.md) / [com.agoda.boots](../index.md) / [Boots](index.md) / [subscribe](./subscribe.md)

# subscribe

`@JvmStatic fun subscribe(key: `[`Key`](../-key/index.md)`, listener: `[`Listener`](../-listener/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Adds given listener to event callbacks of a given key.

### Parameters

`key` - bootable/bootables identifier

`listener` - subscribing instance`fun subscribe(key: `[`Key`](../-key/index.md)`, listener: `[`Listener`](../-listener/index.md)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Listener`](../-listener/index.md)

Adds given listener to event callbacks of a given key.

### Parameters

`key` - bootable/bootables identifier

`listener` - receiving lambda of listener

**Return**
created listener instance

`@JvmStatic fun subscribe(key: `[`Key`](../-key/index.md)`, listener: `[`Builder`](../-listener/-builder/index.md)`): `[`Listener`](../-listener/index.md)

Adds given listener to event callbacks of a given key.

### Parameters

`key` - bootable/bootables identifier

`listener` - builder instance

**Return**
created listener instance

