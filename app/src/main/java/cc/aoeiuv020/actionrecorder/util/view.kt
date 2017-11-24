@file:Suppress("unused")

package cc.aoeiuv020.actionrecorder.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.view.View
import cc.aoeiuv020.actionrecorder.R

/**
 *
 * Created by AoEiuV020 on 2017.11.11-22:10:59.
 */
fun Context.notify(id: Int, text: String? = null, title: String? = null, noCancel: Boolean = false) {
    val icon = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        R.mipmap.ic_launcher_round
    } else {
        R.mipmap.ic_launcher_foreground
    }
    val channelId = "channel_default"
    val name = "default"
    val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(icon)
            .build()
    if (noCancel) {
        notification.flags = notification.flags or Notification.FLAG_NO_CLEAR
    }
    val manager = (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        if (manager.getNotificationChannel(channelId) == null) {
            val channel = NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
    }
    manager.notify(id, notification)
}

fun Context.cancel(id: Int) {
    (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).cancel(id)
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.show(show: Boolean) {
    if (show) {
        show()
    } else {
        hide()
    }
}
