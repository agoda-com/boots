package com.agoda.boots.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.agoda.boots.Boots
import com.agoda.boots.Key.Companion.all
import com.agoda.boots.Key.Companion.multiple
import com.agoda.boots.Listener
import com.agoda.boots.Logger.Level.DEBUG
import com.agoda.boots.executor.RxAndroidExecutor
import com.agoda.boots.logger.AndroidLogger
import rx.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_main.*

class KotlinActivity : AppCompatActivity() {

    private val key = all()

    var listener: Listener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        Boots {
            configure {
                executor = RxAndroidExecutor(Schedulers.io())
                logger = AndroidLogger(DEBUG)
            }

            add(
                    DeviceIdBootable(applicationContext),
                    NetworkRequestBootable(),
                    DatabaseBootable(),
                    RandomTimeBootable()
            )

            listener = boot(key) {
                onBoot = {
                    progressBar.visibility = GONE
                    textView.visibility = VISIBLE
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()

        listener?.let {
            Boots { unsubscribe(key, it) }
        }
    }
}
