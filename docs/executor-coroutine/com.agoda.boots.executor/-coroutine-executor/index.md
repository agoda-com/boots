[executor-coroutine](../../index.md) / [com.agoda.boots.executor](../index.md) / [CoroutineExecutor](./index.md)

# CoroutineExecutor

`open class CoroutineExecutor : Executor`

Coroutine implementation of [Executor](#).

Can switch to dispatcher to launch [concurrent](#) bootables.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CoroutineExecutor(coroutineScope: CoroutineScope, dispatcher: CoroutineDispatcher, capacity: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = DEFAULT_CAPACITY)`<br>Coroutine implementation of [Executor](#). |

### Properties

| Name | Summary |
|---|---|
| [capacity](capacity.md) | `open val capacity: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [isMainThreadSupported](is-main-thread-supported.md) | `open val isMainThreadSupported: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

### Functions

| Name | Summary |
|---|---|
| [execute](execute.md) | `open fun execute(isConcurrent: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, executable: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
