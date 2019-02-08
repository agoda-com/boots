package com.agoda.boots.sample

import android.content.Context
import android.os.Looper
import android.preference.PreferenceManager
import com.agoda.boots.Bootable
import com.agoda.boots.Key.Companion.multiple
import java.lang.Math.random
import java.util.*

class DeviceIdBootable(private val context: Context) : Bootable() {
    override val key = Keys.DEVICE_ID
    override val isCritical = true

    override fun boot() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)

        if (!preferences.contains("device_id")) {
            preferences
                    .edit()
                    .putString("device_id", UUID.randomUUID().toString())
                    .apply()
        }
    }
}

class MainThreadBootable : Bootable() {
    override val key = Keys.DEVICE_ID
    override val isCritical = true
    override val isConcurrent = false

    override fun boot() {
       if(Looper.myLooper() != Looper.getMainLooper()) {
           throw IllegalThreadStateException("Should be invoked on Main Thread")
       }
    }
}

class NetworkRequestBootable : Bootable() {
    override val key = Keys.NETWORK

    override fun boot() {
        // No actual code, for test purposes only
        Thread.sleep(1000L)
    }

}

class DatabaseBootable : Bootable() {
    override val key = Keys.DATABASE

    override fun boot() {
        // No actual code, for test purposes only
        Thread.sleep(1500L)
    }

}

class DatabaseCacheBootable : Bootable() {
    override val key = Keys.DATABASE_CACHE
    override val dependencies = multiple(Keys.DATABASE)

    override fun boot() {
        // No actual code, for test purposes only
        Thread.sleep(300L)
    }

}

class RandomTimeBootable : Bootable() {
    override val key = Keys.RANDOM

    override fun boot() {
        Thread.sleep((random() * 3000).toLong())
    }

}
