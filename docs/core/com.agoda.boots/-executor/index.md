[core](../../index.md) / [com.agoda.boots](../index.md) / [Executor](./index.md)

# Executor

`interface Executor`

Executor is responsible for schedule, executions and switching thread context
(not necessary) for invocation of [boot()](../-bootable/boot.md) function.

### Properties

| Name | Summary |
|---|---|
| [capacity](capacity.md) | `abstract val capacity: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>maximum concurrent threads capacity |
| [isMainThreadSupported](is-main-thread-supported.md) | `abstract val isMainThreadSupported: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>true if executor can execute given function on main thread (switch context) |

### Functions

| Name | Summary |
|---|---|
| [execute](execute.md) | `abstract fun execute(isConcurrent: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, executable: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Schedules and/or executes given executable on a main/separate thread. |

### Companion Object Properties

| Name | Summary |
|---|---|
| [DEFAULT_CAPACITY](-d-e-f-a-u-l-t_-c-a-p-a-c-i-t-y.md) | `const val DEFAULT_CAPACITY: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

### Inheritors

| Name | Summary |
|---|---|
| [DefaultExecutor](../../com.agoda.boots.impl/-default-executor/index.md) | `open class DefaultExecutor : `[`Executor`](./index.md)<br>Default implementation of [Executor](./index.md) for JVM. |
