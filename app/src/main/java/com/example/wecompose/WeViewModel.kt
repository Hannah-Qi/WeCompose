package com.example.wecompose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wecompose.data.Chat
import com.example.wecompose.data.Msg
import com.example.wecompose.data.User
import com.example.wecompose.ui.theme.WeComposeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class WeViewModel: ViewModel() {
    var selectedTab by mutableIntStateOf(0)
    var theme by mutableStateOf(WeComposeTheme.Theme.Light)
    var currentChat: Chat? by mutableStateOf(null)
    var chatting by mutableStateOf(false)



//    private fun coroutinesStyle() = CoroutineScope(Dispatchers.Main).launch {
//
//    }
    fun someFun() {
        viewModelScope.launch {

        }
    }

    fun startChat(chat: Chat) {
        chatting = true
        currentChat = chat
    }

    fun endChat(): Boolean {
        if(chatting) {
            chatting = false
            return true
        }
        return false
    }

    fun boom(chat: Chat) {
        chat.msgs.add(
            Msg(User.Me,"\uD83D\uDCA3","15.10")
                .apply { read = true }
        )
    }

    var chats by mutableStateOf(
        listOf(
            Chat(
                friend = User("gaolaoshi","金晨曦",R.drawable.avatar_gaolaoshi),
                mutableStateListOf(
                    Msg(User("gaolaoshi","金晨曦", R.drawable.avatar_gaolaoshi),"锄禾日当午","14:20"),
                    Msg(User.Me,"汗滴禾下土","14:20"),
                    Msg(User("gaolaoshi","金晨曦", R.drawable.avatar_gaolaoshi), "谁知盘中餐","14:20"),
                    Msg(User.Me, text = "粒粒皆辛苦", time = "14:20"),
                    Msg(User("gaolaoshi","金晨曦", R.drawable.avatar_gaolaoshi), text = "唧唧复唧唧，木兰当户织。不闻机杼声，唯闻女叹息。", time = "14:20"),
                    Msg(User.Me, text = "双兔傍地走，安能辨我是雄雌？", time = "14:20"),
                    Msg(User("gaolaoshi","金晨曦", R.drawable.avatar_gaolaoshi), text = "床前明月光，疑是地上霜。", time = "14:20"),
                    Msg(User.Me, text = "吃饭吧？", time = "14:20"),
                )
            ),
            Chat(
                friend = User("qitingting","齐婷婷",R.drawable.qitingting),
                mutableStateListOf(
                    Msg(User("qitingting", "齐婷婷", R.drawable.qitingting), text = "哈哈哈", time = "13:48"),
                    Msg(User.Me, text = "哈哈昂", time = "13:48"),
                    Msg(User("qitingting", "齐婷婷", R.drawable.qitingting), text = "你笑个屁呀", time = "13:48").apply { read = false },
                )
            )
        )
    )
}