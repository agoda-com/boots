[boots](../../index.md) / [com.agoda.boots](../index.md) / [Report](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`Report(key: `[`Key`](../-key/index.md)`, status: `[`Status`](../-status/index.md)`, start: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)` = -1L, time: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)` = -1L, dependent: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Report`](index.md)`> = emptyList())`

Report provides all available information on a single [bootable](../-bootable/index.md),
or a combination of them.

For example, if report's key is [All](../-key/-all/index.md), then report represent
shared status of all bootables and time it took to boot all of them.
[dependent](dependent.md) property in that case will contain reports for every single
[bootable](../-bootable/index.md) without dependencies, which in return a list of reports on [bootables](../-bootable/index.md)
that were declaring dependency on it.

Default rules for the calculation of common status are as follows:

* if there is any [bootable](../-bootable/index.md) in the report failed to boot, status will be [failed](../-status/-failed/index.md)
* if all of [bootables](../-bootable/index.md) in the report succeeded to boot, status will be [booted](../-status/-booted/index.md)
* if all of [bootables](../-bootable/index.md) in the report are idle, status will be [idle](../-status/-idle/index.md)
* if none of the above conditions are satisfied, status will be [booting](../-status/-booting/index.md)

### Parameters

`key` - key of report. Can be any type of the [Key](../-key/index.md)

`status` - status of report. Can be any type of the [Status](../-status/index.md)

`start` - start timestamp of boot process. -1 if status is [idle](../-status/-idle/index.md)

`time` - time it took to finish boot process. -1 if status is not [booted](../-status/-booted/index.md) or [failed](../-status/-failed/index.md)

`dependent` - reports of a single [bootables](../-bootable/index.md) that are dependent on the [key](key.md) of this report, or
    non dependent reports that the current report includes (e.g. reports [key](key.md) is
    [multiple](../-key/-multiple/index.md), [critical](../-key/-critical/index.md) or [all](../-key/-all/index.md))