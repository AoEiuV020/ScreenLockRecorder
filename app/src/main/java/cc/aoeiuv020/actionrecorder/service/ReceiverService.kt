package cc.aoeiuv020.actionrecorder.service

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.os.IBinder
import cc.aoeiuv020.actionrecorder.R
import cc.aoeiuv020.actionrecorder.receiver.AllReceiver
import cc.aoeiuv020.actionrecorder.recorder.ActionRecorder
import cc.aoeiuv020.actionrecorder.util.cancel
import cc.aoeiuv020.actionrecorder.util.notify
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import java.util.concurrent.TimeUnit

/**
 * 维持广播接收器的服务，
 * Created by AoEiuV020 on 2017.11.11-21:26:39.
 */
class ReceiverService : Service(), AnkoLogger {
    companion object {
        private val NOTIFICATION_ID = ReceiverService::class.java.simpleName.hashCode()
    }

    private lateinit var receiver: AllReceiver
    private val handler = Handler()
    private val notifyRunnable = object : Runnable {
        override fun run() {
            notify(NOTIFICATION_ID, getString(R.string.recording), noCancel = true)
            // 监控通知每分钟刷新一次，
            handler.postDelayed(this, TimeUnit.MINUTES.toMillis(1))
        }
    }

    override fun onCreate() {
        super.onCreate()
        debug { "onCreate" }
        handler.post(notifyRunnable)
        receiver = AllReceiver()
        val filter = IntentFilter()
        ActionRecorder.broadcastActionCommentMap.keys.forEach {
            filter.addAction(it)
        }
        registerReceiver(receiver, filter)
        startForeground(1, Notification())
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
        handler.removeCallbacks(notifyRunnable)
        cancel(NOTIFICATION_ID)
        super.onDestroy()
    }
}
