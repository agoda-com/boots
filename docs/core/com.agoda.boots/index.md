[core](../index.md) / [com.agoda.boots](./index.md)

## Package com.agoda.boots

### Types

| Name | Summary |
|---|---|
| [Bootable](-bootable/index.md) | `abstract class Bootable`<br>Main boot component. It is a piece of code that executes any logic required to boot the system properly. |
| [Boots](-boots/index.md) | `object Boots`<br>Main controller object. Used to interact with the library. |
| [Configuration](-configuration/index.md) | `class Configuration`<br>Configuration is used to provide a custom component to the [Boots](-boots/index.md). By default, all fields are null. When you provide a configuration with some actual values to [configure()](-boots/configure.md), only non-null will be used to replace current ones. |
| [Executor](-executor/index.md) | `interface Executor`<br>Executor is responsible for schedule, executions and switching thread context (not necessary) for invocation of [boot()](-bootable/boot.md) function. |
| [Holder](-holder/index.md) | `interface Holder`<br>Holder interface is used to propagate all components with bootable instances provided to the system as well as providing access to [Executor](-executor/index.md) and [Logger](-logger/index.md) without exposing them through controller [object](-boots/index.md) and make components invoke it in cycle manner. |
| [Key](-key/index.md) | `sealed class Key`<br>This class is used to distinct [bootables](-bootable/index.md) in the boot system. Primary goal of a separate class like that is to avoid the need of having direct access to [bootable](-bootable/index.md) instances, instead allowing it to be provided/injected by any other means while operating with [keys](-key/index.md), which are lightweight and easy to declare. |
| [Listener](-listener/index.md) | `class Listener`<br>Callback class to listen results of a boot process. |
| [Logger](-logger/index.md) | `interface Logger`<br>Simple logger interface. There is no default implementation provided with the core module. |
| [Notifier](-notifier/index.md) | `interface Notifier : `[`Holder`](-holder/index.md)<br>Notifier is responsible for managing listeners, invoking them at appropriate moments and switch context (if possible). |
| [Report](-report/index.md) | `data class Report`<br>Report provides all available information on a single [bootable](-bootable/index.md), or a combination of them. |
| [Reporter](-reporter/index.md) | `interface Reporter : `[`Holder`](-holder/index.md)<br>Reported is responsible for storing, retrieving and generating [reports](-report/index.md). |
| [Sequencer](-sequencer/index.md) | `interface Sequencer : `[`Holder`](-holder/index.md)<br>Sequencer is responsible for deciding the order of bootable invocation based on the user's request. |
| [Status](-status/index.md) | `sealed class Status`<br>Represents the status of a given [bootable](-bootable/index.md) within the boot system. Status is returned as a property of [Report](-report/index.md) data class returned by the [report()](-boots/report.md). |

### Exceptions

| Name | Summary |
|---|---|
| [BootException](-boot-exception/index.md) | `class BootException : `[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)<br>Exception that aggregates the reasons of failure in case a [report](-report/index.md) has a [failed](-status/-failed/index.md) state and is not with [single](-key/-single/index.md) key. |
| [IncorrectConnectedBootException](-incorrect-connected-boot-exception/index.md) | `class IncorrectConnectedBootException : `[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)<br>Exception that indicates that incorrect connected components were found in the [bootables](-bootable/index.md) graph. |
| [StrongConnectedBootException](-strong-connected-boot-exception/index.md) | `class StrongConnectedBootException : `[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)<br>Exception that indicates that strong connected components were found in the [bootables](-bootable/index.md) graph. |
