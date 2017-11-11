package cc.aoeiuv020.actionrecorder.util

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat
import cc.aoeiuv020.actionrecorder.R

/**
 *
 * Created by AoEiuV020 on 2017.11.11-22:10:59.
 */
fun Context.notify(id: Int, text: String? = null, title: String? = null, noCancel: Boolean = false) {
    val icon = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        R.mipmap.ic_launcher_round
    } else {
        R.drawable.ic_launcher_foreground
    }
    val notification = NotificationCompat.Builder(this, "actionReceiver")
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(icon)
            .build()
    if (noCancel) {
        notification.flags = notification.flags or Notification.FLAG_NO_CLEAR
    }
    (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(id, notification)
}

fun Context.cancel(id: Int) {
    (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).cancel(id)
}
