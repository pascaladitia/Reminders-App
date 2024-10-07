package com.example.medicationreminder.data.service

import android.Manifest
import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.example.medicationreminder.data.local.model.TaskEntity
import com.example.medicationreminder.utils.NotifUtils.Companion.NOTIFICATION
import com.example.medicationreminder.utils.NotifUtils.Companion.NOTIFICATION_LIST
import com.example.medicationreminder.utils.createChannel
import com.example.medicationreminder.utils.getUniqueId

class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // API 33
            intent.getParcelableExtra(NOTIFICATION, Notification::class.java)
        } else {
            intent.getParcelableExtra<Notification>(NOTIFICATION)
        }
        val notificationList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // API 33
            intent.getParcelableArrayListExtra<TaskEntity>(NOTIFICATION_LIST, TaskEntity::class.java) // need to get list of class
        } else {
            intent.getParcelableArrayListExtra<TaskEntity>(NOTIFICATION_LIST)
        }
        if(notificationList?.isNotEmpty() == true){
            notificationList.forEach{
                if(it.date != null && it.time != null){
                    //val notification =  createNotification(it,context)
                    createChannel(getUniqueId().toString(),context,it)
                }
            }
        }
        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        // Show the notification
        if (notification != null) {
            notificationManager.notify(getUniqueId().toInt(), notification) // notification id
        }
    }
}