package cc.aoeiuv020.actionrecorder.recorder

import cc.aoeiuv020.actionrecorder.App
import cc.aoeiuv020.actionrecorder.util.notify
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug

/**
 * 负责记录事件，
 * Created by AoEiuV020 on 2017.11.12-12:30:58.
 */
object ActionRecorder : AnkoLogger {
    enum class Type {
        BROADCAST
    }

    fun broadcast(action: String) {
        record(Type.BROADCAST, action)
    }

    private fun record(type: Type, action: String) {
        debug {
            "type = $type, action = $action"
        }
        App.ctx.notify(action.hashCode(), action)
    }
}