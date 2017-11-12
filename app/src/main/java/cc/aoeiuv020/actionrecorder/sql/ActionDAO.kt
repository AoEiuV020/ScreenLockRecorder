package cc.aoeiuv020.actionrecorder.sql

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

/**
 *
 * Created by AoEiuV020 on 2017.11.12-13:45:38.
 */
@Dao
interface ActionDAO {
    @Query("SELECT * FROM action")
    fun loadAllUsers(): Array<Action>

    @Insert
    fun insert(action: Action)
}