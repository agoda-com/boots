[boots](../../index.md) / [com.agoda.boots](../index.md) / [Reporter](index.md) / [get](./get.md)

# get

`abstract fun get(key: `[`Key`](../-key/index.md)`): `[`Report`](../-report/index.md)

Retrieves the [report](../-report/index.md) for a given key. In case where [key](get.md#com.agoda.boots.Reporter$get(com.agoda.boots.Key)/key) is not [single](../-key/-single/index.md),
reporter generates combined report based on the type of the [key](get.md#com.agoda.boots.Reporter$get(com.agoda.boots.Key)/key).

### Parameters

`key` - identifier of the requested report

**Return**
retrieved/generated [Report](../-report/index.md) instance

