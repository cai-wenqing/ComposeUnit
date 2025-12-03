package com.zcrain.composeunit.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastFilteredMap
import kotlin.collections.forEach
import kotlin.collections.isNotEmpty
import kotlin.text.contains
import kotlin.text.indexOf
import kotlin.text.isEmpty
import kotlin.text.isNotEmpty
import kotlin.text.lowercase
import kotlin.text.startsWith
import kotlin.text.substring
import kotlin.text.trim

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoCompleteTextField(
    allOptions: List<String>,
    modifier: Modifier = Modifier,
    value: String = "",
    hint: String = "",
    focus: Boolean = false,
    onValueChange: (String) -> Unit = {}
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(value)) }
    var expanded by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    if (focus) {
        focusRequester.requestFocus()
    }

    val filteredOptions: List<String> = remember(textFieldValue.text) {
        if (textFieldValue.text.isEmpty()) {
            emptyList()
        } else {
            val inputValue = textFieldValue.text.lowercase().trim()
            allOptions.fastFilteredMap(
                predicate = {
                    return@fastFilteredMap if (inputValue.contains("@")) {
                        val afterAt = inputValue.substring(inputValue.indexOf("@"))
                        it.lowercase().startsWith(afterAt)
                    } else {
                        true
                    }
                },
                transform = {
                    return@fastFilteredMap if (inputValue.contains("@")) {
                        val afterAt = inputValue.substring(inputValue.indexOf("@"))
                        "$inputValue${it.substring(afterAt.length)}"
                    } else {
                        "$inputValue$it"
                    }
                })
        }
    }

    ExposedDropdownMenuBox(
        expanded = expanded && filteredOptions.isNotEmpty(),
        modifier = modifier.fillMaxWidth(),
        onExpandedChange = {
        }
    ) {
        if (textFieldValue.text.isEmpty() && hint.isNotEmpty()) {
            Text(
                text = hint,
                style = TextStyle(fontSize = 15.sp, color = Color(0xFF999999)),
                modifier = Modifier
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, true)
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }
        BasicTextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                textFieldValue = newValue
                onValueChange(newValue.text)
                expanded = true
            },
            singleLine = true,
            textStyle = TextStyle(fontSize = 15.sp, color = Color(0xFF333333)),
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, true)
                .fillMaxWidth()
                .padding(vertical = 6.dp)
                .focusRequester(focusRequester),
            cursorBrush = SolidColor(Color.Black)
        )

        ExposedDropdownMenu(
            expanded = expanded && filteredOptions.isNotEmpty(),
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                .border(1.dp, Color(0xFF6834E6), RoundedCornerShape(16.dp))
                .padding(4.dp),
            tonalElevation = 0.dp,
            shadowElevation = 0.dp,
            containerColor = Color.Transparent,
            onDismissRequest = {
            }
        ) {
            filteredOptions.forEach { option ->
                CustomPressedBox(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    normalColor = Color.Transparent,
                    pressedColor = Color(0xFF6834E6),
                    contentAlignment = Alignment.CenterStart,
                    onClick = {
                        textFieldValue = textFieldValue.copy(
                            text = option,
                            selection = TextRange(option.length)
                        )
                        onValueChange(option)
                        expanded = false
                    }
                ) { pressed ->
                    Text(
                        text = option,
                        color = if (pressed) Color.White else Color(0xFF666666),
                        fontSize = 15.sp,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun AutoCompleteTextFieldPreview() {
    AutoCompleteTextField(
        allOptions = listOf(
            "@gmail.com",
            "@yahoo.com",
            "@icloud.com",
            "@outlook.com"
        )
    )
}