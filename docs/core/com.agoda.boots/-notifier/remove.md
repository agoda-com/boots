[core](../../index.md) / [com.agoda.boots](../index.md) / [Notifier](index.md) / [remove](./remove.md)

# remove

`abstract fun remove(key: `[`Key`](../-key/index.md)`, listener: `[`Listener`](../-listener/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Disables given listener to receive event callbacks for given key.

### Parameters

`key` - identifier of bootable/bootables to stop listen for

`listener` - listener to not be invoked on such events`abstract fun remove(listener: `[`Listener`](../-listener/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Disables given listener to receive event callbacks for all events.

### Parameters

`listener` - listener to not be invoked