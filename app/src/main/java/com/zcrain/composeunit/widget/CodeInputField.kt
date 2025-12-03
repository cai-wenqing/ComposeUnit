package com.zcrain.composeunit.widget

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CodeInputField(
    value: String,
    modifier: Modifier = Modifier,
    itemHeight: Dp = 50.dp,
    itemMargin: Dp = 8.dp,
    onValueChange: (String) -> Unit = {},
    onVerify: (String) -> Unit = {},
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(value) {
        if (value.isEmpty()) {
            focusRequester.requestFocus()
        }
    }

    // 动画值用于光标闪烁
    val cursorAlpha by animateFloatAsState(
        targetValue = if (isFocused) 1f else 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                0f at 0 using LinearEasing
                1f at 500 using LinearEasing
                0f at 1000 using LinearEasing
            }
        ),
        label = "cursor_blink"
    )

    BasicTextField(
        value = value,
        onValueChange = { newText ->
            if (newText.all { it.isDigit() } && newText.length <= 6) {
                onValueChange(newText)
                if (newText.length == 6) {
                    onVerify(newText)
                    focusManager.clearFocus()
                }
            }
        },
        singleLine = true,
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
                if (focusState.isFocused) {
                    keyboardController?.show()
                }
            },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done
        ),
        cursorBrush = SolidColor(Color.Unspecified),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(itemMargin)
            ) {
                repeat(6) { index ->
                    val digit = value.getOrNull(index)
                    val digitFocused = isFocused && index == value.length

                    CodeDigit(
                        digit = digit,
                        isFocused = digitFocused,
                        cursorAlpha = cursorAlpha,
                        modifier = Modifier
                            .height(itemHeight)
                            .weight(1f),
                        onClick = {
                            focusRequester.requestFocus()
                            keyboardController?.show()
                        }
                    )
                }
            }
        }
    )
}

@Composable
private fun CodeDigit(
    digit: Char?,
    isFocused: Boolean,
    cursorAlpha: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color(0xFFDAD8DF),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (digit != null) {
            Text(
                text = digit.toString(),
                fontSize = 26.sp,
                color = Color(0xFF030509),
                fontWeight = FontWeight.Bold
            )
        }

        if (isFocused) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.6f)
                    .width(1.dp)
                    .background(
                        Color(0xFF6834E6).copy(alpha = cursorAlpha)
                    )
            )
        }
    }
}