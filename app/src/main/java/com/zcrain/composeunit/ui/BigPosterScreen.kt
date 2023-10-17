package com.zcrain.composeunit.ui

import android.animation.ValueAnimator
import android.content.Context
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.core.animation.addListener
import com.zcrain.composeunit.R

/**
 * @Author:CWQ
 * @DATE:2023/10/17
 * @DESC:
 */

object BigPosterManager {
    //动画时长
    const val animatorDuration = 200L

    //当前点击宫格index
    var currentClickCellIndex = 0

    //所有宫格的位置
    var cellOffsetMap = mutableMapOf<Int, Offset>()

    //下拉松手时大图的透明度
    var dragEndAlpha = 0F
}


val picList = listOf(
    R.mipmap.girl1,
    R.mipmap.girl2,
    R.mipmap.girl3,
    R.mipmap.girl4,
    R.mipmap.girl5,
    R.mipmap.girl6,
    R.mipmap.girl7,
    R.mipmap.girl8,
    R.mipmap.girl9,
    R.mipmap.girl10,
    R.mipmap.girl11,
    R.mipmap.girl12,
    R.mipmap.girl13,
    R.mipmap.girl14
)


@Composable
fun BigPosterScreen() {
    val context = LocalContext.current
    // 记录是否展示大图的状态
    val showBigImageStatus = remember {
        mutableStateOf(false)
    }
    // 记录列表item的大小
    val cellSize = remember {
        mutableStateOf(IntSize(0, 0))
    }
    // 透明度是否逐渐增大
    val alphaIncrease = remember {
        mutableStateOf(false)
    }
    // 透明度动画，当showBigImageStatus为true也就是由小变大时，targetValue应该是1，反之则为0
    // animationSpec设置的是1s的时长
    val alpha = animateFloatAsState(
        targetValue = if (alphaIncrease.value) 1F else 0F,
        label = "",
        animationSpec = tween(BigPosterManager.animatorDuration.toInt())
    )
    // 大图x轴的偏移量
    val bigImageOffsetX = remember {
        mutableStateOf(0F)
    }
    // 大图y轴的偏移量
    val bigImageOffsetY = remember {
        mutableStateOf(0F)
    }
    // 大图宽度
    val bigImageSizeWidth = remember {
        mutableStateOf(0)
    }
    // 大图高度
    val bigImageSizeHeight = remember {
        mutableStateOf(0)
    }

    // 下拉状态
    val dragStatus = remember {
        mutableStateOf(false)
    }
    // 下拉时透明度
    val dragAlpha = remember {
        mutableStateOf(1F)
    }

    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        items(picList.size) { index ->
            Image(
                painter = painterResource(picList[index]),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .clickable {
                        if (showBigImageStatus.value) {
                            return@clickable
                        }
                        BigPosterManager.currentClickCellIndex = index
                        showBigImageStatus.value = true
                        alphaIncrease.value = true

                        val currentOffset = BigPosterManager.cellOffsetMap[index] ?: Offset(0f, 0f)
                        animatorOfFloat(bigImageOffsetX, currentOffset.x, 0f)
                        animatorOfFloat(bigImageOffsetY, currentOffset.y, 0f)
                        animatorOfInt(
                            bigImageSizeWidth,
                            cellSize.value.width,
                            getScreenWidth(context)
                        )
                        animatorOfInt(
                            bigImageSizeHeight,
                            cellSize.value.height,
                            getScreenHeight(context)
                        )
                    }
                    .onGloballyPositioned {
                        val rect = it.boundsInRoot()
                        val offset = Offset(rect.left, rect.top)
                        BigPosterManager.cellOffsetMap[index] = offset
                        cellSize.value = it.size
                    }
            )
        }
    }

    if (showBigImageStatus.value) {
        BigImage(
            bigImageSizeWidth.value,
            bigImageSizeHeight.value,
            bigImageOffsetX.value,
            bigImageOffsetY.value,
            if (dragStatus.value) dragAlpha.value else alpha.value,
            click = {
                if (!showBigImageStatus.value) {
                    return@BigImage
                }

                alphaIncrease.value = false
                val currentCellOffset =
                    BigPosterManager.cellOffsetMap[BigPosterManager.currentClickCellIndex]
                        ?: Offset(0F, 0F)
                animatorOfFloat(state = bigImageOffsetX, bigImageOffsetX.value, currentCellOffset.x)
                animatorOfFloat(state = bigImageOffsetY, bigImageOffsetY.value, currentCellOffset.y)
                animatorOfInt(
                    state = bigImageSizeWidth,
                    getScreenWidth(context),
                    cellSize.value.width,
                    onEnd = { showBigImageStatus.value = false })
                animatorOfInt(
                    state = bigImageSizeHeight,
                    getScreenHeight(context),
                    cellSize.value.height,
                    onEnd = { showBigImageStatus.value = false })
            },
            onDragStart = { offset ->
                dragStatus.value = true
            },
            onDragEnd = {
                val currentCellOffset =
                    BigPosterManager.cellOffsetMap[BigPosterManager.currentClickCellIndex]
                        ?: Offset(0F, 0F)
                animatorOfFloat(state = bigImageOffsetX, bigImageOffsetX.value, currentCellOffset.x)
                animatorOfFloat(state = bigImageOffsetY, bigImageOffsetY.value, currentCellOffset.y)
                animatorOfInt(
                    state = bigImageSizeWidth,
                    bigImageSizeWidth.value,
                    cellSize.value.width,
                    onEnd = { showBigImageStatus.value = false })
                animatorOfInt(
                    state = bigImageSizeHeight,
                    bigImageSizeHeight.value,
                    cellSize.value.height,
                    onEnd = { showBigImageStatus.value = false })
                animatorOfFloat(state = dragAlpha, BigPosterManager.dragEndAlpha, 0F, onEnd = {
                    dragStatus.value = false
                    alphaIncrease.value = false
                })
            },
            onDrag = { change, dragAmount ->
                val offsetX = bigImageOffsetX.value
                val offsetY = bigImageOffsetY.value
                // 上滑暂时不处理
                if (offsetY < 0) {
                    return@BigImage
                }
                bigImageOffsetX.value = offsetX + dragAmount.x
                bigImageOffsetY.value = offsetY + dragAmount.y
                var scale = 1 - (offsetY + dragAmount.y) / (getScreenHeight(context) / 2)
                if (scale > 1.0f){
                    scale = 1.0f
                }
                if (scale > 0.5F) {
                    dragAlpha.value = scale
                    BigPosterManager.dragEndAlpha = scale
                    bigImageSizeWidth.value = (getScreenWidth(context) * scale).toInt()
                    bigImageSizeHeight.value = (getScreenHeight(context) * scale).toInt()
                }
            }
        )
    }
}

@Composable
fun BigImage(
    sizeWidth: Int,
    sizeHeight: Int,
    offsetX: Float,
    offsetY: Float,
    alpha: Float,
    click: () -> Unit,
    onDragStart: (Offset) -> Unit,
    onDragEnd: () -> Unit,
    onDrag: (change: PointerInputChange, dragAmount: Offset) -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color(0f, 0f, 0f, alpha))
    ) {
        Image(
            painter = painterResource(picList[BigPosterManager.currentClickCellIndex]),
            contentDescription = null,
            modifier = Modifier
                .size(sizeWidth.toDp(context).dp, sizeHeight.toDp(context).dp)
                .offset(offsetX.toDp(context).dp, offsetY.toDp(context).dp)
                .clickable { click() }
                .pointerInput(null) {
                    detectDragGestures(
                        onDragStart = onDragStart,
                        onDragEnd = onDragEnd,
                        onDrag = onDrag
                    )
                }
        )
    }
}

fun animatorOfFloat(state: MutableState<Float>, vararg offset: Float, onEnd: () -> Unit = {}) {
    val valueAnimator = ValueAnimator.ofFloat(*offset)
    valueAnimator.duration = BigPosterManager.animatorDuration
    valueAnimator.addUpdateListener {
        state.value = it.animatedValue as Float
    }
    valueAnimator.interpolator = LinearInterpolator()
    valueAnimator.addListener(onEnd = { onEnd() })
    valueAnimator.start()
}

fun animatorOfInt(state: MutableState<Int>, vararg offset: Int, onEnd: () -> Unit = {}) {
    val valueAnimator = ValueAnimator.ofInt(*offset)
    valueAnimator.duration = BigPosterManager.animatorDuration
    valueAnimator.addUpdateListener {
        state.value = it.animatedValue as Int
    }
    valueAnimator.interpolator = LinearInterpolator()
    valueAnimator.addListener(onEnd = { onEnd() })
    valueAnimator.start()
}

fun getScreenWidth(context: Context): Int {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
    return wm?.defaultDisplay?.width ?: 0
}

fun getScreenHeight(context: Context): Int {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
    return wm?.defaultDisplay?.height ?: 0
}

fun Int.toDp(context: Context): Int {
    val density = context.resources.displayMetrics.density
    return (this / density + 0.5).toInt()
}

fun Float.toDp(context: Context): Int {
    val density = context.resources.displayMetrics.density
    return (this / density + 0.5).toInt()
}