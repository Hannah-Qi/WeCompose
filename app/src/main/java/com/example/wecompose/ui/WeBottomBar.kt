package com.example.wecompose.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wecompose.R
import com.example.wecompose.ui.theme.WeComposeTheme

@Composable
fun WeBottomBar(selected: Int, onSelectedChanged: (Int) -> Unit) {

    Row(Modifier.background(WeComposeTheme.colors.bottomBar)) {
        TabItem(
            if(selected == 0) R.drawable.tab_1_0 else R.drawable.tab_1_1, "聊天",
            if(selected == 0) WeComposeTheme.colors.iconCurrent else WeComposeTheme.colors.icon,
            Modifier
                .weight(1f)
                .clickable {
                    onSelectedChanged(0)
                }
        )
        TabItem(
            if(selected == 1) R.drawable.tab_2_0 else R.drawable.tab_2_1, "通讯录",
            if(selected == 1) WeComposeTheme.colors.iconCurrent else WeComposeTheme.colors.icon,
            Modifier
                .weight(1f)
                .clickable {
                    onSelectedChanged(1)
                }
        )
        TabItem(
            if(selected == 2) R.drawable.tab_3_0 else R.drawable.tab_3_1, "发现",
            if(selected == 2) WeComposeTheme.colors.iconCurrent else WeComposeTheme.colors.icon,
            Modifier
                .weight(1f)
                .clickable {
                    onSelectedChanged(2)
                }
        )
        TabItem(
            if(selected == 3) R.drawable.tab_4_0 else R.drawable.tab_4_1, "我",
            if(selected == 3) WeComposeTheme.colors.iconCurrent else WeComposeTheme.colors.icon,
            Modifier
                .weight(1f)
                .clickable {
                    onSelectedChanged(3)
                }
        )
    }
}

@Composable
fun TabItem(@DrawableRes iconId: Int, title: String, tint: Color, modifier: Modifier = Modifier) {
    Column(
        modifier.padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(painterResource(iconId), title, Modifier.size(24.dp), tint = tint)
        Text(title, fontSize = 11.sp, color = tint)
    }
}

@Preview(showBackground = true)
@Composable
fun WeBottomBarPreviewDark() {
    WeComposeTheme(WeComposeTheme.Theme.Dark) {
        var selectedTab by remember {
            mutableIntStateOf(0)
        }
        WeBottomBar(selectedTab) { selectedTab = it }
    }
}

@Preview(showBackground = true)
@Composable
fun WeBottomBarPreviewLight() {
    WeComposeTheme(WeComposeTheme.Theme.Light) {
        var selectedTab by remember {
            mutableIntStateOf(0)
        }
        WeBottomBar(selectedTab) { selectedTab = it }
    }
}

@Preview(showBackground = true)
@Composable
fun WeBottomBarPreviewNewYear() {
    WeComposeTheme(WeComposeTheme.Theme.NewYear) {
        var selectedTab by remember {
            mutableIntStateOf(0)
        }
        WeBottomBar(selectedTab) { selectedTab = it }
    }
}

@Preview(showBackground = true)
@Composable
fun TabItemPreview() {
    TabItem(iconId = R.drawable.tab_1_0, title = "聊天", tint = WeComposeTheme.colors.icon)
}