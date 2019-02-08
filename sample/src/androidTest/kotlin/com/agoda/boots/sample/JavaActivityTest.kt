package com.agoda.boots.sample

import android.content.Intent
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.agoda.boots.executor.AndroidExecutor
import com.agoda.boots.executor.RxAndroidExecutor
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rx.schedulers.Schedulers

@RunWith(AndroidJUnit4::class)
class JavaActivityTest : MainActivityTest() {

    @Rule
    @JvmField
    val rule = ActivityTestRule(JavaActivity::class.java, true, false)

    @Test
    fun testAndroidExecutor() {
        rule.launchActivity(Intent().putExtra("executor", ExecutorEnum.NATIVE))
        screen {
            progressBar { isGone() }
            textView { isVisible() }
        }
    }

    @Test
    fun testRxAndroidExecutor() {
        rule.launchActivity(Intent().putExtra("executor", ExecutorEnum.RX))
        screen {
            progressBar { isGone() }
            textView { isVisible() }
        }
    }

    @Test
    fun testCoroutineAndroidExecutor() {
        rule.launchActivity(Intent().putExtra("executor", ExecutorEnum.COROUTINE))
        screen {
            progressBar { isGone() }
            textView { isVisible() }
        }
    }
}
