package com.example.wecompose.broadcast

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.wecompose.R
import com.example.wecompose.data.ACTION_UPLOAD_RESULT
import com.example.wecompose.data.KEY_RESULT
import com.example.wecompose.data.toast

class BroadcastActivity : AppCompatActivity() {

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            println("test result")
            val result = intent?.getStringExtra(KEY_RESULT)
            println("test result $result")
            context?.let{
                "接收到了动态广播信息$result".toast(it)
            }
        }
    }

    private val mLocalBroadcastReceiver = MyLocalBroadcastReceiver()
    private val mGlobalBroadcastReceiver = MyGlobalBroadcastReceiver()

    private lateinit var mLocalBroadcastManager: LocalBroadcastManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_broadcast)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this)

        registerReceiver()

        registerLocalReceiver()

        registerGlobalReceiver()
    }

    private fun registerReceiver() {
        //注册广播
        println("test LearnFragmentActivity 注册广播")
        val intentFilter = IntentFilter()
        intentFilter.addAction(ACTION_UPLOAD_RESULT)
        registerReceiver(broadcastReceiver, intentFilter, RECEIVER_EXPORTED)
    }

    private fun registerLocalReceiver() {
        //注册本地广播
        println("test LearnFragmentActivity 注册广播")
        val intentFilter = IntentFilter()
        intentFilter.addAction("local_broad_cast")
        mLocalBroadcastManager.registerReceiver(mLocalBroadcastReceiver,intentFilter)
    }

    private fun registerGlobalReceiver() {
        //注册全局广播
        println("test LearnFragmentActivity 注册广播")
        val intentFilter = IntentFilter()
        intentFilter.addAction("global_broad_cast")
        registerReceiver(mGlobalBroadcastReceiver,intentFilter,RECEIVER_EXPORTED)
    }

    @SuppressLint("WrongConstant")
    fun SendStaticBroadcast(view: View) {
        val intent = Intent("my_broadcast_receiver")
        intent.putExtra(KEY_RESULT, "upload_success")
//        intent.setPackage(packageName)
        intent.addFlags(0x01000000)
        sendBroadcast(intent)
    }

    fun SendDynamicBroadcast(view: View) {
        val intent = Intent(ACTION_UPLOAD_RESULT)
        intent.putExtra(KEY_RESULT, "dynamic_upload_success")
        sendBroadcast(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
        mLocalBroadcastManager.unregisterReceiver(mLocalBroadcastReceiver)
    }

    fun SendOrderedBroadcast(view: View) {
        val intent = Intent("my_ordered_broadcast_receiver")
        intent.putExtra(KEY_RESULT, "upload_success")
        intent.setPackage(packageName)
//        sendBroadcast(intent)
        sendOrderedBroadcast(intent,null)
    }

    fun SendLocalBroadcast(view: View) {
        val intent = Intent("local_broad_cast")
        intent.putExtra(KEY_RESULT, "local_upload_success")
        mLocalBroadcastManager.sendBroadcast(intent)
    }

    fun SendGlobalBroadcast(view: View) {
        val intent = Intent("global_broad_cast")
        intent.putExtra(KEY_RESULT, "global_upload_success")
        sendBroadcast(intent)
    }
}