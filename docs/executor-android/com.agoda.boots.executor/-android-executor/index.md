[executor-android](../../index.md) / [com.agoda.boots.executor](../index.md) / [AndroidExecutor](./index.md)

# AndroidExecutor

`class AndroidExecutor : DefaultExecutor`

Native implementation of [Executor](#) with Android support.

Can switch to Android's main thread context to invoke listeners
and [non-concurrent](#) bootables.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `AndroidExecutor(capacity: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = DEFAULT_CAPACITY)`<br>Native implementation of [Executor](#) with Android support. |

### Properties

| Name | Summary |
|---|---|
| [capacity](capacity.md) | `val capacity: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [isMainThreadSupported](is-main-thread-supported.md) | `val isMainThreadSupported: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

### Functions

| Name | Summary |
|---|---|
| [execute](execute.md) | `fun execute(isConcurrent: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, executable: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
