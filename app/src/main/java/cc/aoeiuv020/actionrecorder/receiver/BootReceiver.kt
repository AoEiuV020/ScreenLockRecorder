package cc.aoeiuv020.actionrecorder.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import cc.aoeiuv020.actionrecorder.recorder.ActionRecorder
import cc.aoeiuv020.actionrecorder.service.ReceiverService
import cc.aoeiuv020.anull.notNull
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.intentFor

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(ctx: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            if (ctx.defaultSharedPreferences.getBoolean("started", false)) {
                ContextCompat.startForegroundService(ctx, ctx.intentFor<ReceiverService>())
            }
            ActionRecorder.broadcast(intent.action.notNull())
        }
    }
}