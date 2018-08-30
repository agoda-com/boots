package com.agoda.boots.impl

import com.agoda.boots.*
import com.agoda.boots.Key.Companion.critical
import com.agoda.boots.Key.Companion.multiple
import com.agoda.boots.Key.Companion.single
import com.agoda.boots.Status.Companion.booted
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultSequencerTest {

    @Mock lateinit var reporter: Reporter

    private val sequencer = DefaultSequencer()

    @Before
    fun setup() {
        Boots { configure { reporter = this@DefaultSequencerTest.reporter } }

        whenever(reporter.get(any())).thenReturn(Report(single("default"), Status.idle()))
    }

    @Test
    fun testSequence() {
        // Arrange
        val bootable1 = object : Bootable() {
            override val key = single("Key 1")
            override val isCritical = true
            override fun boot() {}
        }

        val bootable2 = object : Bootable() {
            override val key = single("Key 2")
            override fun boot() {}
        }

        val bootable3 = object : Bootable() {
            override val key = single("Key 3")
            override val dependencies = multiple(single("Key 2"))
            override fun boot() {}
        }

        val bootable4 = object : Bootable() {
            override val key = single("Key 4")
            override fun boot() {}
        }

        Boots.add(listOf(bootable1, bootable2, bootable3, bootable4))
        sequencer.add(listOf(bootable1, bootable2, bootable3, bootable4))

        // Act
        sequencer.start(single("Key 3"))
        sequencer.start(single("Key 4"))

        val boot1 = sequencer.next(null)
        val boot2 = sequencer.next(null)

        whenever(reporter.get(critical())).thenReturn(Report(critical(), booted()))

        val boot3 = sequencer.next(Report(boot1!!.key, booted()))
        val boot4 = sequencer.next(null)

        whenever(reporter.get(bootable3.dependencies)).thenReturn(Report(bootable3.dependencies, booted()))

        val boot5 = sequencer.next(Report(boot3!!.key, booted()))

        // Assert
        assert(boot1.key == single("Key 1"))
        assert(boot2!!.key == single("Key 1"))
        assert(boot3.key == single("Key 2"))
        assert(boot4!!.key == single("Key 4"))
        assert(boot5!!.key == single("Key 3"))
        assert(sequencer.count() == 0)
    }

    @After
    fun cleanup() {
        Boots { reset() }
    }

}
