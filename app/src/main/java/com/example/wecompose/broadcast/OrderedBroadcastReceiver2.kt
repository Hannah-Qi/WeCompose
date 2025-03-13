package com.example.wecompose.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.wecompose.data.KEY_RESULT
import com.example.wecompose.data.toast

class OrderedBroadcastReceiver2 : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        println("test result")
        val result = intent?.getStringExtra(KEY_RESULT)
        println("test result $result")
        context?.let{
            "接收到了静态广播信息--2--$result".toast(it)
        }

        resultData = "广播2"
//        if(intent != null){
//            abortBroadcast()
//        }
    }
}