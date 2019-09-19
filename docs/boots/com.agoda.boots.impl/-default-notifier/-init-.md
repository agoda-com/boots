[boots](../../index.md) / [com.agoda.boots.impl](../index.md) / [DefaultNotifier](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`DefaultNotifier()`

Default implementation of [Notifier](../../com.agoda.boots/-notifier/index.md).

Implementation is very simple and straightforward. It stores all listeners
in a map, and on status change looks for single key match first, then checks
all other listeners if they can be invoked and if can, invoke and remove them.
All calls are synchronous.

