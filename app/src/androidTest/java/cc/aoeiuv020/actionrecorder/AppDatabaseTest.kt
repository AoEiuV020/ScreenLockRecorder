package cc.aoeiuv020.actionrecorder

import android.content.Intent
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import cc.aoeiuv020.actionrecorder.helper.BatteryHelper
import cc.aoeiuv020.actionrecorder.recorder.ActionRecorder
import cc.aoeiuv020.actionrecorder.sql.Action
import cc.aoeiuv020.actionrecorder.sql.AppDatabase
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    @Rule
    @JvmField
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun migration23Test() {
        val item = Action(
            id = 8,
            type = ActionRecorder.Type.BROADCAST.name,
            name = Intent.ACTION_SCREEN_ON,
            comments = "ON",
            battery = BatteryHelper.getElectricity(InstrumentationRegistry.getInstrumentation().targetContext)
        )
        val db1 = helper.createDatabase(TEST_DB, 2)
        db1.execSQL("INSERT INTO action (id, time, type, name, comments, battery) VALUES ('${item.id}', '${item.time}', '${item.type}', '${item.name}', '${item.comments}', ${item.battery});")
        db1.close()
        // 如果迁移后的数据库信息不匹配，这句就会报错，
        val db2 = helper.runMigrationsAndValidate(TEST_DB, 2, true, AppDatabase.MIGRATION_1_2)
        val c = db2.query("select * from action;")
        println(c.columnNames.joinToString())
        assertEquals(1, c.count)
        if (c.moveToNext()) {
            val result = Action(
                id = c.getLong(c.getColumnIndex("id")),
                time = c.getLong(c.getColumnIndex("time")),
                type = c.getString(c.getColumnIndex("type")),
                name = c.getString(c.getColumnIndex("name")),
                comments = c.getString(c.getColumnIndex("comments")),
                battery = c.getInt(c.getColumnIndex("battery"))
            )
            println(result)
            assertEquals(item, result)
        }
    }

    companion object {
        private const val TEST_DB = "migration-test"
    }

}