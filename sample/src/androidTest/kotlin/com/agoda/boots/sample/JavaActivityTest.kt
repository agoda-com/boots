package com.agoda.boots.sample

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class JavaActivityTest : MainActivityTest() {

    @Rule
    @JvmField
    val rule = ActivityTestRule(JavaActivity::class.java)

    @Test
    fun test() {
        screen {
            progressBar { isGone() }
            textView { isVisible() }
        }
    }

}
