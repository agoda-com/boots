[core](../../index.md) / [com.agoda.boots.impl](../index.md) / [DefaultSequencer](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`DefaultSequencer()`

Default implementation of [Sequencer](../../com.agoda.boots/-sequencer/index.md).

Implementation is very simple and straightforward. It calculates list of
bootables in a dependency-sorted order for every [start()](../../com.agoda.boots/-sequencer/start.md)
request as a separate task. By default it adds all non-booted [critical](../../com.agoda.boots/-bootable/is-critical.md)
bootables in front of any other to ensure that critical bootables are booted ASAP.

On every [next](next.md) invocation it goes through tasks lists and for every task
it tries to iterate through all of it's bootables to check if any of it is
qualified to be invoked. Boot qualification requirements can be found at [check()](check.md) function.

Also it will verify all added bootables

