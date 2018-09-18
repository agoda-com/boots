[core](../../index.md) / [com.agoda.boots.impl](../index.md) / [DefaultNotifier](./index.md)

# DefaultNotifier

`open class DefaultNotifier : `[`Notifier`](../../com.agoda.boots/-notifier/index.md)

Default implementation of [Notifier](../../com.agoda.boots/-notifier/index.md).

Implementation is very simple and straightforward. It stores all listeners
in a map, and on status change looks for single key match first, then checks
all other listeners if they can be invoked and if can, invoke and remove them.
All calls are synchronous.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `DefaultNotifier()`<br>Default implementation of [Notifier](../../com.agoda.boots/-notifier/index.md). |

### Properties

| Name | Summary |
|---|---|
| [boots](boots.md) | `open val boots: `[`MutableMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)`<`[`Key`](../../com.agoda.boots/-key/index.md)`, `[`Bootable`](../../com.agoda.boots/-bootable/index.md)`>`<br>map of bootables added to the system |
| [executor](executor.md) | `open lateinit var executor: `[`Executor`](../../com.agoda.boots/-executor/index.md)<br>instance of [Executor](../../com.agoda.boots/-executor/index.md) used by the system |
| [listeners](listeners.md) | `val listeners: `[`MutableMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)`<`[`Key`](../../com.agoda.boots/-key/index.md)`, `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`Listener`](../../com.agoda.boots/-listener/index.md)`>>`<br>container of listeners |
| [logger](logger.md) | `open var logger: `[`Logger`](../../com.agoda.boots/-logger/index.md)`?`<br>instance of [Logger](../../com.agoda.boots/-logger/index.md) used by the system |

### Functions

| Name | Summary |
|---|---|
| [add](add.md) | `open fun add(key: `[`Key`](../../com.agoda.boots/-key/index.md)`, listener: `[`Listener`](../../com.agoda.boots/-listener/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Enables given listener to receive event callbacks for given key. |
| [notify](notify.md) | `open fun notify(key: `[`Single`](../../com.agoda.boots/-key/-single/index.md)`, report: `[`Report`](../../com.agoda.boots/-report/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Informs the notifier about bootable state change in the boot system so that it can check if there is any listener that it need to call back and invoke any found ones. |
| [remove](remove.md) | `open fun remove(key: `[`Key`](../../com.agoda.boots/-key/index.md)`, listener: `[`Listener`](../../com.agoda.boots/-listener/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Disables given listener to receive event callbacks for given key.`open fun remove(listener: `[`Listener`](../../com.agoda.boots/-listener/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Disables given listener to receive event callbacks for all events. |
