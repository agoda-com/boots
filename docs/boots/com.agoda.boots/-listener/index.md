[boots](../../index.md) / [com.agoda.boots](../index.md) / [Listener](./index.md)

# Listener

`class Listener`

Callback class to listen results of a boot process.

### Types

| Name | Summary |
|---|---|
| [Builder](-builder/index.md) | `interface Builder`<br>Interface providing Java style means to easily instantiate [listener](./index.md) via anonymous inner classes. |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `Listener()`<br>Callback class to listen results of a boot process. |

### Properties

| Name | Summary |
|---|---|
| [onBoot](on-boot.md) | `var onBoot: (`[`Report`](../-report/index.md)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>function that is invoked when boot process has finished successfully |
| [onFailure](on-failure.md) | `var onFailure: (`[`Report`](../-report/index.md)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>function that is invoked when boot process hasn't finished successfully |
