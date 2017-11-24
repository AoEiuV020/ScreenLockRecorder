package cc.aoeiuv020.actionrecorder.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import cc.aoeiuv020.actionrecorder.recorder.ActionRecorder
import org.jetbrains.anko.AnkoLogger
import java.lang.reflect.Modifier

/**
 * 记录所有广播的Receiver,
 * Created by AoEiuV020 on 2017.11.11-21:01:27.
 */
class AllReceiver : BroadcastReceiver(), AnkoLogger {
    companion object {
        @Suppress("unused")
        fun noFilter(): IntentFilter {
            val filter = IntentFilter()
            Intent::class.java.fields.filter {
                val m = it.modifiers
                val flag = Modifier.STATIC or Modifier.PUBLIC or Modifier.FINAL
                m and flag == flag && it.type == String::class.java && it.name.startsWith("ACTION_")
            }.forEach {
                filter.addAction(it.get(null) as String)
            }
            return filter
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        ActionRecorder.broadcast(intent.action)
    }
}
