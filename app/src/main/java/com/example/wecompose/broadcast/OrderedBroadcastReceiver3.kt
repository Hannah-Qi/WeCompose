package com.example.wecompose.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.wecompose.data.KEY_RESULT
import com.example.wecompose.data.toast

class OrderedBroadcastReceiver3 : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        println("test result")
        val result = intent?.getStringExtra(KEY_RESULT)
        val extras = getResultExtras(true)
        val name = extras?.getString("name")
        val name1 = resultData
        println("test result $result")
        context?.let{
            "接收到了静态广播信息--3--$name1---$name---$result".toast(it)
        }
    }
}