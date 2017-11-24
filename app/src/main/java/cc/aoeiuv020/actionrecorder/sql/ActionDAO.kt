package cc.aoeiuv020.actionrecorder.sql

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

/**
 *
 * Created by AoEiuV020 on 2017.11.12-13:45:38.
 */
@Suppress("unused")
@Dao
interface ActionDAO {
    @Query("SELECT * FROM action")
    fun getAllAction(): List<Action>

    @Query("select * from action where time >= :from")
    fun getActionFrom(from: Long): List<Action>

    @Query("select * from action where time >= :from and time <= :to")
    fun getAction(from: Long, to: Long): List<Action>

    @Insert
    fun insert(action: Action)
}