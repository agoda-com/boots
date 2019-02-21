[executor-coroutine-android](../../index.md) / [com.agoda.boots.executor](../index.md) / [CoroutineAndroidExecutor](./index.md)

# CoroutineAndroidExecutor

`class CoroutineAndroidExecutor : CoroutineExecutor`

Coroutine Android implementation of [Executor](#).

Can switch to MainThread Dispatcher to invoke listeners
and [non-concurrent](#) bootables.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CoroutineAndroidExecutor(coroutineScope: CoroutineScope, dispatcher: CoroutineDispatcher, capacity: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = DEFAULT_CAPACITY)`<br>Coroutine Android implementation of [Executor](#). |

### Properties

| Name | Summary |
|---|---|
| [capacity](capacity.md) | `val capacity: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [isMainThreadSupported](is-main-thread-supported.md) | `val isMainThreadSupported: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
