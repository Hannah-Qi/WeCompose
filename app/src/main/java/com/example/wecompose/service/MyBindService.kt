package com.example.wecompose.service

import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import com.example.wecompose.base.BaseService
import com.example.wecompose.data.ACTION_UPLOAD_RESULT

class MyBindService: BaseService(MyBindService::class.java.simpleName) {
    private lateinit var mBinder: MyBinder

    override fun onCreate() {
        println("test MyBindService onCreate")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("test MyBindService onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        println("test MyBindService onBind")
        mBinder = MyBinder(this)
        return mBinder
    }

    override fun onRebind(intent: Intent?) {
        println("test MyBindService onRebind")
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        println("test MyBindService onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        println("test MyBindService onDestroy")
    }

    fun test() {
        println("test MyBindService test")
    }

}