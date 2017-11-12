package cc.aoeiuv020.actionrecorder.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cc.aoeiuv020.actionrecorder.R
import cc.aoeiuv020.actionrecorder.service.ReceiverService
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btStart.setOnClickListener {
            startService<ReceiverService>()
        }

        btStop.setOnClickListener {
            stopService(intentFor<ReceiverService>())
        }

        btFinish.setOnClickListener {
            finish()
        }
    }
}
