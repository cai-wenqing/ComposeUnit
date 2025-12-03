package com.zcrain.composeunit.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape

@Composable
fun CustomPressedBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    contentAlignment: Alignment = Alignment.TopStart,
    shape: Shape = RectangleShape,
    normalColor: Color = Color.Transparent,
    pressedColor: Color = Color.Transparent,
    content: @Composable (Boolean) -> Unit
) {
    val interact = remember { MutableInteractionSource() }
    val pressedState = interact.collectIsPressedAsState()

    Box(
        modifier = modifier
            .clip(shape)
            .background(
                color = if (pressedState.value) pressedColor else normalColor,
            )
            .clickable(
                interactionSource = interact,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = contentAlignment
    ) {
        content(pressedState.value)
    }
}