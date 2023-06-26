package com.example.pocketpal


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.pocketpal.modelsb.BillModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.text.SimpleDateFormat
import java.util.*


const val channelId ="notification_channel"
const val channelName="com.example.pocketpal"


class MyFirebaseMessagingService: FirebaseMessagingService() {



        override fun onMessageReceived(remoteMessage: RemoteMessage) {
            Log.d(TAG, "Received notification: ${remoteMessage.data}")
            val title = remoteMessage.data["title"]
            val message = remoteMessage.data["message"]
            // Handle the notification data here
        }
    }


