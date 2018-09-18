[core](../../index.md) / [com.agoda.boots.impl](../index.md) / [DefaultSequencer](index.md) / [next](./next.md)

# next

`open fun next(finished: `[`Report`](../../com.agoda.boots/-report/index.md)`?): `[`Bootable`](../../com.agoda.boots/-bootable/index.md)`?`

Overrides [Sequencer.next](../../com.agoda.boots/-sequencer/next.md)

Request to retrieve bootable which should be invoked next.
It is sequencer's responsibility to prioritize between tasks and
verify that all dependencies for chosen bootable has been resolved.

**Return**
instance of bootable to be invoked or `null` if there is no bootable
    left or if there is no bootables ready to boot.

