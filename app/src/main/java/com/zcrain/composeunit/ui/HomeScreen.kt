package com.zcrain.composeunit.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zcrain.composeunit.NavigationActions
import com.zcrain.composeunit.NavigationConfig

/**
 * @Author:CWQ
 * @DATE:2023/10/7
 * @DESC:
 */
@Composable
fun HomeScreen(navController: NavController) {
    val navAction = remember(navController) {
        NavigationActions(navController)
    }
    val menus = arrayListOf(NavigationConfig.ROUTE_LOADING, NavigationConfig.ROUTE_CODE_INPUT)
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(10.dp)
    ) {
        items(menus) { string ->
            MenuView(string) {
                navAction.navStart(string)
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
            .padding(horizontal = 6.dp, vertical = 2.dp)
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