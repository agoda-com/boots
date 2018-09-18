[core](../../index.md) / [com.agoda.boots](../index.md) / [Configuration](./index.md)

# Configuration

`class Configuration`

Configuration is used to provide a custom component to the [Boots](../-boots/index.md).
By default, all fields are null. When you provide a configuration with
some actual values to [configure()](../-boots/configure.md), only non-null
will be used to replace current ones.

### Types

| Name | Summary |
|---|---|
| [Builder](-builder/index.md) | `class Builder`<br>Builder is presented to support Java style configuration. |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `Configuration()`<br>Configuration is used to provide a custom component to the [Boots](../-boots/index.md). By default, all fields are null. When you provide a configuration with some actual values to [configure()](../-boots/configure.md), only non-null will be used to replace current ones. |

### Properties

| Name | Summary |
|---|---|
| [executor](executor.md) | `var executor: `[`Executor`](../-executor/index.md)`?`<br>instance of [Executor](../-executor/index.md) |
| [isStrictMode](is-strict-mode.md) | `var isStrictMode: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`?`<br>Library throws exception when ICC (incorrect connected components) are added     if this flag is set to `true`. Default is `true`. |
| [logger](logger.md) | `var logger: `[`Logger`](../-logger/index.md)`?`<br>instance of [Logger](../-logger/index.md) |
| [notifier](notifier.md) | `var notifier: `[`Notifier`](../-notifier/index.md)`?`<br>instance of [Notifier](../-notifier/index.md) |
| [reporter](reporter.md) | `var reporter: `[`Reporter`](../-reporter/index.md)`?`<br>instance of [Reporter](../-reporter/index.md) |
| [sequencer](sequencer.md) | `var sequencer: `[`Sequencer`](../-sequencer/index.md)`?`<br>instance of [Sequencer](../-sequencer/index.md) |
