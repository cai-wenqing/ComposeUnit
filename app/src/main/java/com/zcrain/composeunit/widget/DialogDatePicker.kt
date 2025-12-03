package com.zcrain.composeunit.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun DialogDatePicker(
    title: String,
    modifier: Modifier = Modifier,
    onCancel: () -> Unit,
    initialDate: Triple<Int, Int, Int>? = null,
    onDateSelected: (String) -> Unit
) {

    var selectDate by remember { mutableStateOf(initialDate) }

    Dialog(
        onDismissRequest = {
            onCancel()
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .clickable(indication = null, interactionSource = null) {
                    onCancel()
                },
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(20.dp))
                    .padding(top = 20.dp, bottom = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clickable(indication = null, interactionSource = null) {

                        },
                ) {
                    Text(
                        text = title,
                        modifier = Modifier.align(Alignment.Center),
                        color = Color(0xFF333333),
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                    )

                    Text(
                        text = "Confirm",
                        color = Color(0xFF6834E6),
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .clickable(indication = null, interactionSource = null) {
                                val year = selectDate?.first ?: 2025
                                val month = selectDate?.second ?: 1
                                val day = selectDate?.third ?: 1
                                onDateSelected(formatDateStr(year, month, day))
                            },
                    )
                }

                DatePickerWithSnapper(
                    modifier = Modifier.padding(top = 20.dp),
                    initialDate = initialDate,
                ) { year, month, day ->
                    selectDate = Triple(year, month, day)
                }
            }
        }
    }
}

private fun formatDateStr(year: Int, month: Int, day: Int): String {
    val monthStr = if (month < 10) "0$month" else month.toString()
    val dayStr = if (day < 10) "0$day" else day.toString()
    return "$year-$monthStr-$dayStr"
}