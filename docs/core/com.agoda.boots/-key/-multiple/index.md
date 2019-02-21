[core](../../../index.md) / [com.agoda.boots](../../index.md) / [Key](../index.md) / [Multiple](./index.md)

# Multiple

`class Multiple : `[`Key`](../index.md)`, `[`Set`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)`<`[`Single`](../-single/index.md)`>`

Multiple key. Marks a set of [single](../-single/index.md) keys.

### Parameters

`keys` - set of [single](../-single/index.md) keys

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `Multiple(keys: `[`Set`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)`<`[`Single`](../-single/index.md)`>)`<br>Multiple key. Marks a set of [single](../-single/index.md) keys. |

### Properties

| Name | Summary |
|---|---|
| [size](size.md) | `val size: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

### Inherited Properties

| Name | Summary |
|---|---|
| [isBooted](../is-booted.md) | `val isBooted: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Property that is `true` when all of this key's matching [bootables](../../-bootable/index.md) are in the [booted](../../-status/-booted/index.md) state. |
| [isBooting](../is-booting.md) | `val isBooting: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Property that is `true` when any of this key's matching [bootables](../../-bootable/index.md) are in the [booting](../../-status/-booting/index.md) state. |
| [isFailed](../is-failed.md) | `val isFailed: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Property that is `true` when any of this key's matching [bootables](../../-bootable/index.md) are in the [failed](../../-status/-failed/index.md) state. |
| [isIdle](../is-idle.md) | `val isIdle: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Property that is `true` when all of this key's matching [bootables](../../-bootable/index.md) are in the [idle](../../-status/-idle/index.md) state. |
| [status](../status.md) | `val status: `[`Status`](../../-status/index.md)<br>Property that contains current general [status](../../-status/index.md) of all of this key's matching [bootables](../../-bootable/index.md). |

### Functions

| Name | Summary |
|---|---|
| [contains](contains.md) | `fun contains(element: `[`Single`](../-single/index.md)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [containsAll](contains-all.md) | `fun containsAll(elements: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`Single`](../-single/index.md)`>): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [equals](equals.md) | `fun equals(other: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](hash-code.md) | `fun hashCode(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [isEmpty](is-empty.md) | `fun isEmpty(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isNotEmpty](is-not-empty.md) | `fun isNotEmpty(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Checks if this key is not empty |
| [iterator](iterator.md) | `fun iterator(): `[`Iterator`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterator/index.html)`<`[`Single`](../-single/index.md)`>` |
| [toString](to-string.md) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
