package com.agoda.boots.strict

import com.agoda.boots.Bootable
import com.agoda.boots.Key.Companion.multiple
import com.agoda.boots.Key.Companion.single
import org.junit.Test

class SccFinderTest {

    val bootable1 = object : Bootable() {
        override val key = single("Bootable 1")
        override fun boot() {}
    }

    val bootable2 = object : Bootable() {
        override val key = single("Bootable 2")
        override val dependencies = multiple(single("Bootable 1"))
        override fun boot() {}
    }

    val bootable3 = object : Bootable() {
        override val key = single("Bootable 3")
        override val dependencies = multiple(single("Bootable 2"))
        override fun boot() {}
    }

    @Test
    fun testHasScc() {
        // Arrange
        val bootable4 = object : Bootable() {
            override val key = single("Bootable 4")
            override val dependencies = multiple(single("Bootable 2"), single("Bootable 6"))
            override fun boot() {}
        }

        val bootable5 = object : Bootable() {
            override val key = single("Bootable 5")
            override val dependencies = multiple(single("Bootable 4"), single("Bootable 1"))
            override fun boot() {}
        }

        val bootable6 = object : Bootable() {
            override val key = single("Bootable 6")
            override val dependencies = multiple(single("Bootable 5"))
            override fun boot() {}
        }

        // Act
        val results = SccFinder(setOf(bootable1, bootable2, bootable3, bootable4, bootable5, bootable6))
                .find()

        // Assert
        assert(results.isNotEmpty())
        assert(results.first().size == 3)
    }

    @Test
    fun testHasNoScc() {
        // Arrange
        val bootable4 = object : Bootable() {
            override val key = single("Bootable 4")
            override val dependencies = multiple(single("Bootable 2"), single("Bootable 6"))
            override fun boot() {}
        }

        val bootable5 = object : Bootable() {
            override val key = single("Bootable 5")
            override val dependencies = multiple(single("Bootable 4"), single("Bootable 1"))
            override fun boot() {}
        }

        val bootable6 = object : Bootable() {
            override val key = single("Bootable 6")
            override fun boot() {}
        }

        // Act
        val results = SccFinder(setOf(bootable1, bootable2, bootable3, bootable4, bootable5, bootable6))
                .find()

        // Assert
        assert(results.isEmpty())
    }

}
