[core](../../index.md) / [com.agoda.boots](../index.md) / [Status](./index.md)

# Status

`sealed class Status`

Represents the status of a given [bootable](../-bootable/index.md) within the boot system.
Status is returned as a property of [Report](../-report/index.md) data class returned by the
[report()](../-boots/report.md).

### Types

| Name | Summary |
|---|---|
| [Booted](-booted/index.md) | `class Booted : `[`Status`](./index.md)<br>Booted status means that [bootable](../-bootable/index.md) has finished it's [boot()](../-bootable/boot.md) invocation without throwing any exception, thus meaning that it has booted successfully. |
| [Booting](-booting/index.md) | `class Booting : `[`Status`](./index.md)<br>Booting status means that [bootable](../-bootable/index.md) has been added to the [executor](../-executor/index.md)'s queue and will start executing/executing right now. |
| [Failed](-failed/index.md) | `class Failed : `[`Status`](./index.md)<br>Failed status means that [bootable](../-bootable/index.md) threw an exception during [boot()](../-bootable/boot.md) invocation, thus meaning that it hasn't booted successfully. |
| [Idle](-idle/index.md) | `class Idle : `[`Status`](./index.md)<br>Idle status means that [bootable](../-bootable/index.md) hasn't been touched yet by the boot system. All freshly added [bootables](../-bootable/index.md) through [add()](../-boots/add.md) get the idle status automatically. |

### Companion Object Functions

| Name | Summary |
|---|---|
| [booted](booted.md) | `fun booted(): `[`Status`](./index.md)<br>Creates an instance of [Booted](-booted/index.md) status |
| [booting](booting.md) | `fun booting(): `[`Status`](./index.md)<br>Creates an instance of [Booting](-booting/index.md) status |
| [failed](failed.md) | `fun failed(reason: `[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)`): `[`Status`](./index.md)<br>Creates an instance of [Failed](-failed/index.md) status |
| [idle](idle.md) | `fun idle(): `[`Status`](./index.md)<br>Creates an instance of [Idle](-idle/index.md) status |

### Inheritors

| Name | Summary |
|---|---|
| [Booted](-booted/index.md) | `class Booted : `[`Status`](./index.md)<br>Booted status means that [bootable](../-bootable/index.md) has finished it's [boot()](../-bootable/boot.md) invocation without throwing any exception, thus meaning that it has booted successfully. |
| [Booting](-booting/index.md) | `class Booting : `[`Status`](./index.md)<br>Booting status means that [bootable](../-bootable/index.md) has been added to the [executor](../-executor/index.md)'s queue and will start executing/executing right now. |
| [Failed](-failed/index.md) | `class Failed : `[`Status`](./index.md)<br>Failed status means that [bootable](../-bootable/index.md) threw an exception during [boot()](../-bootable/boot.md) invocation, thus meaning that it hasn't booted successfully. |
| [Idle](-idle/index.md) | `class Idle : `[`Status`](./index.md)<br>Idle status means that [bootable](../-bootable/index.md) hasn't been touched yet by the boot system. All freshly added [bootables](../-bootable/index.md) through [add()](../-boots/add.md) get the idle status automatically. |
