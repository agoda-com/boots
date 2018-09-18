[logger-android](../../index.md) / [com.agoda.boots.logger](../index.md) / [AndroidLogger](./index.md)

# AndroidLogger

`class AndroidLogger : Logger`

Android implementation of [Logger](#).

Simply translates log calls to Android's [Log](https://developer.android.com/reference/android/util/Log.html).

### Parameters

`level` - minimal log level to output

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `AndroidLogger(level: Level)`<br>Android implementation of [Logger](#). |

### Functions

| Name | Summary |
|---|---|
| [log](log.md) | `fun log(level: Level, message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>`fun log(level: Level, message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, throwable: `[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Companion Object Properties

| Name | Summary |
|---|---|
| [LOG_TAG](-l-o-g_-t-a-g.md) | `const val LOG_TAG: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
