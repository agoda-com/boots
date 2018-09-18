[core](../../index.md) / [com.agoda.boots.impl](../index.md) / [DefaultReporter](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`DefaultReporter()`

Default implementation of [Reporter](../../com.agoda.boots/-reporter/index.md)

Implementation is very simple and straightforward. It stores all reports
for [single](../../com.agoda.boots/-key/-single/index.md) bootables in a map and inserts/updates (by copy)
on every [set()](../../com.agoda.boots/-reporter/set.md) invocation.

When getting the report, it looks up the map if the requested key is [single](../../com.agoda.boots/-key/-single/index.md),
otherwise it generates new combined report on every request.

