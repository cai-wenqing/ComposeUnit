package com.zcrain.composeunit.ui.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zcrain.composeunit.widget.AutoCompleteTextField
import com.zcrain.composeunit.widget.CodeInputField
import com.zcrain.composeunit.widget.HintTextField

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CodeInputScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp, horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {

        var hintInputValue by remember { mutableStateOf("") }
        HintTextField(
            value = hintInputValue,
            onValueChange = { hintInputValue = it },
            hint = "请输入内容",
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFDCDCDC), RoundedCornerShape(5.dp))
                .padding(horizontal = 5.dp, vertical = 8.dp)
        )

        var codeValue by remember { mutableStateOf("") }
        CodeInputField(
            value = codeValue,
            modifier = Modifier,
            onValueChange = { pwd ->
                codeValue = pwd
            },
            onVerify = { value ->
                Log.d("CodeInputScreen", "onVerify: $value")
            },
        )

        var autoCompleteValue by remember { mutableStateOf("") }
        AutoCompleteTextField(
            allOptions = listOf(
                "@gmail.com",
                "@yahoo.com",
                "@icloud.com",
                "@outlook.com"
            ),
            value = autoCompleteValue,
            hint = "请输入",
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFDCDCDC), RoundedCornerShape(5.dp))
                .padding(horizontal = 5.dp, vertical = 4.dp),
        ) { value ->
            Log.d("CodeInputScreen", "AutoCompleteTextField: $value")
        }
    }
}