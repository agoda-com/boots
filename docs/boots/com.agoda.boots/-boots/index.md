[boots](../../index.md) / [com.agoda.boots](../index.md) / [Boots](./index.md)

# Boots

`object Boots`

Main controller object. Used to interact with the library.

The default scenario of library's usage is as follows:

1. Provide custom components if any via [configure()](configure.md) function
2. Add bootables to work with via [add()](add.md) function
3. Request to boot everything or some specific bootables with [boot()](boot.md) function
4. Observe boot events with [subscripbe()](subscribe.md)/[unsubscribe()](unsubscribe.md)
5. Get the current status with [report()](report.md) function

Supports both Kotlin DSL and Java style.

### Functions

| Name | Summary |
|---|---|
| [add](add.md) | `fun add(key: `[`Key.Single`](../-key/-single/index.md)`, dependencies: `[`Key.Multiple`](../-key/-multiple/index.md)` = multiple(), isConcurrent: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = true, isCritical: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = false, boot: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Creates instance of bootable and adds it to the system's pool as well as to components ([Reporter](../-reporter/index.md), [Notifier](../-notifier/index.md), [Sequencer](../-sequencer/index.md)).`fun add(vararg bootables: `[`Bootable`](../-bootable/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>`fun add(bootables: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Bootable`](../-bootable/index.md)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Adds given bootable to the system's pool and adds them to components ([Reporter](../-reporter/index.md), [Notifier](../-notifier/index.md), [Sequencer](../-sequencer/index.md)). |
| [boot](boot.md) | `fun boot(key: `[`Key`](../-key/index.md)`, listener: `[`Listener`](../-listener/index.md)`? = null): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>`fun boot(key: `[`Key`](../-key/index.md)`, listener: `[`Listener`](../-listener/index.md)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Listener`](../-listener/index.md)<br>`fun boot(key: `[`Key`](../-key/index.md)`, listener: `[`Listener.Builder`](../-listener/-builder/index.md)`): `[`Listener`](../-listener/index.md)<br>Requests library to boot given bootable/bootables satisfying it's dependencies and critical bootables before. |
| [configure](configure.md) | `fun configure(configuration: `[`Configuration`](../-configuration/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>`fun configure(configuration: `[`Configuration`](../-configuration/index.md)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Sets provided configuration and overrides current component implementations with defined in the given configuration object. |
| [invoke](invoke.md) | `operator fun invoke(tail: `[`Boots`](./index.md)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Dsl support operator function. |
| [report](report.md) | `fun report(key: `[`Key`](../-key/index.md)`): `[`Report`](../-report/index.md)<br>Retrieves the [report](../-report/index.md) for a given key. |
| [reset](reset.md) | `fun reset(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Clears bootable list and sets all components to default instances. Also sets [isStrictMode](../-configuration/is-strict-mode.md) to `true` and sets [logger](../-configuration/logger.md) to `null`. |
| [subscribe](subscribe.md) | `fun subscribe(key: `[`Key`](../-key/index.md)`, listener: `[`Listener`](../-listener/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>`fun subscribe(key: `[`Key`](../-key/index.md)`, listener: `[`Listener`](../-listener/index.md)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Listener`](../-listener/index.md)<br>`fun subscribe(key: `[`Key`](../-key/index.md)`, listener: `[`Listener.Builder`](../-listener/-builder/index.md)`): `[`Listener`](../-listener/index.md)<br>Adds given listener to event callbacks of a given key. |
| [unsubscribe](unsubscribe.md) | `fun unsubscribe(key: `[`Key`](../-key/index.md)`, listener: `[`Listener`](../-listener/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Removes given listener from event callbacks of a given key.`fun unsubscribe(listener: `[`Listener`](../-listener/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Removes given listener from all event callbacks completely. |
