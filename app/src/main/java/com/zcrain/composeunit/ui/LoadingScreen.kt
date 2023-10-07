package com.zcrain.composeunit.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zcrain.composeunit.widget.LoadingView
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

/**
 * @Author:CWQ
 * @DATE:2023/10/7
 * @DESC:
 */
@Composable
fun LoadingScreen() {
    var progress by remember { mutableStateOf(0) }
    LaunchedEffect(progress) {
        flow {
            if (progress <= 100) {
                delay(100)
                emit(2)
            } else {
                progress = 0
            }
        }.collect {
            progress += it
        }
    }
    Box(
        modifier = Modifier.fillMaxSize().background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        LoadingView(Modifier.size(300.dp), progress)
    }
}