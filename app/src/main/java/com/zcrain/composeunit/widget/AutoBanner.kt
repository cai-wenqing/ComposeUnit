package com.zcrain.composeunit.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay

@Composable
fun AutoBanner(
    imageList: List<Int>,
    modifier: Modifier = Modifier,
    pageHeight: Dp = 200.dp,
    autoScrollDelayMs: Long = 3000L,
    onItemClick: (index: Int) -> Unit = {}
) {
    if (imageList.isEmpty()) return

    val pageCount = imageList.size

    val startIndex = Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2 % pageCount)
    val pagerState = rememberPagerState(initialPage = startIndex, pageCount = { Int.MAX_VALUE })

    LaunchedEffect(pagerState, autoScrollDelayMs) {
        while (true) {
            delay(autoScrollDelayMs)
            if (!pagerState.isScrollInProgress) {
                val next = pagerState.currentPage + 1
                pagerState.animateScrollToPage(next)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(pageHeight)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val index = page % pageCount
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onItemClick(index) }
            ) {
                AsyncImage(
                    model = imageList[index],
                    contentDescription = "banner image $index",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}