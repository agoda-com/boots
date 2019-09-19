[boots](../../index.md) / [com.agoda.boots.impl](../index.md) / [DefaultSequencer](index.md) / [update](./update.md)

# update

`protected fun update(report: `[`Report`](../../com.agoda.boots/-report/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Updates the tasks based on the information of incoming report.
If report says that some bootable is booted, that bootable is removed
from all tasks.
If report says that some bootable is failed and if that bootable is [critical](../../com.agoda.boots/-bootable/is-critical.md)
then all tasks containing this bootable will be cleared and cancelled (all tasks as of now).

### Parameters

`report` - updated report of [single](../../com.agoda.boots/-key/-single/index.md) bootable