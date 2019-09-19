[boots](../../index.md) / [com.agoda.boots](../index.md) / [Boots](index.md) / [add](./add.md)

# add

`fun add(key: `[`Single`](../-key/-single/index.md)`, dependencies: `[`Multiple`](../-key/-multiple/index.md)` = multiple(), isConcurrent: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = true, isCritical: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = false, boot: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Creates instance of bootable and adds it to the system's pool as well
as to components ([Reporter](../-reporter/index.md), [Notifier](../-notifier/index.md), [Sequencer](../-sequencer/index.md)).

Also runs verification check on every invocation trying to find
SCC (strong connected components) and ICC (incorrect connected components).

### Parameters

`key` - identifier of bootable

`dependencies` - identifiers of bootables which should be executed prior

`isConcurrent` - `true` if it can be executed in parallel

`isCritical` - `true` if needs to be booted ASAP

`boot` - function to invoke when booting`@JvmStatic fun add(vararg bootables: `[`Bootable`](../-bootable/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)
`@JvmStatic fun add(bootables: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Bootable`](../-bootable/index.md)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Adds given bootable to the system's pool and adds them
to components ([Reporter](../-reporter/index.md), [Notifier](../-notifier/index.md), [Sequencer](../-sequencer/index.md)).

Also runs verification check on every invocation trying to find
SCC (strong connected components) and ICC (incorrect connected components).

### Parameters

`bootables` - bootables to add