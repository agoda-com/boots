[boots](../../index.md) / [com.agoda.boots.impl](../index.md) / [DefaultReporter](./index.md)

# DefaultReporter

`open class DefaultReporter : `[`Reporter`](../../com.agoda.boots/-reporter/index.md)

Default implementation of [Reporter](../../com.agoda.boots/-reporter/index.md)

Implementation is very simple and straightforward. It stores all reports
for [single](../../com.agoda.boots/-key/-single/index.md) bootables in a map and inserts/updates (by copy)
on every [set()](../../com.agoda.boots/-reporter/set.md) invocation.

When getting the report, it looks up the map if the requested key is [single](../../com.agoda.boots/-key/-single/index.md),
otherwise it generates new combined report on every request.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `DefaultReporter()`<br>Default implementation of [Reporter](../../com.agoda.boots/-reporter/index.md) |

### Properties

| Name | Summary |
|---|---|
| [boots](boots.md) | `open val boots: `[`MutableMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)`<`[`Key`](../../com.agoda.boots/-key/index.md)`, `[`Bootable`](../../com.agoda.boots/-bootable/index.md)`>`<br>map of bootables added to the system |
| [executor](executor.md) | `open lateinit var executor: `[`Executor`](../../com.agoda.boots/-executor/index.md)<br>instance of [Executor](../../com.agoda.boots/-executor/index.md) used by the system |
| [logger](logger.md) | `open var logger: `[`Logger`](../../com.agoda.boots/-logger/index.md)`?`<br>instance of [Logger](../../com.agoda.boots/-logger/index.md) used by the system |
| [reports](reports.md) | `val reports: `[`MutableMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)`<`[`Single`](../../com.agoda.boots/-key/-single/index.md)`, `[`Report`](../../com.agoda.boots/-report/index.md)`>`<br>container of [single](../../com.agoda.boots/-key/-single/index.md) reports. |

### Functions

| Name | Summary |
|---|---|
| [get](get.md) | `open fun get(key: `[`Key`](../../com.agoda.boots/-key/index.md)`): `[`Report`](../../com.agoda.boots/-report/index.md)<br>Retrieves the [report](../../com.agoda.boots/-report/index.md) for a given key. In case where [key](../../com.agoda.boots/-reporter/get.md#com.agoda.boots.Reporter$get(com.agoda.boots.Key)/key) is not [single](../../com.agoda.boots/-key/-single/index.md), reporter generates combined report based on the type of the [key](../../com.agoda.boots/-reporter/get.md#com.agoda.boots.Reporter$get(com.agoda.boots.Key)/key). |
| [set](set.md) | `open fun set(key: `[`Single`](../../com.agoda.boots/-key/-single/index.md)`, status: `[`Status`](../../com.agoda.boots/-status/index.md)`, start: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, time: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Report`](../../com.agoda.boots/-report/index.md)<br>Saves the report data from a controller [object](../../com.agoda.boots/-boots/index.md). |
