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
    @Mock lateinit var singleListener: Listener
    @Mock lateinit var multipleListener: Listener
    @Mock lateinit var criticalListener: Listener
    @Mock lateinit var allListener: Listener

    @Before
    fun setup() {
        Boots.reporter = reporter
        whenever(reporter.get(any())).thenReturn(Report(single("default"), idle()))
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
            override val isCritical = true
            override fun boot() {}
        }

        val bootable3 = object : Bootable() {
            override val key = single3
            override val isCritical = true
            override fun boot() {}
        }

        val multiple = multiple(single2, single3)

        with(notifier) {
            add(listOf(bootable1, bootable2, bootable3))
            add(single1, singleListener)
            add(multiple, multipleListener)
            add(Key.critical(), criticalListener)
            add(Key.all(), allListener)
        }

        whenever(reporter.get(critical())).thenReturn(Report(critical(), booted()))

        // Act
        notifier.notify(single1, Report(single1, booted()))

        // Assert
        verify(singleListener).onBoot(any())
        verify(criticalListener).onBoot(any())
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
        verify(multipleListener).onFailure(any())
        verify(allListener).onFailure(any())
    }

    @After
    fun cleanup() {
        Boots.reporter = DefaultReporter()
    }

}
