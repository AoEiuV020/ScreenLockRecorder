package cc.aoeiuv020.actionrecorder.main

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cc.aoeiuv020.actionrecorder.R
import cc.aoeiuv020.actionrecorder.receiver.ReceiverService
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btStart.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intentFor<ReceiverService>())
            } else {
                startService<ReceiverService>()
            }
        }

        btStop.setOnClickListener {
            stopService(intentFor<ReceiverService>())
        }

        btFinish.setOnClickListener {
            finish()
        }
    }
}
