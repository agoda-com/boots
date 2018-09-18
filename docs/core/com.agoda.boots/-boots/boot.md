[core](../../index.md) / [com.agoda.boots](../index.md) / [Boots](index.md) / [boot](./boot.md)

# boot

`@JvmOverloads @JvmStatic fun boot(key: `[`Key`](../-key/index.md)`, listener: `[`Listener`](../-listener/index.md)`? = null): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Requests library to boot given bootable/bootables satisfying
it's dependencies and critical bootables before.

It is library's responsibility to take care of boot order,
handling threading, measuring the performance.

### Parameters

`key` - bootable/bootables identifier

`listener` - subscribing instance`fun boot(key: `[`Key`](../-key/index.md)`, listener: `[`Listener`](../-listener/index.md)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Listener`](../-listener/index.md)

Requests library to boot given bootable/bootables satisfying
it's dependencies and critical bootables before.

It is library's responsibility to take care of boot order,
handling threading, measuring the performance.

### Parameters

`key` - bootable/bootables identifier

`listener` - receiving lambda of listener

**Return**
created listener instance

`@JvmStatic fun boot(key: `[`Key`](../-key/index.md)`, listener: `[`Builder`](../-listener/-builder/index.md)`): `[`Listener`](../-listener/index.md)

Requests library to boot given bootable/bootables satisfying
it's dependencies and critical bootables before.

It is library's responsibility to take care of boot order,
handling threading, measuring the performance.

### Parameters

`key` - bootable/bootables identifier

`listener` - builder instance

**Return**
created listener instance

