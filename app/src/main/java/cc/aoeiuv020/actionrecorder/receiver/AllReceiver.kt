package cc.aoeiuv020.actionrecorder.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import cc.aoeiuv020.actionrecorder.recorder.ActionRecorder
import org.jetbrains.anko.AnkoLogger

/**
 * 记录所有广播的Receiver,
 * Created by AoEiuV020 on 2017.11.11-21:01:27.
 */
class AllReceiver : BroadcastReceiver(), AnkoLogger {
    override fun onReceive(context: Context, intent: Intent) {
        ActionRecorder.broadcast(intent.action)
    }
}
