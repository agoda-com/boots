[core](../../index.md) / [com.agoda.boots.strict](../index.md) / [SccFinder](./index.md)

# SccFinder

`class SccFinder`

This class is used to determine SCC (Strong connected components) in the
[Bootable](../../com.agoda.boots/-bootable/index.md)'s dependency tree every time you invoke [add()](../../com.agoda.boots/-boots/add.md).

It's implementation is based on Tarjan algorithm of finding SCCs in directed graph.

### Parameters

`boots` - list of bootables to process

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `SccFinder(boots: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Bootable`](../../com.agoda.boots/-bootable/index.md)`>)`<br>This class is used to determine SCC (Strong connected components) in the [Bootable](../../com.agoda.boots/-bootable/index.md)'s dependency tree every time you invoke [add()](../../com.agoda.boots/-boots/add.md). |

### Functions

| Name | Summary |
|---|---|
| [find](find.md) | `fun find(): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Key`](../../com.agoda.boots/-key/index.md)`>>`<br>Determines if given [Bootable](../../com.agoda.boots/-bootable/index.md) set contains SCC through it's dependency tree. |
