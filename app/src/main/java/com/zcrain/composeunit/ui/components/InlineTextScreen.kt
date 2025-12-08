package com.zcrain.composeunit.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zcrain.composeunit.R

@Composable
fun InlineTextScreen() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        AndroidBotHeadlineText(modifier = Modifier.fillMaxSize())
    }
}


@Composable
fun AndroidBotHeadlineText(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        val animatedBot = "animatedBot"
        val text = buildAnnotatedString {
            append("Customize\nyour own\n")
            appendInlineContent(animatedBot)
            append("\n\nAndroid bot")
        }
        var placeHolderSize by remember {
            mutableStateOf(220.sp)
        }
        val inlineContent = mapOf(
            Pair(
                animatedBot,
                InlineTextContent(
                    Placeholder(
                        width = placeHolderSize,
                        height = placeHolderSize,
                        placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
                    ),
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.android_robot),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .fillMaxSize(),
                    )
                },
            ),
        )
        BasicText(
            text,
            modifier = Modifier
                .align(Alignment.Center),
            style = TextStyle(
                fontSize = 60.sp,
                textAlign = TextAlign.Center,
                color = Color.Black,
                lineHeight = 55.sp,
            ),
            inlineContent = inlineContent,
        )
    }
}