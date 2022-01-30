package cc.aoeiuv020.actionrecorder

import android.app.Application
import cc.aoeiuv020.actionrecorder.sql.AppDatabase

/**
 *
 * Created by AoEiuV020 on 2017.11.11-20:47:15.
 */
class App : Application() {
    companion object {
        lateinit var ctx: App
        lateinit var appDatabase: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        ctx = this
        appDatabase = AppDatabase.getInstance(this)
    }
}
