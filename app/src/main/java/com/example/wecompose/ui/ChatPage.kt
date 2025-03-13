package com.example.wecompose.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wecompose.R
import com.example.wecompose.WeViewModel
import com.example.wecompose.data.Msg
import com.example.wecompose.data.User
import com.example.wecompose.ui.theme.WeComposeTheme
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun ChatPage(context: Context) {
    val viewModel: WeViewModel = viewModel()
    val offsetPercentX by animateFloatAsState(if (viewModel.chatting) 0f else 1f, label = "offsetPercent") //百分比
    val chat = viewModel.currentChat

    if(chat != null) {
        Column(
            Modifier
                .offsetPercent(offsetPercentX)
                .background(WeComposeTheme.colors.background)
                .fillMaxSize()
        ) {
            WeTopBar(chat.friend.name) {
                viewModel.endChat()
            }

            var shakingTime by remember {
                mutableIntStateOf(0)
            }

            Box(
                Modifier
                    .background(WeComposeTheme.colors.chatPage)
                    .weight(1f)
            ) {
                Box(
                    Modifier
                        .alpha(WeComposeTheme.colors.chatPageBgAlpha)
                        .fillMaxSize()
                ) {
                    Image(
                        painterResource(R.drawable.tab_1_1),
                        null,
                        Modifier
                            .align(Alignment.CenterStart)
                            .padding(bottom = 100.dp)
                    )
                    Image(
                        painterResource(R.drawable.tab_1_1),
                        null,
                        Modifier
                            .align(Alignment.TopEnd)
                            .padding(bottom = 24.dp)
                    )
                    Image(
                        painterResource(R.drawable.tab_1_1),
                        null,
                        Modifier
                            .align(Alignment.BottomEnd)
                            .padding(bottom = 200.dp)
                    )
                }

                val shakingOffset = remember {
                    Animatable(0f)
                }
                LaunchedEffect(key1 = shakingTime) {
                    if(shakingTime != 0) {
                        shakingOffset.animateTo(
                            0f,
                            animationSpec = spring(0.3f,600f),
                            initialVelocity = -2000f
                        )
                    }
                }

                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .offset(shakingOffset.value.dp, shakingOffset.value.dp)
                ) {
                    items(chat.msgs.size) { index ->
                        val msg = chat.msgs[index]
                        MessageItem(msg, shakingTime,chat.msgs.size - index - 1)
                    }
                }
            }

            ChatBottomBar(
                onBomClicked = {
                    viewModel.boom(chat)
                    shakingTime++
                },
                onCameraClicked = {
                    val intent = Intent()
//                    intent.action = Intent.ACTION_DIAL
//                    intent.data = Uri.parse("tel:110")

                    intent.action = "android.media.action.IMAGE_CAPTURE"

//                        intent.action = "android.intent.action.PICK"
//                        intent.type = "image/*"
                    context.startActivity(intent)
                }
            )
        }
    }
}

@Composable
fun MessageItem(msg: Msg, shakingTime: Int, shakingLevel: Int) {
    val shakingAndleBubble = remember {
        Animatable(0f)
    }

    /**
     * LaunchedEffect：这是一个用于启动协程的 Composable 函数，它确保协程在组件的生命周期内正确启动和取消。
     *
     * key1 是 LaunchedEffect 的一个参数，用于指定一个或多个键。
     * 当这些键的值发生变化时，LaunchedEffect 内部的协程会被取消并重新启动。
     * 这里使用 shakingTime 作为键，意味着每当 shakingTime 的值改变时，协程会重新启动。
     * */
    LaunchedEffect(key1 = shakingTime) {
        if(shakingTime != 0) {
            delay(shakingLevel.toLong() * 30) //根据 shakingLevel 的值进行延迟，单位是毫秒。这意味着不同的消息可能会有不同的延迟时间，从而产生不同步的抖动效果。
            shakingAndleBubble.animateTo(
                0f,  //使用 Animatable 对象 shakingAndleBubble 执行动画，将其值从当前值动画到 0f。
                animationSpec = spring(0.4f,500f), //使用弹簧动画，0.4f 是阻尼比，500f 是刚度。这会使动画具有弹性的效果
                initialVelocity = 1200f / (1 + shakingLevel * 0.4f) //设置动画的初始速度，shakingLevel 会影响初始速度的大小
            )
        }
    }

    if(msg.from == User.Me) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            val bubbleColor = WeComposeTheme.colors.bubbleMe
            Text(
                msg.text,
                Modifier
                    .graphicsLayer {
                        rotationZ = shakingAndleBubble.value
                        transformOrigin = TransformOrigin(1f,0f)
                    }
                    .drawBehind {
                        val bubble = Path().apply {
                            val rect = RoundRect(
                                10.dp.toPx(),
                                0f,
                                size.width - 10.dp.toPx(),
                                size.height,
                                4.dp.toPx(),
                                4.dp.toPx()
                            )
                            addRoundRect(rect)
                            moveTo(size.width - 10.dp.toPx(), 15.dp.toPx())
                            lineTo(size.width - 5.dp.toPx(), 20.dp.toPx())
                            lineTo(size.width - 10.dp.toPx(), 25.dp.toPx())
                            close()
                        }
                        drawPath(bubble, bubbleColor)
                    }
                    .padding(20.dp, 10.dp),
                color = WeComposeTheme.colors.textPrimaryMe
            )

            Image(
                painterResource(msg.from.avatar),
                contentDescription = msg.from.name,
                Modifier
                    .graphicsLayer {
                        rotationZ = shakingAndleBubble.value * 0.6f
                        transformOrigin = TransformOrigin(1f,0f)
                    }
                    .size(40.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
        }
    } else {
        Row(
            Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Image(
                painterResource(msg.from.avatar),
                contentDescription = msg.from.name,
                Modifier
                    .graphicsLayer {
                        rotationZ = -shakingAndleBubble.value * 0.6f
                        transformOrigin = TransformOrigin(0f,0f)
                    }
                    .size(40.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            val bubbleColor = WeComposeTheme.colors.bubbleOthers
            Text(
                msg.text,
                Modifier
                    .graphicsLayer {
                        rotationZ = -shakingAndleBubble.value
                        transformOrigin = TransformOrigin(0f,0f)
                    }
                    .drawBehind {
                        val bubble = Path().apply {
                            val rect = RoundRect(
                                10.dp.toPx(),
                                0f,
                                size.width - 10.dp.toPx(),
                                size.height,
                                4.dp.toPx(),
                                4.dp.toPx()
                            )
                            addRoundRect(rect)
                            moveTo(10.dp.toPx(), 15.dp.toPx())
                            lineTo(5.dp.toPx(), 20.dp.toPx())
                            lineTo(10.dp.toPx(), 25.dp.toPx())
                            close()
                        }
                        drawPath(bubble, bubbleColor)
                    }
                    .padding(20.dp, 10.dp),
                color = WeComposeTheme.colors.textPrimaryMe
            )
        }
    }
}

@Composable
fun ChatBottomBar(onBomClicked: () -> Unit, onCameraClicked: () -> Unit) {
    var editingText by remember { mutableStateOf("") }

    Row(
        Modifier
            .fillMaxWidth()
            .background(WeComposeTheme.colors.bottomBar)
            .padding(4.dp, 0.dp)
    ) {
        Icon(
            painterResource(id = R.drawable.baseline_settings_voice_24),
            null,
            Modifier
                .align(Alignment.CenterVertically)
                .padding(4.dp)
                .size(28.dp),
            tint = WeComposeTheme.colors.icon
        )
        BasicTextField(
            editingText,
            { editingText = it },
            Modifier
                .weight(1f)
                .padding(4.dp, 8.dp)
                .height(40.dp)
                .clip(MaterialTheme.shapes.small)
                .background(WeComposeTheme.colors.textFieldBackground)
                .padding(start = 8.dp, top = 10.dp, end = 8.dp),
            cursorBrush = SolidColor(WeComposeTheme.colors.textPrimary)
        )
        Text(
            "\uD83D\uDCA3",
            Modifier
                .clickable(onClick = onBomClicked)
                .padding(4.dp)
                .align(Alignment.CenterVertically),
            fontSize = 24.sp
        )
        Icon(
            painterResource(id = R.drawable.baseline_photo_camera_24),
            contentDescription = null,
            Modifier
                .clickable(onClick = onCameraClicked)
                .align(Alignment.CenterVertically)
                .padding(4.dp)
                .size(28.dp),
            tint = WeComposeTheme.colors.icon
        )
    }
}

fun Modifier.offsetPercent(offsetPercentX: Float = 0f, offsetPercentY: Float = 0f) =
    this.layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        val offsetX = (offsetPercentX * placeable.width).roundToInt()
        val offsetY = (offsetPercentY * placeable.height).roundToInt()
        layout(placeable.width, placeable.height) {
            placeable.placeRelative(offsetX, offsetY)
        }
    }