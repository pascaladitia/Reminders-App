package com.example.medicationreminder.utils

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import androidx.core.app.NotificationCompat
import com.example.medicationreminder.R
import com.example.medicationreminder.data.local.model.TaskEntity
import com.example.medicationreminder.data.service.AlarmReceiver
import java.util.*

class NotifUtils {
    companion object {
        var NOTIFICATION = "notification"
        var NOTIFICATION_LIST = "NOTIFICATION_LIST"
        val CHANNEL_NAME = "Reminder"
        val CHANNEL_ID = "0"
    }
}

@SuppressLint("UnspecifiedImmutableFlag")
fun createChannel(channelId: String, context: Context, task: TaskEntity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // API 26
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val (hours, min) = task.time!!.split(":").map { it.toInt() }
        val (year, month, day) = task.date!!.split("-")
            .map { it.toInt() }
        val calendar = Calendar.getInstance()

        val notificationIntent = Intent(context, TaskEntity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pIntent= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // API 31
            PendingIntent.getBroadcast(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_MUTABLE
            )
        } else {
            PendingIntent.getBroadcast(
                context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        val contentIntent= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // start activity from notification
            PendingIntent.getActivity(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_MUTABLE
            )
        } else {
            PendingIntent.getActivity(
                context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        val nm: NotificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        calendar.set(year, month - 1, day, hours, min,0)
        if (calendar.timeInMillis > System.currentTimeMillis()) { // set alarm if the time is in future


            val sb: Spannable = SpannableString(task.title)
            sb.setSpan(StyleSpan(Typeface.BOLD), 0, sb.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            val notificationBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.logo)
                .setChannelId(channelId)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setWhen(calendar.timeInMillis)
                .setContentIntent(contentIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setStyle(NotificationCompat
                    .InboxStyle()
                    .addLine(task.description)
                    //.addLine("another line")
                    .setBigContentTitle(sb)
                    .setSummaryText("Reminder"))
                .build()
            val intent = Intent(context, AlarmReceiver::class.java)
            intent.putExtra(NotifUtils.NOTIFICATION, notificationBuilder)

            val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // API 31
                PendingIntent.getBroadcast(
                    context,
                    task.alarmId.toInt(),
                    intent,
                    PendingIntent.FLAG_MUTABLE
                )
            } else {
                PendingIntent.getBroadcast(
                    context, task.alarmId.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
            alarmManager?.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }
}

fun cancelAlarm(context: Context, id: Long) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // API 31
        PendingIntent.getBroadcast(
            context,
            id.toInt(),
            intent,
            PendingIntent.FLAG_MUTABLE
        )
    } else {
        PendingIntent.getBroadcast(
            context, id.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
    alarmManager.cancel(pendingIntent)
}

fun String.capitalized(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase())
            it.titlecase(Locale.getDefault())
        else it.toString()
    }
}

fun getUniqueId() = ((System.currentTimeMillis() % 1000000).toLong())
