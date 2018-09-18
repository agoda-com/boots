[core](../../index.md) / [com.agoda.boots](../index.md) / [Executor](index.md) / [execute](./execute.md)

# execute

`abstract fun execute(isConcurrent: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, executable: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Schedules and/or executes given executable on a main/separate thread.

### Parameters

`isConcurrent` - true if executable needs to be invoked on separate thread

`executable` - function to invoke