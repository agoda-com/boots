[boots](../../../index.md) / [com.agoda.boots](../../index.md) / [Listener](../index.md) / [Builder](./index.md)

# Builder

`interface Builder`

Interface providing Java style means to easily instantiate [listener](../index.md) via
anonymous inner classes.

### Functions

| Name | Summary |
|---|---|
| [onBoot](on-boot.md) | `abstract fun onBoot(report: `[`Report`](../../-report/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>This function is invoked when boot process has finished successfully. |
| [onFailure](on-failure.md) | `abstract fun onFailure(report: `[`Report`](../../-report/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>This function is invoked when boot process hasn't finished successfully. |
