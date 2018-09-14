# Boots
[![Github tag version](https://img.shields.io/github/tag/agoda-com/boots.svg?label=version)](https://bintray.com/agoda/maven/boots)
[![Kotlin version badge](https://img.shields.io/badge/kotlin-1.2.70-blue.svg)](http://kotlinlang.org/)

Lightweight and easy-to-use bootstrap library for Java | Kotlin | Android

## The problem
At Agoda, our mobile app had a huge problem with modifying and executing the initialization logic.
Our `Application` and `SplashActivity` classes were more that 2k lines of undocumented and uncommented code.
Every change in this classes could possibly lead to unexpected behaviour that could not be discovered by unit or ui testing.

Another problem we had is that when our app was restoring after being terminated by the system,
we had to manually stop current screen and launch our `SplashActivity` to ensure that all our
systems can operate normally.

On top of that, all this logic run mostly on the main thread, which made our TTI (time-to-interactable)
as long as 4-6 seconds on a mid-range devices.

We had a strong feeling that we can change this.

#### Goals we want to achieve
- Simplicity
- Speed
- Safety
- Extendability

## Solution
That's how we implemented Boots, the lightweight and easy-to-use bootstrap library which you
can use for all of your projects running Java or Kotlin as well as Android apps.

The library allows you to easily decouple your initialization logic by splitting it
into `Bootable`s, which you can connect through simple dependency declarations.
Then it's up to the library to do the rest for you.

#### Capabilities
- Automatic boot task resolving (dependencies, critical bootables)
- Runtime SCC (strong connected component) check
- Concurrent execution
- Time measurements
- Simple listener callbacks
- Custom component implementations

#### Usage

##### Making a bootable
`Bootable` is the base construction that is used in the library to decouple
your initialization logic. Most of the library's integration process is
defining your bootables so it is very important to understand it's capabilities.
```kotlin
abstract class Bootable {
    abstract val key: Key.Single

    open val dependencies: Key.Multiple = multiple()
    open val isConcurrent: Boolean = true
    open val isCritical: Boolean = false

    @Throws(Throwable::class)
    abstract fun boot()
}
```
Lets go through it's properties and see what they mean:

- **key** is a unique identifier of your bootable (string id)
- **dependencies** is a key that contains keys of bootables that your bootable depend on
- **isConcurrent** is a flag that determines whether your bootable can be executed on a separate thread
- **isCritical** is a flag that determines whether your bootable should be executed before any other non-critical.
                 If any critical bootable fails to load, all tasks will be stopped.
- **boot()** is a function of your bootable that gets invoked by the library.

Now that you know what bootable consist of, you can create your own one.
But before that, you need to define a key:
```kotlin
object Keys {
    @JvmField val DEVICE_ID = single("device_id")
}
```
Keys were introduced to remove the necessity of having references on actual
bootables when working with a library (especially in a modularized project).
String could work as well, but keys are more flexible since they allow us
to combine multiple identifiers as well as define filtering keys (`Critical` and `All`).

Now it's time to define a bootable:
```kotlin
class DeviceIdBootable(private val context: Context) : Bootable() {
    override val key = Keys.DEVICE_ID
    override val isCritical = true

    override fun boot() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)

        if (!preferences.contains("device_id")) {
            preferences
                    .edit()
                    .putString("device_id", UUID.randomUUID().toString())
                    .apply()
        }
    }
}
```
Or you can use Kotlin DSL to define and add bootable at the same time:
```kotlin
Boots {
    add(Keys.DO_SOMETHING, isCritical = true) {
        doSomething()
    }
}
```
So, you're ready to launch your boot sequence. You can do it using simple Kotlin DSL,
or plain old Java way:
```kotlin
Boots {
    // Adding instance of bootable to the system
    add(DeviceIdBootable(applicationContext))

    // Requesting to boot all bootables and saving listener instance
    listener = boot(all()) {
        onBoot = { /* do something */ }
    }
}
```
```java
Boots.add(new DeviceIdBootable(getApplicationContext()));

listener = Boots.boot(all(), new Builder() {
    @Override
    public void onBoot(@NotNull Report report) {}

    @Override
    public void onFailure(@NotNull Report report) {}
});
```

##### Checking state and observing
To get the current status of your bootable or list of bootables, you can generate the report:
```kotlin
Boots {
    val report = report(all())
}
```
```java
Report report = Boots.report(all());
```
Report contains current status of bootable or common status of list of bootables and
time measurement information if the bootable has finished booting or failed.

Also you can listen for bootable events with the `subscribe()` functions:
```kotlin
Boots {
    if (report(all()).status !is Booted) {
        listener = subscribe(all()) {
            onBoot = { /* do something */ }
            onFailure = { /* do something */ }
        }
    }
}
```
```java
if (!(Boots.report(all()) instanceof Booted)) {
    listener = Boots.subscribe(all(), new Builder() {
        @Override
        public void onBoot(@NotNull Report report) {}

        @Override
        public void onFailure(@NotNull Report report) {}
    });
}
```
**IMPORTANT**: default implementation of `Notifier` holds strong references
on the `Listener` instances. It is user's responsibility to unsubscribe if
the listener holds a reference on outer objects that need to be recycled.
Holding weak references is not the best solution, since GC time is undetermined
and invoking weak referenced listeners may lead to unexpected behaviour
(`IllegalStateException`s in fragments, etc.).

##### Customization
Library provides ability to customize it's behaviour through providing
custom implementation of component interfaces. The components are:

- **Executor**
- **Notifier**
- **Reporter**
- **Sequencer**
- **Logger**

Each of them is responsible for a specific portion of library's behaviour and
can be overridden with custom logic. Let's take `RxAndroidExecutor`, for example,
which uses RxJava under the hood to execute your bootables and also enables
context switching, so that all callbacks and non-concurrent bootables can
be executed on Android's main thread:
```kotlin
Boots {
    configure {
        executor = RxAndroidExecutor(Schedulers.io())
    }
}
```
```java
Boots.configure(new Configuration.Builder()
        .setExecutor(new RxAndroidExecutor(Schedulers.io()))
        .build());
```
All custom implementations are provided via additional artifacts, so that
you can grab only core library with default implementation if that is fully
satisfies your needs. The list of available artifacts is at the **Setup** section.

##### More
For any additional information please refer to library's documentation.

#### Testing
Library provides a testing artifact to help you easily mock your boot process.
`mockito` and `mockito-kotlin` are used to provide you with this functionality.
To mock library's logic, you can use Kotlin DSL or Java style:
```kotlin
Mocker {
    mock(Keys.DEVICE_ID, booting())
}
```
```java
new Mocker()
        .mock(Keys.DEVICE_ID, booting())
        .apply();
```
When you use `Mocker`, it automatically mocks all interactions as `Booted` by default
and immediately triggers all added listeners.

#### Setup
Maven
```xml
<!-- core library !-->
<dependency>
  <groupId>com.agoda.boots</groupId>
  <artifactId>core</artifactId>
  <version>0.0.7</version>
  <type>pom</type>
</dependency>

<!-- android logger !-->
<dependency>
  <groupId>com.agoda.boots</groupId>
  <artifactId>logger-android</artifactId>
  <version>0.0.7</version>
  <type>pom</type>
</dependency>

<!-- rx android executor !-->
<dependency>
  <groupId>com.agoda.boots</groupId>
  <artifactId>executor-rx-android</artifactId>
  <version>0.0.7</version>
  <type>pom</type>
</dependency>

<!-- test mocker !-->
<dependency>
  <groupId>com.agoda.boots</groupId>
  <artifactId>test</artifactId>
  <version>0.0.7</version>
  <type>pom</type>
</dependency>
```
or Gradle:
```groovy
repositories {
    jcenter()
}

dependencies {
    // core library
    implementation 'com.agoda.boots:core:0.0.7'

    // android logger
    implementation 'com.agoda.boots:logger-android:0.0.7'

    // rx android executor
    implementation 'com.agoda.boots:executor-rx-android:0.0.7'

    // test mocker
    implementation 'com.agoda.boots:test:0.0.7'
}
```

#### Contribution Policy

Boots is an open source project, and depends on its users to improve it. We are more than happy
to find you interested in taking the project forward.

Kindly refer to the [Contribution Guidelines](https://github.com/agoda-com/boots/blob/master/CONTRIBUTING.md) for detailed information.

#### Code of Conduct

Please refer to [Code of Conduct](https://github.com/agoda-com/boots/blob/master/CODE_OF_CONDUCT.md) document.

#### License

Boots is available under the [Apache License, Version 2.0](https://github.com/agoda-com/boots/blob/master/LICENSE).

#### Thanks to

* [Unlimity](https://github.com/unlimity) - **Ilya Lim**
* [Сdsap](https://github.com/cdsap) - **Inaki Villar**
