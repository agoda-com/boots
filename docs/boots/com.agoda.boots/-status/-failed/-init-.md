[boots](../../../index.md) / [com.agoda.boots](../../index.md) / [Status](../index.md) / [Failed](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`Failed(reason: `[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)`)`

Failed status means that [bootable](../../-bootable/index.md) threw an exception during [boot()](../../-bootable/boot.md)
invocation, thus meaning that it hasn't booted successfully.

### Parameters

`reason` - exception that the [boot()](../../-bootable/boot.md) function has thrown.