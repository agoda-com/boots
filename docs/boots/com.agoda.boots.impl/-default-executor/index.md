[boots](../../index.md) / [com.agoda.boots.impl](../index.md) / [DefaultExecutor](./index.md)

# DefaultExecutor

`open class DefaultExecutor : `[`Executor`](../../com.agoda.boots/-executor/index.md)

Default implementation of [Executor](../../com.agoda.boots/-executor/index.md) for JVM.

Implementation uses cached [ThreadPoolExecutor](https://docs.oracle.com/javase/6/docs/api/java/util/concurrent/ThreadPoolExecutor.html) under the hood and is not able
to forward non-concurrent bootables to the main thread, executing them on the thread
that has invoked it.

Thus, it is not possible to build boot chain where non-concurrent bootables depends on
execution of concurrent ones.

**See Also**

[DefaultSequencer](../-default-sequencer/index.md)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `DefaultExecutor(capacity: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = DEFAULT_CAPACITY)`<br>Default implementation of [Executor](../../com.agoda.boots/-executor/index.md) for JVM. |

### Properties

| Name | Summary |
|---|---|
| [capacity](capacity.md) | `open val capacity: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>maximum concurrent threads capacity |
| [isMainThreadSupported](is-main-thread-supported.md) | `open val isMainThreadSupported: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>true if executor can execute given function on main thread (switch context) |
| [pool](pool.md) | `val pool: `[`ExecutorService`](https://docs.oracle.com/javase/6/docs/api/java/util/concurrent/ExecutorService.html) |

### Functions

| Name | Summary |
|---|---|
| [execute](execute.md) | `open fun execute(isConcurrent: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, executable: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Schedules and/or executes given executable on a main/separate thread. |
