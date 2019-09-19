[boots](../../index.md) / [com.agoda.boots](../index.md) / [StrongConnectedBootException](./index.md)

# StrongConnectedBootException

`class StrongConnectedBootException : `[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)

Exception that indicates that strong connected components were found in
the [bootables](../-bootable/index.md) graph.

### Parameters

`scc` - list of strong connected components

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `StrongConnectedBootException(scc: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Key`](../-key/index.md)`>>)`<br>Exception that indicates that strong connected components were found in the [bootables](../-bootable/index.md) graph. |

### Properties

| Name | Summary |
|---|---|
| [message](message.md) | `val message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
