[boots](../../index.md) / [com.agoda.boots](../index.md) / [IncorrectConnectedBootException](./index.md)

# IncorrectConnectedBootException

`class IncorrectConnectedBootException : `[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)

Exception that indicates that incorrect connected components were found in
the [bootables](../-bootable/index.md) graph.

### Parameters

`icc` - list of incorrect connected components

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `IncorrectConnectedBootException(icc: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)`<`[`Key`](../-key/index.md)`, `[`Key`](../-key/index.md)`>>)`<br>Exception that indicates that incorrect connected components were found in the [bootables](../-bootable/index.md) graph. |

### Properties

| Name | Summary |
|---|---|
| [message](message.md) | `val message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
