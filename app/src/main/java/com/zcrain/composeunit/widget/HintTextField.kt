package com.zcrain.composeunit.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import kotlin.text.isEmpty

@Composable
fun HintTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    focus: Boolean = false,
    maxLength: Int = 100,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textStyle: TextStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
    hintStyle: TextStyle = TextStyle(fontSize = 16.sp, color = Color.Gray)
) {
    var text by remember { mutableStateOf(value) }
    val focusRequester = remember { FocusRequester() }

    if (focus) {
        focusRequester.requestFocus()
    }

    Box(
        modifier = modifier.background(Color.Transparent),
        contentAlignment = Alignment.CenterStart
    ) {
        if (text.isEmpty()) {
            Text(
                text = hint,
                style = hintStyle
            )
        }

        BasicTextField(
            value = text,
            onValueChange = { newValue ->
                if (newValue.length > maxLength) return@BasicTextField
                text = newValue
                onValueChange(newValue)
            },
            singleLine = true,
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            cursorBrush = SolidColor(Color.Black)
        )
    }
}