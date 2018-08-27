package com.agoda.boots.sample

import android.support.test.espresso.IdlingRegistry
import com.agoda.boots.Boots
import org.junit.After
import org.junit.Before
import rx.schedulers.Schedulers

open class MainActivityTest {

    val screen = MainScreen()
    val idling = BootIdle()

    @Before
    fun setup() {
        IdlingRegistry
                .getInstance()
                .register(idling)
    }

    @After
    fun cleanup() {
        IdlingRegistry
                .getInstance()
                .unregister(idling)

        Schedulers.shutdown()
        Schedulers.reset()

        Boots { reset() }
    }
}