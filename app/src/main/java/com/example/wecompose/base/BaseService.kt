package com.example.wecompose.base

import android.app.Service
import android.content.Intent
import android.os.IBinder

open class BaseService(private val serviceName: String): Service() {
    override fun onCreate() {
        super.onCreate()
        println("test $serviceName onCreate-base")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("test $serviceName onStartCommand-base")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        println("test $serviceName onBind-base")
        return null
    }

    override fun onRebind(intent: Intent?) {
        println("test $serviceName onRebind-base")
        super.onRebind(intent)
    }

    override fun onDestroy() {
        println("test $serviceName onDestroy-base")
        super.onDestroy()
    }
}