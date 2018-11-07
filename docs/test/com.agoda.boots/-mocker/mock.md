[test](../../index.md) / [com.agoda.boots](../index.md) / [Mocker](index.md) / [mock](./mock.md)

# mock

`fun mock(key: Key, status: Status): `[`Mocker`](index.md)

Mocks behaviour of library for given key.

When mocked, [boot](#) request or [subscription](#) with given key will:

* immediately invoke provided listener calling [onBoot](#) if provided status is [Booted](#).
* immediately invoke provided listener calling [onFailure](#) if provided status is [Failed](#).
* will not invoke provided listener in all other cases

When mocked, [report](#) for given key will return report with given status and 0 execution time.

### Parameters

`key` - identifier to mock

`status` - status to set

**Return**
self (for Java builder style usage)

`fun mock(key: Key, report: Report): `[`Mocker`](index.md)

Mocks behaviour of library for given key.

When mocked, [boot](#) request or [subscription](#) with given key will:

* immediately invoke provided listener calling [onBoot](#) if provided report's status is [Booted](#).
* immediately invoke provided listener calling [onFailure](#) if provided report's status is [Failed](#).
* will not invoke provided listener in all other cases

When mocked, [report](#) for given key will return given report.

### Parameters

`key` - identifier to mock

`report` - report to return

**Return**
self (for Java builder style usage)

