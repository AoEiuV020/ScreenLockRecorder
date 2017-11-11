package cc.aoeiuv020.actionrecorder.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import cc.aoeiuv020.actionrecorder.util.notify
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug

/**
 *
 * Created by AoEiuV020 on 2017.11.11-21:01:27.
 */
class AllReceiver : BroadcastReceiver(), AnkoLogger {
    override fun onReceive(context: Context, intent: Intent) {
        debug { intent.action }
        context.notify(1, intent.action)
    }
}
