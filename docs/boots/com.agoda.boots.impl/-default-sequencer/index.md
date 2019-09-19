[boots](../../index.md) / [com.agoda.boots.impl](../index.md) / [DefaultSequencer](./index.md)

# DefaultSequencer

`open class DefaultSequencer : `[`Sequencer`](../../com.agoda.boots/-sequencer/index.md)

Default implementation of [Sequencer](../../com.agoda.boots/-sequencer/index.md).

Implementation is very simple and straightforward. It calculates list of
bootables in a dependency-sorted order for every [start()](../../com.agoda.boots/-sequencer/start.md)
request as a separate task. By default it adds all non-booted [critical](../../com.agoda.boots/-bootable/is-critical.md)
bootables in front of any other to ensure that critical bootables are booted ASAP.

On every [next](next.md) invocation it goes through tasks lists and for every task
it tries to iterate through all of it's bootables to check if any of it is
qualified to be invoked. Boot qualification requirements can be found at [check()](check.md) function.

Also it will verify all added bootables

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `DefaultSequencer()`<br>Default implementation of [Sequencer](../../com.agoda.boots/-sequencer/index.md). |

### Properties

| Name | Summary |
|---|---|
| [boots](boots.md) | `open val boots: `[`MutableMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)`<`[`Key`](../../com.agoda.boots/-key/index.md)`, `[`Bootable`](../../com.agoda.boots/-bootable/index.md)`>`<br>map of bootables added to the system |
| [executor](executor.md) | `open lateinit var executor: `[`Executor`](../../com.agoda.boots/-executor/index.md)<br>instance of [Executor](../../com.agoda.boots/-executor/index.md) used by the system |
| [logger](logger.md) | `open var logger: `[`Logger`](../../com.agoda.boots/-logger/index.md)`?`<br>instance of [Logger](../../com.agoda.boots/-logger/index.md) used by the system |
| [map](map.md) | `val map: `[`MutableMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)`<`[`Key`](../../com.agoda.boots/-key/index.md)`, `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`Key`](../../com.agoda.boots/-key/index.md)`>>`<br>container of boot tasks |
| [tasks](tasks.md) | `val tasks: `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`Key`](../../com.agoda.boots/-key/index.md)`>>`<br>container of boot tasks in FIFO ordering |

### Functions

| Name | Summary |
|---|---|
| [check](check.md) | `fun check(bootable: `[`Bootable`](../../com.agoda.boots/-bootable/index.md)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>To be qualified for boot, bootable should meet following requirements: |
| [count](count.md) | `open fun count(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Amount of bootables, that are awaiting to be invoked by the system. |
| [next](next.md) | `open fun next(finished: `[`Report`](../../com.agoda.boots/-report/index.md)`?): `[`Bootable`](../../com.agoda.boots/-bootable/index.md)`?`<br>Request to retrieve bootable which should be invoked next. It is sequencer's responsibility to prioritize between tasks and verify that all dependencies for chosen bootable has been resolved. |
| [resolve](resolve.md) | `fun resolve(bootables: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Bootable`](../../com.agoda.boots/-bootable/index.md)`>): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`Key`](../../com.agoda.boots/-key/index.md)`>`<br>Builds the boot task based on the list of bootables that has to be booted. Function resolves and adds required dependencies to the provided list. |
| [start](start.md) | `open fun start(key: `[`Key`](../../com.agoda.boots/-key/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Request to prepare and calculate boot sequence (task) for the given key. |
| [update](update.md) | `fun update(report: `[`Report`](../../com.agoda.boots/-report/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Updates the tasks based on the information of incoming report. If report says that some bootable is booted, that bootable is removed from all tasks. If report says that some bootable is failed and if that bootable is [critical](../../com.agoda.boots/-bootable/is-critical.md) then all tasks containing this bootable will be cleared and cancelled (all tasks as of now). |
| [visit](visit.md) | `fun visit(key: `[`Key`](../../com.agoda.boots/-key/index.md)`, visited: `[`MutableMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)`<`[`Key`](../../com.agoda.boots/-key/index.md)`, `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>, list: `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`Key`](../../com.agoda.boots/-key/index.md)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Checks the bootable dependencies in a recursive manner and adds them before adding bootable itself to the task. |
