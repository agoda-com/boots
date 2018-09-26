[test](../../index.md) / [com.agoda.boots.executor](../index.md) / [TestExecutor](./index.md)

# TestExecutor

`class TestExecutor : Executor`

Implementation of [Executor](#) inteded for testing purposes.

It executes all [bootables](#) in a blocking manner.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `TestExecutor()`<br>Implementation of [Executor](#) inteded for testing purposes. |

### Properties

| Name | Summary |
|---|---|
| [capacity](capacity.md) | `val capacity: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [isMainThreadSupported](is-main-thread-supported.md) | `val isMainThreadSupported: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

### Functions

| Name | Summary |
|---|---|
| [execute](execute.md) | `fun execute(isConcurrent: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, executable: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
