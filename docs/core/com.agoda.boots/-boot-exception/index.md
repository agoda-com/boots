[core](../../index.md) / [com.agoda.boots](../index.md) / [BootException](./index.md)

# BootException

`class BootException : `[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)

Exception that aggregates the reasons of failure in case a [report](../-report/index.md) has
a [failed](../-status/-failed/index.md) state and is not with [single](../-key/-single/index.md) key.

### Parameters

`reasons` - map of throwables associated with keys of bootables that threw it.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `BootException(reasons: `[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)`<`[`Key`](../-key/index.md)`, `[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)`>)`<br>Exception that aggregates the reasons of failure in case a [report](../-report/index.md) has a [failed](../-status/-failed/index.md) state and is not with [single](../-key/-single/index.md) key. |

### Properties

| Name | Summary |
|---|---|
| [message](message.md) | `val message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
