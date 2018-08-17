package com.agoda.boots.strict

import com.agoda.boots.Bootable
import com.agoda.boots.Key.Companion.multiple
import com.agoda.boots.Key.Companion.single
import org.junit.Test

class IccFinderTest {

    @Test
    fun testHasIcc() {
        // Arrange
        val bootable1 = object : Bootable() {
            override val key = single("Bootable 1")
            override fun boot() {}
        }

        val bootable2 = object : Bootable() {
            override val key = single("Bootable 2")
            override val dependencies = multiple(single("Bootable 1"))
            override val isCritical = true
            override fun boot() {}
        }

        // Act
        val results = IccFinder(setOf(bootable1, bootable2)).find()

        // Assert
        assert(results.isNotEmpty())
        assert(results.first().first == single("Bootable 2"))
        assert(results.first().second == single("Bootable 1"))
    }

    @Test
    fun testHasNoIcc() {
        // Arrange
        val bootable1 = object : Bootable() {
            override val key = single("Bootable 1")
            override fun boot() {}
        }

        val bootable2 = object : Bootable() {
            override val key = single("Bootable 2")
            override val dependencies = multiple(single("Bootable 1"))
            override fun boot() {}
        }

        // Act
        val results = IccFinder(setOf(bootable1, bootable2)).find()

        // Assert
        assert(results.isEmpty())
    }

}
