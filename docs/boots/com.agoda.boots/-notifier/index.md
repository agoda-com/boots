[boots](../../index.md) / [com.agoda.boots](../index.md) / [Notifier](./index.md)

# Notifier

`interface Notifier : `[`Holder`](../-holder/index.md)

Notifier is responsible for managing listeners, invoking them at appropriate
moments and switch context (if possible).

By default, notifier is holding strong references to the provided listeners, so
keep in mind that you need to manually remove them is your outer class instance (if any)
is going to be garbage collected.

### Inherited Properties

| Name | Summary |
|---|---|
| [boots](../-holder/boots.md) | `abstract val boots: `[`MutableMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)`<`[`Key`](../-key/index.md)`, `[`Bootable`](../-bootable/index.md)`>`<br>map of bootables added to the system |
| [executor](../-holder/executor.md) | `abstract var executor: `[`Executor`](../-executor/index.md)<br>instance of [Executor](../-executor/index.md) used by the system |
| [logger](../-holder/logger.md) | `abstract var logger: `[`Logger`](../-logger/index.md)`?`<br>instance of [Logger](../-logger/index.md) used by the system |

### Functions

| Name | Summary |
|---|---|
| [add](add.md) | `abstract fun add(key: `[`Key`](../-key/index.md)`, listener: `[`Listener`](../-listener/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Enables given listener to receive event callbacks for given key. |
| [notify](notify.md) | `abstract fun notify(key: `[`Key.Single`](../-key/-single/index.md)`, report: `[`Report`](../-report/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Informs the notifier about bootable state change in the boot system so that it can check if there is any listener that it need to call back and invoke any found ones. |
| [remove](remove.md) | `abstract fun remove(key: `[`Key`](../-key/index.md)`, listener: `[`Listener`](../-listener/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Disables given listener to receive event callbacks for given key.`abstract fun remove(listener: `[`Listener`](../-listener/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Disables given listener to receive event callbacks for all events. |

### Inherited Functions

| Name | Summary |
|---|---|
| [add](../-holder/add.md) | `open fun add(bootables: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Bootable`](../-bootable/index.md)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Adds bootables to the storage and maps it with corresponding keys for easier access. |
| [all](../-holder/all.md) | `open fun all(): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Bootable`](../-bootable/index.md)`>`<br>Retrieves all available bootables to the system. |
| [critical](../-holder/critical.md) | `open fun critical(): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Bootable`](../-bootable/index.md)`>`<br>Retrieves instances of bootables that have thier [isCritical](../-bootable/is-critical.md) flag set to `true`. |
| [excluding](../-holder/excluding.md) | `open fun excluding(key: `[`Key.Excluding`](../-key/-excluding/index.md)`): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Bootable`](../-bootable/index.md)`>`<br>Retrieves instances of bootables excluding ones in the given excluding key. |
| [multiple](../-holder/multiple.md) | `open fun multiple(key: `[`Key.Multiple`](../-key/-multiple/index.md)`): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Bootable`](../-bootable/index.md)`>`<br>Retrieves instances of bootables that are included in the given multiple key. |

### Inheritors

| Name | Summary |
|---|---|
| [DefaultNotifier](../../com.agoda.boots.impl/-default-notifier/index.md) | `open class DefaultNotifier : `[`Notifier`](./index.md)<br>Default implementation of [Notifier](./index.md). |
