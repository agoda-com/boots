[boots](../../../index.md) / [com.agoda.boots](../../index.md) / [Status](../index.md) / [Failed](./index.md)

# Failed

`class Failed : `[`Status`](../index.md)

Failed status means that [bootable](../../-bootable/index.md) threw an exception during [boot()](../../-bootable/boot.md)
invocation, thus meaning that it hasn't booted successfully.

### Parameters

`reason` - exception that the [boot()](../../-bootable/boot.md) function has thrown.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `Failed(reason: `[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)`)`<br>Failed status means that [bootable](../../-bootable/index.md) threw an exception during [boot()](../../-bootable/boot.md) invocation, thus meaning that it hasn't booted successfully. |

### Properties

| Name | Summary |
|---|---|
| [reason](reason.md) | `val reason: `[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)<br>exception that the [boot()](../../-bootable/boot.md) function has thrown. |
