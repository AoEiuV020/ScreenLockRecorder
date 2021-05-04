package cc.aoeiuv020.actionrecorder.sql

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 *
 * Created by AoEiuV020 on 2017.11.12-13:13:51.
 */
@Entity(tableName = "action", indices = arrayOf(Index(value = *arrayOf("id"))))
data class Action(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        val id: Long? = null,
        @ColumnInfo(name = "time")
        val time: Long = System.currentTimeMillis(),
        @ColumnInfo(name = "type")
        val type: String,
        @ColumnInfo(name = "name")
        val name: String,
        @ColumnInfo(name = "comments")
        val comments: String? = null
)