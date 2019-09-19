[boots](../../index.md) / [com.agoda.boots](../index.md) / [Notifier](index.md) / [notify](./notify.md)

# notify

`abstract fun notify(key: `[`Single`](../-key/-single/index.md)`, report: `[`Report`](../-report/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Informs the notifier about bootable state change in the boot system
so that it can check if there is any listener that it need to call back
and invoke any found ones.

By default, any listener that has been invoked is being removed.
If you have same listener added for different keys, it will be completely
removed from notifier only when all of the key callbacks has been satisfied.

### Parameters

`key` - identifier of bootable that has changed state

`report` - most recent report for given key