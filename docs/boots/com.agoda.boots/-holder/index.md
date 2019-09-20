[boots](../../index.md) / [com.agoda.boots](../index.md) / [Holder](./index.md)

# Holder

`interface Holder`

Holder interface is used to propagate all components with bootable instances
provided to the system as well as providing access to [Executor](../-executor/index.md) and [Logger](../-logger/index.md)
without exposing them through controller [object](../-boots/index.md) and make components
invoke it in cycle manner.

Controller [object](../-boots/index.md) takes care of providing values to the properties of
the holders.

### Properties

| Name | Summary |
|---|---|
| [boots](boots.md) | `abstract val boots: `[`MutableMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)`<`[`Key`](../-key/index.md)`, `[`Bootable`](../-bootable/index.md)`>`<br>map of bootables added to the system |
| [executor](executor.md) | `abstract var executor: `[`Executor`](../-executor/index.md)<br>instance of [Executor](../-executor/index.md) used by the system |
| [logger](logger.md) | `abstract var logger: `[`Logger`](../-logger/index.md)`?`<br>instance of [Logger](../-logger/index.md) used by the system |

### Functions

| Name | Summary |
|---|---|
| [add](add.md) | `open fun add(bootables: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Bootable`](../-bootable/index.md)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Adds bootables to the storage and maps it with corresponding keys for easier access. |
| [all](all.md) | `open fun all(): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Bootable`](../-bootable/index.md)`>`<br>Retrieves all available bootables to the system. |
| [critical](critical.md) | `open fun critical(): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Bootable`](../-bootable/index.md)`>`<br>Retrieves instances of bootables that have thier [isCritical](../-bootable/is-critical.md) flag set to `true`. |
| [excluding](excluding.md) | `open fun excluding(key: `[`Key.Excluding`](../-key/-excluding/index.md)`): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Bootable`](../-bootable/index.md)`>`<br>Retrieves instances of bootables excluding ones in the given excluding key. |
| [multiple](multiple.md) | `open fun multiple(key: `[`Key.Multiple`](../-key/-multiple/index.md)`): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Bootable`](../-bootable/index.md)`>`<br>Retrieves instances of bootables that are included in the given multiple key. |

### Inheritors

| Name | Summary |
|---|---|
| [Notifier](../-notifier/index.md) | `interface Notifier : `[`Holder`](./index.md)<br>Notifier is responsible for managing listeners, invoking them at appropriate moments and switch context (if possible). |
| [Reporter](../-reporter/index.md) | `interface Reporter : `[`Holder`](./index.md)<br>Reported is responsible for storing, retrieving and generating [reports](../-report/index.md). |
| [Sequencer](../-sequencer/index.md) | `interface Sequencer : `[`Holder`](./index.md)<br>Sequencer is responsible for deciding the order of bootable invocation based on the user's request. |
