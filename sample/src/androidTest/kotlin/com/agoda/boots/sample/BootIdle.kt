package com.agoda.boots.sample

import android.support.test.espresso.IdlingResource
import android.support.test.espresso.IdlingResource.ResourceCallback
import com.agoda.boots.Boots
import com.agoda.boots.Key.Companion.all

class BootIdle : IdlingResource {

    @Volatile var booted = false

    lateinit var callback: ResourceCallback

    init {
        Boots {
            subscribe(all()) {
                onBoot = { booted = true }
            }
        }
    }

    override fun getName() = "BootIdle"

    override fun isIdleNow() = booted.also {
        if (booted) callback.onTransitionToIdle()
    }

    override fun registerIdleTransitionCallback(callback: ResourceCallback) {
        this.callback = callback
    }

}
