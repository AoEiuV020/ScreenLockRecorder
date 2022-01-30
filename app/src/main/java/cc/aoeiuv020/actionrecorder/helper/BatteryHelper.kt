package cc.aoeiuv020.actionrecorder.helper

import android.content.Context
import android.content.Context.BATTERY_SERVICE
import android.os.BatteryManager


/**
 * Created by AoEiuV020 on 2022.01.30-19:43:11.
 */
object BatteryHelper {
    fun getElectricity(ctx: Context): Int {
        val mb = ctx.getSystemService(BATTERY_SERVICE) as BatteryManager
        return mb.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    }
}
