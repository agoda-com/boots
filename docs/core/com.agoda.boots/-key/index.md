[core](../../index.md) / [com.agoda.boots](../index.md) / [Key](./index.md)

# Key

`sealed class Key`

This class is used to distinct [bootables](../-bootable/index.md) in the boot system.
Primary goal of a separate class like that is to avoid the need of having
direct access to [bootable](../-bootable/index.md) instances, instead allowing it to be provided/injected
by any other means while operating with [keys](./index.md), which are lightweight and easy to declare.

### Types

| Name | Summary |
|---|---|
| [All](-all/index.md) | `class All : `[`Key`](./index.md)<br>All key. Used to select all available [bootables](../-bootable/index.md) in the system. |
| [Critical](-critical/index.md) | `class Critical : `[`Key`](./index.md)<br>Critical key. Used to select all [bootables](../-bootable/index.md) that have their [isCritical](../-bootable/is-critical.md) flag set to `true` |
| [Excluding](-excluding/index.md) | `class Excluding : `[`Key`](./index.md)`, `[`Set`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)`<`[`Single`](-single/index.md)`>`<br>Excluding key. Marks all available keys except given set of [single](-single/index.md) keys. |
| [Multiple](-multiple/index.md) | `class Multiple : `[`Key`](./index.md)`, `[`Set`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)`<`[`Single`](-single/index.md)`>`<br>Multiple key. Marks a set of [single](-single/index.md) keys. |
| [Single](-single/index.md) | `class Single : `[`Key`](./index.md)<br>Single key. Marks specific [bootable](../-bootable/index.md) in the system. |

### Companion Object Functions

| Name | Summary |
|---|---|
| [all](all.md) | `fun all(): `[`All`](-all/index.md)<br>Creates an instance of [All](-all/index.md) key. |
| [critical](critical.md) | `fun critical(): `[`Critical`](-critical/index.md)<br>Creates an instance of [Critical](-critical/index.md) key. |
| [excluding](excluding.md) | `fun excluding(vararg keys: `[`Single`](-single/index.md)` = emptyArray()): `[`Excluding`](-excluding/index.md)<br>Creates an instance of [Excluding](-excluding/index.md) key. |
| [multiple](multiple.md) | `fun multiple(vararg keys: `[`Single`](-single/index.md)` = emptyArray()): `[`Multiple`](-multiple/index.md)<br>Creates an instance of [Multiple](-multiple/index.md) key. |
| [single](single.md) | `fun single(id: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = ""): `[`Single`](-single/index.md)<br>Creates an instance of [Single](-single/index.md) key. |

### Inheritors

| Name | Summary |
|---|---|
| [All](-all/index.md) | `class All : `[`Key`](./index.md)<br>All key. Used to select all available [bootables](../-bootable/index.md) in the system. |
| [Critical](-critical/index.md) | `class Critical : `[`Key`](./index.md)<br>Critical key. Used to select all [bootables](../-bootable/index.md) that have their [isCritical](../-bootable/is-critical.md) flag set to `true` |
| [Excluding](-excluding/index.md) | `class Excluding : `[`Key`](./index.md)`, `[`Set`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)`<`[`Single`](-single/index.md)`>`<br>Excluding key. Marks all available keys except given set of [single](-single/index.md) keys. |
| [Multiple](-multiple/index.md) | `class Multiple : `[`Key`](./index.md)`, `[`Set`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)`<`[`Single`](-single/index.md)`>`<br>Multiple key. Marks a set of [single](-single/index.md) keys. |
| [Single](-single/index.md) | `class Single : `[`Key`](./index.md)<br>Single key. Marks specific [bootable](../-bootable/index.md) in the system. |
