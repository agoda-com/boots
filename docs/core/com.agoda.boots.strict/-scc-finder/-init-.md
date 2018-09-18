[core](../../index.md) / [com.agoda.boots.strict](../index.md) / [SccFinder](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`SccFinder(boots: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Bootable`](../../com.agoda.boots/-bootable/index.md)`>)`

This class is used to determine SCC (Strong connected components) in the
[Bootable](../../com.agoda.boots/-bootable/index.md)'s dependency tree every time you invoke [add()](../../com.agoda.boots/-boots/add.md).

It's implementation is based on Tarjan algorithm of finding SCCs in directed graph.

### Parameters

`boots` - list of bootables to process