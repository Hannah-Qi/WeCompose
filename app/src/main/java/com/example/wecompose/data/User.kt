package com.example.wecompose.data

import androidx.annotation.DrawableRes
import com.example.wecompose.R

class User(
    val id: String,
    val name: String,
    @DrawableRes val avatar: Int
) {
    companion object {
        val Me: User = User("QiTingting","齐婷婷", R.drawable.img)
    }
}