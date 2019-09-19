package com.agoda.boots

import com.agoda.boots.Key.Companion.excluding
import com.agoda.boots.Key.Companion.multiple
import com.agoda.boots.Key.Companion.single
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.IllegalArgumentException

@RunWith(MockitoJUnitRunner::class)
class KeyTest {

    @Mock lateinit var reporter: Reporter

    @Before
    fun setup() {
        Boots {
            configure {
                reporter = this@KeyTest.reporter
            }
        }
    }

    @Test
    fun testSingleEquals() {
        // Arrange
        val single = single("1")
        val same = single("1")
        val different = single("2")
        val multiple = multiple(single("1"))

        // Assert
        assert(single == same)
        assert(single != different)
        assert(single != multiple as Key)
    }

    @Test
    fun testMultipleEquals() {
        // Arrange
        val multiple = Key.Multiple(setOf(single("1")))
        val same = multiple(single("1"))
        val different = multiple(single("2"))
        val single = single("1")

        // Assert
        assert(multiple == same)
        assert(multiple != different)
        assert(multiple != single as Key)
    }

    @Test
    fun testMultipleContainsAll() {
        // Arrange
        val multiple = Key.Multiple(setOf(single("1"), single("2")))

        // Assert
        assert(multiple.containsAll(setOf(single("1"), single("2"))))
    }

    @Test
    fun testMultipleToString() {
        // Arrange
        val multiple = Key.Multiple(setOf(single("1"), single("2")))

        // Act
        val text = multiple.toString()

        // Assert
        assert(text == "{1, 2}")
    }

    @Test
    fun testExcludingEquals() {
        // Arrange
        val excluding = Key.Excluding(setOf(single("1")))
        val same = excluding(single("1"))
        val different = excluding(single("2"))
        val single = single("1")

        // Assert
        assert(excluding == same)
        assert(excluding != different)
        assert(excluding != single as Key)
    }

    @Test
    fun testExcludingContainsAll() {
        // Arrange
        val excluding = Key.Excluding(setOf(single("1"), single("2")))

        // Assert
        assert(excluding.containsAll(setOf(single("1"), single("2"))))
    }

    @Test
    fun testExcludingToString() {
        // Arrange
        val excluding = Key.Excluding(setOf(single("1"), single("2")))

        // Act
        val text = excluding.toString()

        // Assert
        assert(text == "{1, 2}")
    }

    @Test
    fun testIsIdle() {
        // Arrange
        val key = single("1")
        whenever(reporter.get(key)).thenReturn(Report(key, Status.booted()))

        // Act
        var isIdle = key.isIdle

        // Assert
        assert(!isIdle)

        // Arrange
        whenever(reporter.get(key)).thenReturn(Report(key, Status.idle()))

        // Act
        isIdle = key.isIdle

        // Assert
        assert(isIdle)
    }

    @Test
    fun testIsBooting() {
        // Arrange
        val key = single("1")
        whenever(reporter.get(key)).thenReturn(Report(key, Status.idle()))

        // Act
        var isBooting = key.isBooting

        // Assert
        assert(!isBooting)

        // Arrange
        whenever(reporter.get(key)).thenReturn(Report(key, Status.booting()))

        // Act
        isBooting = key.isBooting

        // Assert
        assert(isBooting)
    }

    @Test
    fun testIsBooted() {
        // Arrange
        val key = single("1")
        whenever(reporter.get(key)).thenReturn(Report(key, Status.booting()))

        // Act
        var isBooted = key.isBooted

        // Assert
        assert(!isBooted)

        // Arrange
        whenever(reporter.get(key)).thenReturn(Report(key, Status.booted()))

        // Act
        isBooted = key.isBooted

        // Assert
        assert(isBooted)
    }

    @Test
    fun testIsFailed() {
        // Arrange
        val key = single("1")
        whenever(reporter.get(key)).thenReturn(Report(key, Status.booted()))

        // Act
        var isFailed = key.isFailed

        // Assert
        assert(!isFailed)

        // Arrange
        whenever(reporter.get(key)).thenReturn(Report(key, Status.failed(IllegalArgumentException())))

        // Act
        isFailed = key.isFailed

        // Assert
        assert(isFailed)
    }

    @Test
    fun testStatus() {

    }

}
