package com.example.wecompose.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.wecompose.WeViewModel
import kotlinx.coroutines.launch

@Composable
fun Home(viewModel: WeViewModel) {
    Column {
        val pagerState = rememberPagerState { 4 }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when(page) {
                0 -> ChatList(viewModel.chats)
                1 -> Box(Modifier.fillMaxSize()) { Text("通讯录") }
                2 -> Box(Modifier.fillMaxSize()) { Text("发现") }
                3 -> Box(Modifier.fillMaxSize()) { Text("我的") }
            }
        }

        val scope = rememberCoroutineScope()

        WeBottomBar(pagerState.currentPage) { page ->
            scope.launch {
                pagerState.animateScrollToPage(page)
            }
        }

    }
}