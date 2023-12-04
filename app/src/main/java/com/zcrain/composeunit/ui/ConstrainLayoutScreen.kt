package com.zcrain.composeunit.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zcrain.composeunit.widget.CollapsingScafold

/**
 * @Author:CWQ
 * @DATE:2023/12/4
 * @DESC:
 */

@Composable
fun ConstrainLayoutScreen() {
    CollapsingScafold(backClick = {}) {
        ScrollableContent()
    }
}


@Composable
private fun ScrollableContent() {
    val items = (1..30).toList()
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(items) {
            Card {
                Text(
                    text = "Index: $it",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}