[boots](../../index.md) / [com.agoda.boots.impl](../index.md) / [DefaultExecutor](index.md) / [execute](./execute.md)

# execute

`open fun execute(isConcurrent: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, executable: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Overrides [Executor.execute](../../com.agoda.boots/-executor/execute.md)

Schedules and/or executes given executable on a main/separate thread.

### Parameters

`isConcurrent` - true if executable needs to be invoked on separate thread

`executable` - function to invoke