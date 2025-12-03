package com.zcrain.composeunit.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar
import kotlin.collections.indices
import kotlin.collections.isNotEmpty
import kotlin.collections.lastIndex
import kotlin.collections.map
import kotlin.collections.minByOrNull
import kotlin.let
import kotlin.math.abs
import kotlin.ranges.coerceAtMost
import kotlin.ranges.coerceIn
import kotlin.text.padStart
import kotlin.text.toInt

@Composable
fun DatePickerWithSnapper(
    modifier: Modifier = Modifier,
    itemCount: Int = 5,
    initialDate: Triple<Int, Int, Int>? = null,
    onDateSelected: (year: Int, month: Int, day: Int) -> Unit
) {
    val calendar = Calendar.getInstance()

    val defaultYear = initialDate?.first ?: calendar.get(Calendar.YEAR)
    val defaultMonth = initialDate?.second ?: (calendar.get(Calendar.MONTH) + 1)
    val defaultDay = initialDate?.third ?: calendar.get(Calendar.DAY_OF_MONTH)

    val years = (1950..2100).map { it.toString() }
    val months = (1..12).map { it.toString().padStart(2, '0') }

    var selectedYear by remember { mutableIntStateOf(defaultYear) }
    var selectedMonth by remember { mutableIntStateOf(defaultMonth) }
    var selectedDay by remember { mutableIntStateOf(defaultDay) }

    val days by remember(selectedYear, selectedMonth) {
        mutableStateOf(
            (1..getDaysInMonth(selectedYear, selectedMonth))
                .map { it.toString().padStart(2, '0') })
    }

    val dayListState =
        rememberLazyListState(initialFirstVisibleItemIndex = (selectedDay - 1).coerceAtMost(days.lastIndex))

    LaunchedEffect(selectedYear, selectedMonth) {
        val newIndex = (selectedDay - 1).coerceAtMost(days.lastIndex)
        selectedDay = days[newIndex].toInt()
        dayListState.scrollToItem(newIndex)
        onDateSelected(selectedYear, selectedMonth, selectedDay)
    }

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .height(48.dp)
                .padding(horizontal = 12.dp)
                .fillMaxWidth()
                .background(Color(0xFFEFE8FF), RoundedCornerShape(12.dp))
                .align(Alignment.Center)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            WheelPickerWithSnap(
                items = years,
                endLabel = "Year",
                itemCount = itemCount,
                initialIndex = years.indexOf(selectedYear.toString()),
                onItemSelected = { idx ->
                    selectedYear = years[idx].toInt()
                }
            )
            WheelPickerWithSnap(
                items = months,
                endLabel = "Month",
                itemCount = itemCount,
                initialIndex = defaultMonth - 1,
                onItemSelected = { idx ->
                    selectedMonth = months[idx].toInt()
                }
            )
            WheelPickerWithSnap(
                items = days,
                endLabel = "Day",
                itemCount = itemCount,
                initialIndex = (selectedDay - 1).coerceAtMost(days.lastIndex),
                selectedIndex = (selectedDay - 1).coerceAtMost(days.lastIndex),
                onItemSelected = { idx ->
                    selectedDay = days[idx].toInt()
                    onDateSelected(selectedYear, selectedMonth, selectedDay)
                },
                listState = dayListState
            )
        }
    }

}

private fun getDaysInMonth(year: Int, month: Int): Int {
    return when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) 29 else 28
        else -> 30
    }
}

@Composable
private fun WheelPickerWithSnap(
    items: List<String>,
    initialIndex: Int,
    endLabel: String = "",
    selectedIndex: Int? = null,
    onItemSelected: (Int) -> Unit,
    itemCount: Int,
    itemHeight: Dp = 48.dp,
    listState: LazyListState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)
) {
    val wheelHeight = itemHeight * itemCount
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    val centerPadding = (wheelHeight - itemHeight) / 2
    var internalSelectedIndex by remember { mutableIntStateOf(initialIndex) }

    LaunchedEffect(selectedIndex) {
        selectedIndex?.let {
            internalSelectedIndex = it.coerceIn(items.indices)
        }
    }

    Box(
        modifier = Modifier
            .height(wheelHeight)
            .width(100.dp),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = centerPadding, bottom = centerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(items) { index, item ->
                val isSelected = index == internalSelectedIndex
                Text(
                    text = item + endLabel,
                    fontSize = 15.sp,
                    fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                    color = if (isSelected) Color(0xFF6834E6) else Color(0xFF666666),
                    modifier = Modifier
                        .height(itemHeight)
                        .padding(vertical = 4.dp)
                        .wrapContentHeight(Alignment.CenterVertically)
                )
            }
        }

        LaunchedEffect(listState) {
            snapshotFlow { listState.isScrollInProgress }
                .collect { isScrolling ->
                    if (!isScrolling) {
                        val layoutInfo = listState.layoutInfo
                        val visibleItems = layoutInfo.visibleItemsInfo
                        if (visibleItems.isNotEmpty()) {
                            val center =
                                layoutInfo.viewportStartOffset + layoutInfo.viewportSize.height / 2f
                            val nearest = visibleItems.minByOrNull {
                                val itemCenter = it.offset + it.size / 2f
                                abs(itemCenter - center)
                            }
                            nearest?.let {
                                val newIndex = it.index
                                if (newIndex != internalSelectedIndex) {
                                    internalSelectedIndex = newIndex
                                    onItemSelected(newIndex)
                                }
                            }
                        }
                    }
                }
        }
    }
}
