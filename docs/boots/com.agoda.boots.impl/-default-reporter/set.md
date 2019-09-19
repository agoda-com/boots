[boots](../../index.md) / [com.agoda.boots.impl](../index.md) / [DefaultReporter](index.md) / [set](./set.md)

# set

`open fun set(key: `[`Single`](../../com.agoda.boots/-key/-single/index.md)`, status: `[`Status`](../../com.agoda.boots/-status/index.md)`, start: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, time: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Report`](../../com.agoda.boots/-report/index.md)

Overrides [Reporter.set](../../com.agoda.boots/-reporter/set.md)

Saves the report data from a controller [object](../../com.agoda.boots/-boots/index.md).

### Parameters

`key` - identifier of the bootable

`status` - current status of the bootable

`start` - boot start time, -1 if [idle](../../com.agoda.boots/-status/-idle/index.md)

`time` - boot time, -1 if not [booted](../../com.agoda.boots/-status/-booted/index.md) or [failed](../../com.agoda.boots/-status/-failed/index.md)

**Return**
updated [Report](../../com.agoda.boots/-report/index.md) instance

