package cc.aoeiuv020.actionrecorder.sql

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 *
 * Created by AoEiuV020 on 2017.11.12-13:48:17.
 */
@Database(entities = arrayOf(Action::class), version = 1)
abstract class Database : RoomDatabase() {
    abstract fun actionDao(): ActionDAO
}