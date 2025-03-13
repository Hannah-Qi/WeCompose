package com.example.wecompose.service

import android.os.Binder

class MyBinder(private val mMyBinderService: MyBindService): Binder() {
    fun test() {
        println("test MyBinder")
        mMyBinderService.test()
    }
}