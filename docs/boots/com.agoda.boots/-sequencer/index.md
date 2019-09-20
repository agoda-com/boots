[boots](../../index.md) / [com.agoda.boots](../index.md) / [Sequencer](./index.md)

# Sequencer

`interface Sequencer : `[`Holder`](../-holder/index.md)

Sequencer is responsible for deciding the order of bootable invocation based on the
user's request.

By default, sequencer for every boot request creates a task with [critical](../-bootable/is-critical.md) bootables
put in all cases. Then sequencer provide bootables that are ready to be booted on requests from controller
[object](../-boots/index.md) in the order decided by sequencer.

### Inherited Properties

| Name | Summary |
|---|---|
| [boots](../-holder/boots.md) | `abstract val boots: `[`MutableMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)`<`[`Key`](../-key/index.md)`, `[`Bootable`](../-bootable/index.md)`>`<br>map of bootables added to the system |
| [executor](../-holder/executor.md) | `abstract var executor: `[`Executor`](../-executor/index.md)<br>instance of [Executor](../-executor/index.md) used by the system |
| [logger](../-holder/logger.md) | `abstract var logger: `[`Logger`](../-logger/index.md)`?`<br>instance of [Logger](../-logger/index.md) used by the system |

### Functions

| Name | Summary |
|---|---|
| [count](count.md) | `abstract fun count(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Amount of bootables, that are awaiting to be invoked by the system. |
| [next](next.md) | `abstract fun next(finished: `[`Report`](../-report/index.md)`?): `[`Bootable`](../-bootable/index.md)`?`<br>Request to retrieve bootable which should be invoked next. It is sequencer's responsibility to prioritize between tasks and verify that all dependencies for chosen bootable has been resolved. |
| [start](start.md) | `abstract fun start(key: `[`Key`](../-key/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Request to prepare and calculate boot sequence (task) for the given key. |

### Inherited Functions

| Name | Summary |
|---|---|
| [add](../-holder/add.md) | `open fun add(bootables: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Bootable`](../-bootable/index.md)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Adds bootables to the storage and maps it with corresponding keys for easier access. |
| [all](../-holder/all.md) | `open fun all(): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Bootable`](../-bootable/index.md)`>`<br>Retrieves all available bootables to the system. |
| [critical](../-holder/critical.md) | `open fun critical(): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Bootable`](../-bootable/index.md)`>`<br>Retrieves instances of bootables that have thier [isCritical](../-bootable/is-critical.md) flag set to `true`. |
| [excluding](../-holder/excluding.md) | `open fun excluding(key: `[`Key.Excluding`](../-key/-excluding/index.md)`): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Bootable`](../-bootable/index.md)`>`<br>Retrieves instances of bootables excluding ones in the given excluding key. |
| [multiple](../-holder/multiple.md) | `open fun multiple(key: `[`Key.Multiple`](../-key/-multiple/index.md)`): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Bootable`](../-bootable/index.md)`>`<br>Retrieves instances of bootables that are included in the given multiple key. |

### Inheritors

| Name | Summary |
|---|---|
| [DefaultSequencer](../../com.agoda.boots.impl/-default-sequencer/index.md) | `open class DefaultSequencer : `[`Sequencer`](./index.md)<br>Default implementation of [Sequencer](./index.md). |
