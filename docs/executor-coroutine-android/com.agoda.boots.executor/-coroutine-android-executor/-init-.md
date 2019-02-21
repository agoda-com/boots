[executor-coroutine-android](../../index.md) / [com.agoda.boots.executor](../index.md) / [CoroutineAndroidExecutor](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`CoroutineAndroidExecutor(coroutineScope: CoroutineScope, dispatcher: CoroutineDispatcher, capacity: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = DEFAULT_CAPACITY)`

Coroutine Android implementation of [Executor](#).

Can switch to MainThread Dispatcher to invoke listeners
and [non-concurrent](#) bootables.

