[core](../../index.md) / [com.agoda.boots.impl](../index.md) / [DefaultSequencer](index.md) / [check](./check.md)

# check

`protected fun check(bootable: `[`Bootable`](../../com.agoda.boots/-bootable/index.md)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

To be qualified for boot, bootable should meet following requirements:

* if bootable is [critical](../../com.agoda.boots/-bootable/is-critical.md):
it should not have dependencies or it's dependencies should have already [booted](../../com.agoda.boots/-status/-booted/index.md)
* if bootable is not [critical](../../com.agoda.boots/-bootable/is-critical.md):
critical bootables should be loaded `AND` it should not have dependencies or it's
dependencies should have already [booted](../../com.agoda.boots/-status/-booted/index.md)

**Return**
`true` if bootable is qualified to be booted, `false` otherwise

