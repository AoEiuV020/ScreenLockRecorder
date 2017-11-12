package cc.aoeiuv020.actionrecorder.recorder

import cc.aoeiuv020.actionrecorder.App
import cc.aoeiuv020.actionrecorder.sql.Action
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import java.util.concurrent.Executor
import java.util.concurrent.Executors

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

    private fun record(type: Type, name: String, comments: String? = null) {
        debug {
            "type = $type, action = $name"
        }
        val action = Action(type = type.name, name = name, comments = comments)
        save(action)
    }

    private val executor: Executor = Executors.newCachedThreadPool()
    private fun save(action: Action) {
        executor.execute {
            App.database.actionDao().insert(action)
        }
    }
}