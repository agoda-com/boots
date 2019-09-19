[boots](../../index.md) / [com.agoda.boots](../index.md) / [Sequencer](index.md) / [next](./next.md)

# next

`abstract fun next(finished: `[`Report`](../-report/index.md)`?): `[`Bootable`](../-bootable/index.md)`?`

Request to retrieve bootable which should be invoked next.
It is sequencer's responsibility to prioritize between tasks and
verify that all dependencies for chosen bootable has been resolved.

**Return**
instance of bootable to be invoked or `null` if there is no bootable
    left or if there is no bootables ready to boot.

