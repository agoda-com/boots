package com.agoda.boots.logger

import android.util.Log
import com.agoda.boots.Logger
import com.agoda.boots.Logger.Level.*

/**
 * Android implementation of [Logger].
 *
 * Simply translates log calls to Android's [Log].
 * @param level minimal log level to output
 */
class AndroidLogger(private val level: Logger.Level) : Logger {

    override fun log(level: Logger.Level, message: String) {
        if (level >= this.level) {
            when (level) {
                VERBOSE -> Log.v(LOG_TAG, message)
                DEBUG -> Log.d(LOG_TAG, message)
                INFO -> Log.i(LOG_TAG, message)
                WARNING -> Log.w(LOG_TAG, message)
                ERROR -> Log.e(LOG_TAG, message)
            }
        }
    }

    override fun log(level: Logger.Level, message: String, throwable: Throwable) {
        if (level >= this.level) {
            when (level) {
                VERBOSE -> Log.v(LOG_TAG, message, throwable)
                DEBUG -> Log.d(LOG_TAG, message, throwable)
                INFO -> Log.i(LOG_TAG, message, throwable)
                WARNING -> Log.w(LOG_TAG, message, throwable)
                ERROR -> Log.e(LOG_TAG, message, throwable)
            }
        }
    }

    companion object {
        const val LOG_TAG = "Boots"
    }

}