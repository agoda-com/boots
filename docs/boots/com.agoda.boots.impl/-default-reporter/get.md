[boots](../../index.md) / [com.agoda.boots.impl](../index.md) / [DefaultReporter](index.md) / [get](./get.md)

# get

`open fun get(key: `[`Key`](../../com.agoda.boots/-key/index.md)`): `[`Report`](../../com.agoda.boots/-report/index.md)

Overrides [Reporter.get](../../com.agoda.boots/-reporter/get.md)

Retrieves the [report](../../com.agoda.boots/-report/index.md) for a given key. In case where [key](../../com.agoda.boots/-reporter/get.md#com.agoda.boots.Reporter$get(com.agoda.boots.Key)/key) is not [single](../../com.agoda.boots/-key/-single/index.md),
reporter generates combined report based on the type of the [key](../../com.agoda.boots/-reporter/get.md#com.agoda.boots.Reporter$get(com.agoda.boots.Key)/key).

### Parameters

`key` - identifier of the requested report

**Return**
retrieved/generated [Report](../../com.agoda.boots/-report/index.md) instance

