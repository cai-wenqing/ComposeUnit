package com.zcrain.composeunit.ui.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zcrain.composeunit.widget.DialogDatePicker

@Composable
fun SelectorScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        var showDateSelector by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            Text(
                text = "Date Selector",
                modifier = Modifier
                    .border(width = 1.dp, Color.Blue, shape = RoundedCornerShape(5.dp))
                    .padding(horizontal = 6.dp, vertical = 4.dp)
                    .clickable(indication = null, interactionSource = null) {
                        showDateSelector = true
                    },
                color = Color.Black,
                fontSize = 20.sp,
            )
        }

        if (showDateSelector) {
            DialogDatePicker(
                title = "Select Date",
                onCancel = {
                    showDateSelector = false
                },
                initialDate = null,
                onDateSelected = { dateStr ->
                    Log.d("SelectorScreen", "SelectorScreen: $dateStr")
                    showDateSelector = false
                }
            )
        }
    }
}