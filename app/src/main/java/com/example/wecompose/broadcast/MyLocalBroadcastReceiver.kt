package com.example.wecompose.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.wecompose.data.KEY_RESULT
import com.example.wecompose.data.toast

class MyLocalBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val result = intent?.getStringExtra(KEY_RESULT)
        println("test result $result")
        context?.let{
            "这是一个本地广播".toast(it)
        }
    }
}