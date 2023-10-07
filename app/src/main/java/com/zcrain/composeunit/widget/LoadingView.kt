package com.zcrain.composeunit.widget

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

/**
 * @Author:CWQ
 * @DATE:2023/9/28
 * @DESC:
 */

@OptIn(ExperimentalTextApi::class)
@Composable
fun LoadingView(modifier: Modifier, progress: Int) {
    var centerX by remember { mutableStateOf(0f) }
    var centerY by remember { mutableStateOf(0f) }
    val outRadius = centerX.coerceAtLeast(centerY) * 3 / 5
    val transition = rememberInfiniteTransition(label = "transition")
    val start by transition.animateFloat(
        0f,
        360f,
        animationSpec = InfiniteRepeatableSpec(tween(durationMillis = 1500, easing = LinearEasing)),
        label = "start"
    )
    val sweepDif by animateFloatAsState(
        progress * 3.6f,
        tween(100),
        label = "sweepDif"
    )
    val circleColorList = listOf(
        Color(0xff0055ff),
        Color(0xffFF1d1d),
        Color(0xffFFcf93),
        Color(0xffFFEBE7),
        Color(0xff39E7FF),
        Color(0xff43B988),
        Color(0xffFE6672),
        Color(0xffEB4C40),
        Color(0xffE6A639),
        Color(0xff2951ef),
        Color(0xffFFA300),
    )
    val ringColor by animateColorAsState(
        circleColorList[progress / 10],
        animationSpec = tween(1000),
        label = "ringColor"
    )
    Box(modifier = modifier.background(Color.Black)) {
        Canvas(modifier = modifier) {
            centerX = this.size.center.x
            centerY = this.size.center.y
            drawArc(
                brush = Brush.verticalGradient(listOf(ringColor, Color.White)),
                startAngle = start,
                sweepAngle = sweepDif,
                useCenter = false,
                style = Stroke(30f, cap = StrokeCap.Round),
                topLeft = Offset(centerX - outRadius, centerY - outRadius),
                size = Size(outRadius * 2, outRadius * 2)
            )
        }
        Text(
            text = "${progress}%",
            modifier = Modifier.align(Alignment.Center),
            style = TextStyle(
                fontSize = 35.sp,
                color = Color.White,
                drawStyle = Stroke(
                    width = 3f,
                    cap = StrokeCap.Square
                )
            )
        )
    }
}


@Preview
@Composable
fun PreviewTest() {
//    LoadingView(Modifier, 0)
}