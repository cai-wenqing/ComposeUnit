package com.zcrain.composeunit.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zcrain.composeunit.widget.LoadingIndicator

/**
 * @Author:CWQ
 * @DATE:2023/10/7
 * @DESC:
 */
@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoadingIndicator(
            color = Color.Black,
            size = 86.dp,
            barCount = 8,
            animationDuration = 800
        )
    }
}