package com.zcrain.composeunit.widget

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.layoutId
import com.zcrain.composeunit.R
import com.zcrain.composeunit.utils.toYOffset

/**
 * @Author:CWQ
 * @DATE:2023/12/4
 * @DESC:
 */
enum class CollapsingState {
    EXPANDED,
    COLLAPSED
}


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun CollapsingScafold(backClick: () -> Unit, collapsingContent: @Composable () -> Unit) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val swipingState = rememberSwipeableState(initialValue = CollapsingState.EXPANDED)
        val heightInPx = with(LocalDensity.current) { maxHeight.toPx() }
        val scrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    val delta = available.y
                    return if (delta < 0) {
                        swipingState.performDrag(delta).toYOffset()
                    } else {
                        Offset.Zero
                    }
                }

                override fun onPostScroll(
                    consumed: Offset,
                    available: Offset,
                    source: NestedScrollSource
                ): Offset {
                    val delta = available.y
                    return swipingState.performDrag(delta).toYOffset()
                }

                override suspend fun onPostFling(
                    consumed: Velocity,
                    available: Velocity
                ): Velocity {
                    swipingState.performFling(velocity = available.y)
                    return super.onPostFling(consumed, available)
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .swipeable(
                    state = swipingState,
                    thresholds = { _, _ -> FractionalThreshold(0.4f) },
                    orientation = Orientation.Vertical,
                    anchors = mapOf(
                        0f to CollapsingState.COLLAPSED,
                        heightInPx to CollapsingState.EXPANDED
                    )
                )
                .nestedScroll(scrollConnection)
        ) {
            CollapsingHeader(
                progress = if (swipingState.progress.to == CollapsingState.COLLAPSED) swipingState.progress.fraction else 1f - swipingState.progress.fraction,
                backClick = backClick
            ) {
                collapsingContent()
            }
        }
    }
}


@Composable
private fun CollapsingHeader(
    progress: Float,
    backClick: () -> Unit,
    scrollableBody: @Composable () -> Unit
) {
    MotionLayout(
        start = collapsingConstraintSetStart(),
        end = collapsingConstraintSetEnd(),
        progress = progress,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.mipmap.girl1),
            contentDescription = "bgIv",
            modifier = Modifier
                .layoutId("bgIv")
                .background(Color.White),
            contentScale = ContentScale.FillWidth,
            alpha = 1f - progress
        )
        IconButton(
            onClick = { backClick() },
            modifier = Modifier.layoutId("backBtn")
        ) {
            Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "")
        }
        Text(
            text = "CollapsingTitle",
            modifier = Modifier.layoutId("title")
                .wrapContentHeight(),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Box(modifier = Modifier.layoutId("content")) {
            scrollableBody()
        }
    }
}


@Composable
private fun collapsingConstraintSetStart() = ConstraintSet {
    val bgIv = createRefFor("bgIv")
    val backBtn = createRefFor("backBtn")
    val title = createRefFor("title")
    val content = createRefFor("content")
    this.constrain(bgIv) {
        width = Dimension.matchParent
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(parent.top)
    }
    this.constrain(backBtn) {
        start.linkTo(parent.start)
        top.linkTo(parent.top)
    }
    this.constrain(title) {
        bottom.linkTo(bgIv.bottom, margin = 16.dp)
        start.linkTo(parent.start, margin = 16.dp)
    }
    this.constrain(content) {
        width = Dimension.matchParent
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(title.bottom, margin = 16.dp)
    }
}


@Composable
private fun collapsingConstraintSetEnd() = ConstraintSet {
    val bgIv = createRefFor("bgIv")
    val backBtn = createRefFor("backBtn")
    val title = createRefFor("title")
    val content = createRefFor("content")
    this.constrain(bgIv) {
        width = Dimension.matchParent
        height = Dimension.value(56.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(parent.top)
    }
    this.constrain(backBtn) {
        start.linkTo(parent.start)
        top.linkTo(parent.top)
    }
    this.constrain(title) {
        top.linkTo(backBtn.top)
        bottom.linkTo(backBtn.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }
    this.constrain(content) {
        width = Dimension.matchParent
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(bgIv.bottom)
    }
}