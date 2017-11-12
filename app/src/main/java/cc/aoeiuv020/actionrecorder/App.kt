package cc.aoeiuv020.actionrecorder

import android.app.Application
import android.arch.persistence.room.Room
import cc.aoeiuv020.actionrecorder.sql.Database

/**
 *
 * Created by AoEiuV020 on 2017.11.11-20:47:15.
 */
class App : Application() {
    companion object {
        lateinit var ctx: App
        lateinit var database: Database
    }

    override fun onCreate() {
        super.onCreate()
        ctx = this
        database = Room.databaseBuilder(ctx, Database::class.java, "recorder.db").build()
    }
}
