[core](../../index.md) / [com.agoda.boots](../index.md) / [Reporter](index.md) / [set](./set.md)

# set

`abstract fun set(key: `[`Single`](../-key/-single/index.md)`, status: `[`Status`](../-status/index.md)`, start: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)` = -1L, time: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)` = -1L): `[`Report`](../-report/index.md)

Saves the report data from a controller [object](../-boots/index.md).

### Parameters

`key` - identifier of the bootable

`status` - current status of the bootable

`start` - boot start time, -1 if [idle](../-status/-idle/index.md)

`time` - boot time, -1 if not [booted](../-status/-booted/index.md) or [failed](../-status/-failed/index.md)

**Return**
updated [Report](../-report/index.md) instance

