package com.agoda.boots

import com.agoda.boots.Status.Companion.booted
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever

/**
 * This class provides easy-to-use mocking framework through Mockito library.
 *
 * By default, any [boot][Boots.boot] request or [subscription][Boots.subscribe] will immediately invoke
 * provided listener calling [onBoot][Listener.onBoot].
 *
 * By default, any [report][Boots.report] will return report with [Booted][Status.Booted] status and 0 execution time.
 */
class Mocker @JvmOverloads constructor(tail: Mocker.() -> Unit = {}) {

    private val notifier: Notifier = mock()
    private val reporter: Reporter = mock()
    private val executor: Executor = mock()
    private val sequencer: Sequencer = mock()

    init {
        whenever(notifier.add(any(), any())).thenAnswer {
            (it.arguments[1] as Listener).onBoot(Report(it.arguments[0] as Key, booted()))
        }

        whenever(reporter.get(any())).thenAnswer {
            Report(it.arguments[0] as Key, booted())
        }

        whenever(sequencer.count()).thenReturn(0)

        tail()
        apply()
    }

    /**
     * Mocks behaviour of library for given key.
     *
     * When mocked, [boot][Boots.boot] request or [subscription][Boots.subscribe] with given key will:
     * - immediately invoke provided listener calling [onBoot][Listener.onBoot] if provided status is [Booted][Status.Booted].
     * - immediately invoke provided listener calling [onBoot][Listener.onFailure] if provided status is [Booted][Status.Failed].
     * - will not invoke provided listener in all other cases
     *
     * When mocked, [report][Boots.report] for given key will return report with given status and 0 execution time.
     * @param key identifier to mock
     * @param status status to mock
     * @return self (for Java builder style usage)
     */
    fun mock(key: Key, status: Status) = this.apply {
        whenever(notifier.add(eq(key), any())).thenAnswer {
            if (status is Status.Booted) {
                (it.arguments[1] as Listener).onBoot(Report(key, status))
            } else if (status is Status.Failed) {
                (it.arguments[1] as Listener).onFailure(Report(key, status))
            }
        }

        whenever(reporter.get(key)).thenReturn(Report(key, status))
    }

    private fun apply() {
        Boots {
            configure {
                notifier = this@Mocker.notifier
                reporter = this@Mocker.reporter
                executor = this@Mocker.executor
                sequencer = this@Mocker.sequencer
            }
        }
    }

}
