[boots](../../index.md) / [com.agoda.boots.impl](../index.md) / [DefaultExecutor](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`DefaultExecutor(capacity: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = DEFAULT_CAPACITY)`

Default implementation of [Executor](../../com.agoda.boots/-executor/index.md) for JVM.

Implementation uses cached [ThreadPoolExecutor](https://docs.oracle.com/javase/6/docs/api/java/util/concurrent/ThreadPoolExecutor.html) under the hood and is not able
to forward non-concurrent bootables to the main thread, executing them on the thread
that has invoked it.

Thus, it is not possible to build boot chain where non-concurrent bootables depends on
execution of concurrent ones.

**See Also**

[DefaultSequencer](../-default-sequencer/index.md)

