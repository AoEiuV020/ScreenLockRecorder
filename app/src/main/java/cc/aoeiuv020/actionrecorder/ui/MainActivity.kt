package cc.aoeiuv020.actionrecorder.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import cc.aoeiuv020.actionrecorder.R
import cc.aoeiuv020.actionrecorder.service.ReceiverService
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btStart.setOnClickListener {
            ContextCompat.startForegroundService(this, intentFor<ReceiverService>())
        }

        btStop.setOnClickListener {
            stopService(intentFor<ReceiverService>())
        }

        btFinish.setOnClickListener {
            finish()
        }

        btData.setOnClickListener {
            DataActivity.start(this)
        }
    }
}
