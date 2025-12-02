package com.zcrain.composeunit.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.zcrain.composeunit.widget.VerificationCodeTextField

/**
 * @Author:CWQ
 * @DATE:2023/10/7
 * @DESC:
 */

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CodeInputScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val context = LocalContext.current
        VerificationCodeTextField(Modifier) {
            Toast.makeText(context, "验证码:$it", Toast.LENGTH_SHORT).show()
        }
    }
}