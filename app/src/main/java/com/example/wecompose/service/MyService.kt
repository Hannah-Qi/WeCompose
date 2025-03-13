package com.example.wecompose.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.wecompose.R
import com.example.wecompose.base.BaseService
import com.example.wecompose.data.ACTION_UPLOAD_RESULT
import com.example.wecompose.data.KEY_RESULT
import java.util.Timer
import java.util.TimerTask

class MyService: BaseService(MyService::class.java.simpleName) {
    override fun onCreate() {
        println("test MyService onCreate")

        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("test MyService onStartCommand")
//        val value = intent?.getStringExtra("key_stop")
//        if(value.equals("stop")) {
//            stopSelf()
//        }


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("channel_id", "通知", importance)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this,"channel_id")
            .setSmallIcon(R.drawable.baseline_doorbell_24)
            .setContentTitle("这是通知标题")
            .setContentText("这是通知内容")
            .build()

        // Android 8.0 及以上版本，需要指定 foregroundServiceType
        startForeground(1, notification)

        val timer = Timer()

        timer.schedule(object: TimerTask() {
            override fun run() {
//                stopSelf()
                stopForeground(STOP_FOREGROUND_DETACH)
                println("test MyService stopForeground")
            }
        },20000)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        println("test MyService onBind")
        return null
    }
}