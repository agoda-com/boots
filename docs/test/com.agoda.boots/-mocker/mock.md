[test](../../index.md) / [com.agoda.boots](../index.md) / [Mocker](index.md) / [mock](./mock.md)

# mock

`fun mock(key: Key, status: Status): `[`Mocker`](index.md)

Mocks behaviour of library for given key.

When mocked, [boot](#) request or [subscription](#) with given key will:

* immediately invoke provided listener calling [onBoot](#) if provided status is [Booted](#).
* immediately invoke provided listener calling [onBoot](#) if provided status is [Booted](#).
* will not invoke provided listener in all other cases

When mocked, [report](#) for given key will return report with given status and 0 execution time.

### Parameters

`key` - identifier to mock

`status` - status to mock

**Return**
self (for Java builder style usage)

