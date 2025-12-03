package com.zcrain.composeunit.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zcrain.composeunit.R
import com.zcrain.composeunit.widget.AutoBanner

@Composable
fun BannerScreen() {
    val picList = listOf(
        R.mipmap.girl1,
        R.mipmap.girl2,
        R.mipmap.girl3,
        R.mipmap.girl4,
        R.mipmap.girl5,
        R.mipmap.girl6,
        R.mipmap.girl7,
        R.mipmap.girl8,
        R.mipmap.girl9,
        R.mipmap.girl10,
        R.mipmap.girl11,
        R.mipmap.girl12,
        R.mipmap.girl13,
        R.mipmap.girl14
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
    ) {
        AutoBanner(imageList = picList, autoScrollDelayMs = 2000L)
    }
}