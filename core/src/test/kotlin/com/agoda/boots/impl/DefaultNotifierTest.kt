package com.agoda.boots.impl

import com.agoda.boots.*
import com.agoda.boots.Key.Companion.all
import com.agoda.boots.Key.Companion.critical
import com.agoda.boots.Key.Companion.multiple
import com.agoda.boots.Key.Companion.single
import com.agoda.boots.Status.Companion.booted
import com.agoda.boots.Status.Companion.failed
import com.agoda.boots.Status.Companion.idle
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultNotifierTest {

    private val notifier = DefaultNotifier()

    @Mock lateinit var reporter: Reporter
    @Mock lateinit var executor: Executor
    @Mock lateinit var singleListener: (Report) -> Unit
    @Mock lateinit var multipleListener: (Report) -> Unit
    @Mock lateinit var criticalListener: (Report) -> Unit
    @Mock lateinit var allListener: (Report) -> Unit

    @Before
    fun setup() {
        Boots { configure { reporter = this@DefaultNotifierTest.reporter } }

        whenever(reporter.get(any())).thenReturn(Report(single("default"), idle()))
        whenever(executor.isMainThreadSupported).thenReturn(false)

        notifier.executor = executor
    }

    @Test
    fun testNotify() {
        // Arrange
        val single1 = single("key 1")
        val single2 = single("key 2")
        val single3 = single("key 3")

        val bootable1 = object : Bootable() {
            override val key = single1
            override val isCritical = true
            override fun boot() {}
        }

        val bootable2 = object : Bootable() {
            override val key = single2
            override fun boot() {}
        }

        val bootable3 = object : Bootable() {
            override val key = single3
            override fun boot() {}
        }

        val multiple = multiple(single2, single3)

        with(notifier) {
            add(listOf(bootable1, bootable2, bootable3))
            add(single1, Listener().apply { onBoot = singleListener })
            add(multiple, Listener().apply { onFailure = multipleListener })
            add(Key.critical(), Listener().apply { onBoot = criticalListener })
            add(Key.all(), Listener().apply { onFailure = allListener })
        }

        whenever(reporter.get(critical())).thenReturn(Report(critical(), booted()))

        // Act
        notifier.notify(single1, Report(single1, booted()))

        // Assert
        verify(singleListener).invoke(any())
        verify(criticalListener).invoke(any())
        verifyZeroInteractions(multipleListener)
        verifyZeroInteractions(allListener)

        // Act
        notifier.notify(single2, Report(single2, booted()))

        // Assert
        verifyZeroInteractions(multipleListener)
        verifyZeroInteractions(allListener)

        // Arrange
        whenever(reporter.get(multiple)).thenReturn(Report(multiple, failed(Throwable())))
        whenever(reporter.get(all())).thenReturn(Report(all(), failed(Throwable())))

        // Act
        notifier.notify(single3, Report(single3, failed(Throwable())))

        // Assert
        verify(multipleListener).invoke(any())
        verify(allListener).invoke(any())
    }

    @After
    fun cleanup() {
        Boots { reset() }
    }

}
