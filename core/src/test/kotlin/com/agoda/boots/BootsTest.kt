package com.agoda.boots

import com.agoda.boots.Key.Companion.all
import com.agoda.boots.Key.Companion.single
import com.agoda.boots.Status.Booted
import com.agoda.boots.impl.DefaultNotifier
import com.agoda.boots.impl.DefaultReporter
import com.agoda.boots.impl.DefaultSequencer
import com.nhaarman.mockito_kotlin.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BootsTest {

    @Mock lateinit var executor: Executor
    @Mock lateinit var notifier: Notifier
    @Mock lateinit var reporter: Reporter
    @Mock lateinit var sequencer: Sequencer
    @Mock lateinit var listener: (Report) -> Unit

    @Before
    fun setup() {
        Boots {
            configure {
                executor = this@BootsTest.executor
                notifier = this@BootsTest.notifier
                reporter = this@BootsTest.reporter
                sequencer = this@BootsTest.sequencer
            }
        }

        whenever(executor.capacity).thenReturn(2)
    }

    @Test(expected = StrongConnectedBootException::class)
    fun testVerifySccFailure() {
        // Arrange
        val bootable1 = object : Bootable() {
            override val key = Key.single("Key 1")
            override val dependencies = Key.multiple(Key.single("Key 2"))
            override fun boot() {}
        }

        val bootable2 = object : Bootable() {
            override val key = Key.single("Key 2")
            override val dependencies = Key.multiple(Key.single("Key 1"))
            override fun boot() {}
        }

        // Act
        Boots.add(listOf(bootable1, bootable2))
    }

    @Test(expected = IncorrectConnectedBootException::class)
    fun testVerifyIccFailure() {
        // Arrange
        val bootable1 = object : Bootable() {
            override val key = Key.single("Key 1")
            override fun boot() {}
        }

        val bootable2 = object : Bootable() {
            override val key = Key.single("Key 2")
            override val dependencies = Key.multiple(Key.single("Key 1"))
            override val isCritical = true
            override fun boot() {}
        }

        // Act
        Boots.add(listOf(bootable1, bootable2))
    }

    @Test
    fun testAdd() {
        // Arrange
        val bootable1 = object : Bootable() {
            override val key = Key.single("Key 1")
            override fun boot() {}
        }

        val bootable2 = object : Bootable() {
            override val key = Key.single("Key 2")
            override fun boot() {}
        }

        val list = listOf(bootable1, bootable2)

        // Act
        Boots.add(list)

        // Assert
        verify(notifier).add(list)
        verify(reporter).add(list)
        verify(sequencer).add(list)
    }

    @Test
    fun testObserve() {
        // Arrange
        val key = single("Key 1")
        val listener = Listener()

        // Act
        Boots.observe(key, listener)

        // Assert
        verify(notifier).add(key, listener)
    }

    @Test
    fun testObserveDSL() {
        // Arrange
        val key = single("Key 1")

        // Act
        Boots.observe(key) {}

        // Assert
        verify(notifier).add(eq(key), any())
    }

    @Test
    fun testReport() {
        // Arrange
        val key = single("Key 1")

        // Act
        Boots.report(key)

        // Assert
        verify(reporter).get(key)
    }

    @Test
    fun testBoot() {
        // Arrange
        Boots {
            configure {
                notifier = DefaultNotifier()
                reporter = DefaultReporter()
                sequencer = DefaultSequencer()
            }
        }

        val key1 = single("Key 1")
        val key2 = single("Key 2")
        val key3 = single("Key 3")
        val key4 = single("Key 4")

        val bootable1 = object : Bootable() {
            override val key = key1
            override val isCritical = true
            override fun boot() {}
        }

        val bootable2 = object : Bootable() {
            override val key = key2
            override fun boot() {}
        }

        val bootable3 = object : Bootable() {
            override val key = key3
            override val dependencies = Key.multiple(key2)
            override fun boot() {}
        }

        val bootable4 = object : Bootable() {
            override val key = key4
            override fun boot() {}
        }

        Boots.add(listOf(bootable1, bootable2, bootable3, bootable4))

        whenever(executor.execute(any(), any())).thenAnswer {
            (it.arguments[1] as () -> Unit).invoke()
        }

        // Act
        Boots.boot(key3, Listener().apply { onBoot = listener })
        Boots.boot(key4, Listener().apply { onBoot = listener })

        // Assert
        verify(executor, times(4)).execute(eq(true), any())
        verify(listener, times(2)).invoke(any())

        val report = Boots.report(all())
        assert(report.status is Booted)
    }

    @After
    fun cleanup() {
        Boots { reset() }
    }

}
