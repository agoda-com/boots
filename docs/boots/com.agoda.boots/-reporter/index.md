[boots](../../index.md) / [com.agoda.boots](../index.md) / [Reporter](./index.md)

# Reporter

`interface Reporter : `[`Holder`](../-holder/index.md)

Reported is responsible for storing, retrieving and generating [reports](../-report/index.md).

For example, if report's key is [All](../-key/-all/index.md), then report represent
shared status of all bootables and time it took to boot all of them.
[dependent](../-report/dependent.md) property in that case will contain reports for every single
[bootable](../-bootable/index.md) without dependencies, which in return a list of reports on [bootables](../-bootable/index.md)
that were declaring dependency on it.

Default rules for the calculation of common status are as follows:

* if there is any [bootable](../-bootable/index.md) in the report failed to boot, status will be [failed](../-status/-failed/index.md)
* if all of [bootables](../-bootable/index.md) in the report succeeded to boot, status will be [booted](../-status/-booted/index.md)
* if all of [bootables](../-bootable/index.md) in the report are idle, status will be [idle](../-status/-idle/index.md)
* if none of the above conditions are satisfied, status will be [booting](../-status/-booting/index.md)

### Inherited Properties

| Name | Summary |
|---|---|
| [boots](../-holder/boots.md) | `abstract val boots: `[`MutableMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)`<`[`Key`](../-key/index.md)`, `[`Bootable`](../-bootable/index.md)`>`<br>map of bootables added to the system |
| [executor](../-holder/executor.md) | `abstract var executor: `[`Executor`](../-executor/index.md)<br>instance of [Executor](../-executor/index.md) used by the system |
| [logger](../-holder/logger.md) | `abstract var logger: `[`Logger`](../-logger/index.md)`?`<br>instance of [Logger](../-logger/index.md) used by the system |

### Functions

| Name | Summary |
|---|---|
| [get](get.md) | `abstract fun get(key: `[`Key`](../-key/index.md)`): `[`Report`](../-report/index.md)<br>Retrieves the [report](../-report/index.md) for a given key. In case where [key](get.md#com.agoda.boots.Reporter$get(com.agoda.boots.Key)/key) is not [single](../-key/-single/index.md), reporter generates combined report based on the type of the [key](get.md#com.agoda.boots.Reporter$get(com.agoda.boots.Key)/key). |
| [set](set.md) | `abstract fun set(key: `[`Key.Single`](../-key/-single/index.md)`, status: `[`Status`](../-status/index.md)`, start: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)` = -1L, time: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)` = -1L): `[`Report`](../-report/index.md)<br>Saves the report data from a controller [object](../-boots/index.md). |

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
| [DefaultReporter](../../com.agoda.boots.impl/-default-reporter/index.md) | `open class DefaultReporter : `[`Reporter`](./index.md)<br>Default implementation of [Reporter](./index.md) |
