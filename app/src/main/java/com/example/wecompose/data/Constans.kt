package com.example.wecompose.data

import android.content.Context
import android.widget.Toast

const val KEY_RESULT = "key_send1"
const val ACTION_UPLOAD_RESULT = "com.example.we.compose.upload_result"

fun String.toast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}