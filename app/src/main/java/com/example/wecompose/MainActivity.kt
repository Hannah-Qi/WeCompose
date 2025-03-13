package com.example.wecompose

import android.content.Intent
import android.content.pm.LauncherActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.wecompose.learnCoroution.LaunchCoroutionActivity
import com.example.wecompose.ui.ChatPage
import com.example.wecompose.ui.Home
import com.example.wecompose.ui.theme.WeComposeTheme

class MainActivity : ComponentActivity() {
    private val viewModel: WeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println("test: onCreate")
        setContent {

            WeComposeTheme(viewModel.theme) {
                Column {
                    Text(text = "打开相机", fontSize = 20.sp, modifier = Modifier
                        .clickable {
//                        val intent = Intent(this@MainActivity,LaunchCoroutionActivity::class.java)




                    })
                    Box {
                        Home(viewModel = viewModel)
                        ChatPage(context = this@MainActivity)
                    }
                }
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun onStart() {
        super.onStart()
        println("test: onStart")
    }

    override fun onResume() {
        super.onResume()
        println("test: onResume")
    }

    override fun onPause() {
        super.onPause()
        println("test: onPause")
    }

    override fun onStop() {
        super.onStop()
        println("test: onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("test: onDestroy")
    }

    override fun onBackPressed() {
        println("test: onBackPressed()")
        if(!viewModel.endChat()) {
            super.onBackPressed()
        } else {
            viewModel.endChat()
        }
    }
}

