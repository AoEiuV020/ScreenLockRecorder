package cc.aoeiuv020.actionrecorder.receiver

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import cc.aoeiuv020.actionrecorder.R
import cc.aoeiuv020.actionrecorder.util.cancel
import cc.aoeiuv020.actionrecorder.util.notify
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug

/**
 *
 * Created by AoEiuV020 on 2017.11.11-21:26:39.
 */
class ReceiverService : Service(), AnkoLogger {
    private lateinit var receiver: AllReceiver
    override fun onCreate() {
        super.onCreate()
        debug { "onCreate" }
        notify(0, getString(R.string.recording), noCancel = true)
        receiver = AllReceiver()
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        filter.addAction(Intent.ACTION_SCREEN_ON)
        registerReceiver(receiver, filter)
    }

    override fun onBind(intent: Intent): IBinder? {
        debug { "onBind" }
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        debug { "onStartCommand" }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        debug { "onDestroy" }
        unregisterReceiver(receiver)
        cancel(0)
        super.onDestroy()
    }
}
