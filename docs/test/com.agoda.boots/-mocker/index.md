[test](../../index.md) / [com.agoda.boots](../index.md) / [Mocker](./index.md)

# Mocker

`class Mocker`

This class provides easy-to-use mocking framework through Mockito library.

By default, any [boot](#) request or [subscription](#) will immediately invoke
provided listener calling [onBoot](#).

By default, any [report](#) will return report with [Booted](#) status and 0 execution time.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `Mocker(tail: `[`Mocker`](./index.md)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = {})`<br>This class provides easy-to-use mocking framework through Mockito library. |

### Functions

| Name | Summary |
|---|---|
| [mock](mock.md) | `fun mock(key: Key, status: Status): `[`Mocker`](./index.md)<br>Mocks behaviour of library for given key. |
