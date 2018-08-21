package com.agoda.boots

import com.agoda.boots.Key.Companion.single
import org.junit.Test

class ErrorsTest {

    @Test
    fun testBootException() {
        // Arrange
        val exception = BootException(mapOf(single("Component 1") to Throwable("Message")))

        // Act
        val message = exception.message

        // Assert
        assert(message == "Problems were encountered while booting sequence!\n"
                + "Component 1 -> Message\n")
    }

    @Test
    fun testStrongConnectedBootException() {
        // Arrange
        val exception = StrongConnectedBootException(listOf(listOf(single("Component 1"), single("Component 2"))))

        // Act
        val message = exception.message

        // Assert
        assert(message == "Strong connected components are not allowed!\n"
                + "Components: Component 1 -> Component 2\n")
    }

    @Test
    fun testIncorrectConnectedBootException() {
        // Arrange
        val exception = IncorrectConnectedBootException(listOf(single("Component 1") to single("Component 2")))

        // Act
        val message = exception.message

        // Assert
        assert(message == "Incorrect connected components are not allowed!\n"
                + "Components: Component 1 -> Component 2\n")
    }

}
