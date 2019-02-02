package com.agoda.boots.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import com.agoda.boots.Boots
import com.agoda.boots.Executor
import com.agoda.boots.Key.Companion.all
import com.agoda.boots.Key.Companion.single
import com.agoda.boots.Listener
import com.agoda.boots.Logger.Level.DEBUG
import com.agoda.boots.logger.AndroidLogger
import kotlinx.android.synthetic.main.activity_main.*

class KotlinActivity : AppCompatActivity() {

    private val key = all()

    var listener: Listener? = null
    private var executor: Executor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intent.extras?.let {
            (it.getSerializable("executor") as ExecutorEnum).let {
                executor = it.executor
            }
        }
    }

    override fun onStart() {
        super.onStart()

        Boots {
            configure {
                executor = this@KotlinActivity.executor
                logger = AndroidLogger(DEBUG)
            }

            add(
                    DeviceIdBootable(applicationContext),
                    NetworkRequestBootable(),
                    DatabaseBootable(),
                    RandomTimeBootable()
            )

            val custom = single("CUSTOM")

            add(custom, isCritical = true) {
                val something = 2 + 2
            }

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
