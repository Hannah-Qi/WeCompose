package com.example.wecompose.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity(private val activity: String): AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        println("test $activity onCreate-base")
    }

    override fun onStart() {
        super.onStart()
        println("test $activity onStart-base")
    }

    override fun onResume() {
        super.onResume()
        println("test $activity onResume-base")
    }

    override fun onPause() {
        super.onPause()
        println("test $activity onPause-base")
    }

    override fun onStop() {
        super.onStop()
        println("test $activity onStop-base")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("test $activity onDestroy-base")
    }

    override fun onRestart() {
        super.onRestart()
        println("test $activity onRestart-base")
    }
}