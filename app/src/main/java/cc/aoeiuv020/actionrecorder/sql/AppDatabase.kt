package cc.aoeiuv020.actionrecorder.sql

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 *
 * Created by AoEiuV020 on 2017.11.12-13:48:17.
 */
@Database(entities = [Action::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var sInstance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            val dbFile = context.getDatabasePath("recorder.db")
            return sInstance ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                dbFile.path
            )
                .addMigrations(MIGRATION_1_2)
                .build().also {
                    sInstance = it
                }
        }

        private fun migration(start: Int, end: Int, migrate: (SupportSQLiteDatabase) -> Unit) =
            object : Migration(start, end) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    migrate(database)
                }
            }

        @VisibleForTesting
        val MIGRATION_1_2 = migration(1, 2) {
            // 添加了电量的字段，
            it.execSQL("ALTER TABLE action ADD battery integer default -1 NOT NULL;")
        }


    }

    abstract fun actionDao(): ActionDAO
}