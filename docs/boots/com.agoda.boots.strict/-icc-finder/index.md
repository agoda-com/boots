[boots](../../index.md) / [com.agoda.boots.strict](../index.md) / [IccFinder](./index.md)

# IccFinder

`class IccFinder`

This class is used to determine ICC (Incorrect connected components) in the
[Bootable](../../com.agoda.boots/-bootable/index.md)'s dependency tree every time you invoke [add()](../../com.agoda.boots/-boots/add.md).

As of now, ICC is considered to be:

* Bootable with [Bootable.isCritical](../../com.agoda.boots/-bootable/is-critical.md) flag set to `true` which is dependent
on a bootable with [Bootable.isCritical](../../com.agoda.boots/-bootable/is-critical.md) flag set to `false`
* Bootable with [Bootable.isConcurrent](../../com.agoda.boots/-bootable/is-concurrent.md) flag set to `false` which is dependent
on a bootable with [Bootable.isCritical](../../com.agoda.boots/-bootable/is-critical.md) flag set to `true` and system's
[Executor.isMainThreadSupported](../../com.agoda.boots/-executor/is-main-thread-supported.md)
is `false`

### Parameters

`boots` - list of bootables to process

`isMainThreadSupported` - flag indicating if switching to main thread's context is supported

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `IccFinder(boots: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Bootable`](../../com.agoda.boots/-bootable/index.md)`>, isMainThreadSupported: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`)`<br>This class is used to determine ICC (Incorrect connected components) in the [Bootable](../../com.agoda.boots/-bootable/index.md)'s dependency tree every time you invoke [add()](../../com.agoda.boots/-boots/add.md). |

### Functions

| Name | Summary |
|---|---|
| [find](find.md) | `fun find(): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)`<`[`Key`](../../com.agoda.boots/-key/index.md)`, `[`Key`](../../com.agoda.boots/-key/index.md)`>>`<br>Determines if given [Bootable](../../com.agoda.boots/-bootable/index.md) set contains ICC through it's dependency tree. |
