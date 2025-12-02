package com.zcrain.composeunit.widget

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlin.math.cos
import kotlin.math.sin
import kotlin.ranges.until


@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    color: Color = Color.Gray,
    size: Dp = 48.dp,
    barCount: Int = 12,
    animationDuration: Int = 1000
) {
    val infiniteTransition = rememberInfiniteTransition(label = "RotationTransition")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing)
        ),
        label = "Rotation"
    )

    val angleStep = 360f / barCount

    Box(
        modifier = modifier
            .size(size)
            .rotate(rotation),
        contentAlignment = Alignment.Center
    ) {
        // 4. 使用 Canvas 绘制所有线条
        Canvas(modifier = Modifier.matchParentSize()) {
            val canvasWidth = size.toPx()
            val canvasHeight = size.toPx()
            val center = Offset(canvasWidth / 2f, canvasHeight / 2f)
            val lineLength = size.toPx() * 0.15f
            val lineThickness = size.toPx() * 0.08f
            val radius = size.toPx() * 0.21f

            for (i in 0 until barCount) {
                val currentAngle = i * angleStep

                val normalizedRotation = rotation % 360f

                var diff = (normalizedRotation - currentAngle) % 360f
                if (diff < 0) diff += 360f

                val brightnessFactor = when {
                    diff < 120f -> lerp(0.2f, 1f, 1f - diff / 120f)
                    else -> 0.2f
                }

                val angleRad = Math.toRadians(currentAngle.toDouble()).toFloat()

                val startX = center.x + radius * cos(angleRad)
                val startY = center.y + radius * sin(angleRad)

                val endX = center.x + (radius + lineLength) * cos(angleRad)
                val endY = center.y + (radius + lineLength) * sin(angleRad)

                drawLine(
                    color = color.copy(alpha = brightnessFactor),
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = lineThickness,
                    cap = StrokeCap.Round
                )
            }
        }
    }
}

@Preview
@Composable
fun LoadingExample() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LoadingIndicator(
            color = Color(0xFFB8A7E0),
            size = 86.dp,
            barCount = 8,
            animationDuration = 800
        )
    }
}