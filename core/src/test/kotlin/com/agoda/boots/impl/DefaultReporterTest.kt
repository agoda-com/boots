package com.agoda.boots.impl

import com.agoda.boots.*
import com.agoda.boots.Key.Companion.all
import com.agoda.boots.Key.Companion.critical
import com.agoda.boots.Key.Companion.multiple
import com.agoda.boots.Status.Booted
import com.agoda.boots.Status.Companion.booted
import com.agoda.boots.Status.Companion.failed
import com.agoda.boots.Status.Failed
import org.junit.Test

class DefaultReporterTest {

    private val reporter = DefaultReporter()

    @Test
    fun testReport() {
        // Arrange
        val single1 = Key.single("key 1")
        val single2 = Key.single("key 2")
        val single3 = Key.single("key 3")

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

        reporter.add(listOf(bootable1, bootable2, bootable3))

        // Act
        reporter.set(single1, booted(), 0, 100)
        reporter.set(single2, booted(), 50, 100)
        reporter.set(single3, failed(Throwable()), 70, 100)

        val multiple1 = reporter.get(multiple(single1, single2))
        val multiple2 = reporter.get(multiple(single2, single3))
        val critical = reporter.get(critical())
        val all = reporter.get(all())

        // Assert
        assert(multiple1.status is Booted)
        assert(multiple1.start == 0L)
        assert(multiple1.time == 150L)

        assert(multiple2.status is Failed)
        assert(multiple2.start == 50L)
        assert(multiple2.time == 120L)

        assert(critical.status is Booted)
        assert(critical.start == 0L)
        assert(critical.time == 100L)

        assert(all.status is Failed)
        assert(all.start == 0L)
        assert(all.time == 170L)
    }

}
