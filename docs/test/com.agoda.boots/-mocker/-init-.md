[test](../../index.md) / [com.agoda.boots](../index.md) / [Mocker](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`Mocker(tail: `[`Mocker`](index.md)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = {})`

This class provides easy-to-use mocking framework through Mockito library.

By default, any [boot](#) request or [subscription](#) will immediately invoke
provided listener calling [onBoot](#).

By default, any [report](#) will return report with [Booted](#) status and 0 execution time.

