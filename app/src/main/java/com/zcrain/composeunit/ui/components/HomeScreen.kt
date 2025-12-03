package com.zcrain.composeunit.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zcrain.composeunit.NaviConfig


@Composable
fun HomeScreen(navList: SnapshotStateList<String>) {
    val menus = arrayListOf(
        NaviConfig.ROUTE_LOADING,
        NaviConfig.ROUTE_INPUT,
        NaviConfig.ROUTE_BIG_POSTER,
        NaviConfig.ROUTE_BANNER,
        NaviConfig.ROUTE_SELECTOR,
    )
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp, horizontal = 10.dp)
    ) {
        items(menus) { item ->
            MenuView(item) {
                navList.add(item)
            }
        }
    }
}


@Composable
fun MenuView(string: String, modifier: Modifier = Modifier, onClick: (() -> Unit)? = null) {
    Text(
        text = string,
        modifier = modifier
            .border(width = 1.dp, Color.Blue, shape = RoundedCornerShape(5.dp))
            .padding(horizontal = 6.dp, vertical = 4.dp)
            .clickable {
                onClick?.invoke()
            },
        color = Color.Black,
        fontSize = 20.sp,
        fontStyle = FontStyle.Italic,
        textAlign = TextAlign.Center,
        fontFamily = FontFamily.Serif,
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MenuView("Android")
}