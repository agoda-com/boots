[core](../../index.md) / [com.agoda.boots.impl](../index.md) / [DefaultNotifier](index.md) / [remove](./remove.md)

# remove

`open fun remove(key: `[`Key`](../../com.agoda.boots/-key/index.md)`, listener: `[`Listener`](../../com.agoda.boots/-listener/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Overrides [Notifier.remove](../../com.agoda.boots/-notifier/remove.md)

Disables given listener to receive event callbacks for given key.

### Parameters

`key` - identifier of bootable/bootables to stop listen for

`listener` - listener to not be invoked on such events`open fun remove(listener: `[`Listener`](../../com.agoda.boots/-listener/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Overrides [Notifier.remove](../../com.agoda.boots/-notifier/remove.md)

Disables given listener to receive event callbacks for all events.

### Parameters

`listener` - listener to not be invoked