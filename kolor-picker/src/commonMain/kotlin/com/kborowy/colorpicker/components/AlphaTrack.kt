package com.kborowy.colorpicker.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.kborowy.colorpicker.ext.toHex

@Composable
internal fun AlphaTrack(
    color: Color,
    onColorSelected: (Color) -> Unit,
    modifier: Modifier = Modifier,
    config: TrackConfig,
) {
    var sliderSize by remember { mutableStateOf(Size.Zero) }
    val innerPadding = with(LocalDensity.current) { config.trackPadding.roundToPx() }
    val checkerSize = sliderSize.width / 2 - innerPadding
    val rows = (sliderSize.height / checkerSize).toInt() + 1
    val thumbHeightPx = with(LocalDensity.current) { config.thumbSize.height.toPx() }
    var thumbPositionY by remember { mutableStateOf(0f) }

    fun positionToAlphaValue(positionY: Float): Float {
        if (sliderSize.isEmpty()) {
            return 0f
        }

        return (1f - positionY / (sliderSize.height - thumbHeightPx)).coerceIn(0f, 1f)
    }

    fun alphaToPosition(alpha: Float): Float {
        if (sliderSize.isEmpty()) {
            return 0f
        }

        return (1f - alpha) * (sliderSize.height - thumbHeightPx)
    }

    fun onThumbPositionChange(position: Offset) {
        val thumbCenter = thumbHeightPx / 2
        val y = position.y.coerceIn(thumbCenter, sliderSize.height - thumbCenter)
        val coercedY = y - thumbCenter
        thumbPositionY = coercedY
        onColorSelected(color.copy(alpha = positionToAlphaValue(coercedY)))
    }

    LaunchedEffect(sliderSize, color.toHex()) {
        if (sliderSize.isEmpty()) {
            return@LaunchedEffect
        }
        val newPosition = alphaToPosition(color.alpha)
        if (thumbPositionY != newPosition) {
            thumbPositionY = newPosition
        }
    }

    Box(
        modifier =
            modifier
                .fillMaxHeight()
                .onSizeChanged { sliderSize = it.toSize() }
                .pointerInput(Unit) { detectTapGestures(onTap = ::onThumbPositionChange) }
                .pointerInput(Unit) {
                    detectDragGestures { change, _ -> onThumbPositionChange(change.position) }
                }
    ) {

        // draw checkerboard
        var trackModifier = Modifier.fillMaxSize()
        if (config.trackWidth != 0.dp) {
            trackModifier = trackModifier.wrapContentSize().fillMaxHeight().size(config.trackWidth)
        }
        Canvas(
            trackModifier
                .align(Alignment.Center)
                .padding(horizontal = config.trackPadding)
                .clip(RoundedCornerShape(config.trackBorderRadius))
        ) {
            for (row in 0 until rows) {
                val dark = row % 2 == 0
                val topOffset = row * checkerSize
                drawRect(
                    color = if (dark) Color.LightGray else Color.White,
                    size = Size(checkerSize, checkerSize),
                    topLeft = Offset(x = 0f, y = topOffset),
                )
                drawRect(
                    color = if (dark) Color.White else Color.LightGray,
                    size = Size(checkerSize, checkerSize),
                    topLeft = Offset(x = checkerSize, y = topOffset),
                )
            }
        }

        // draw slider with color with applied alpha channel
        Canvas(
            trackModifier
                .align(Alignment.Center)
                .padding(horizontal = config.trackPadding)
                .clip(RoundedCornerShape(config.trackBorderRadius))
        ) {
            drawRect(
                brush =
                    Brush.verticalGradient(
                        colors = listOf(color.copy(alpha = 1f), color.copy(alpha = 0f))
                    )
            )
        }

        // draw thumb only when we know the size
        if (!sliderSize.isEmpty()) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val strokeWidth = config.thumbBorderSize.toPx()
                val thumbWidth =
                    if (config.thumbSize.width == 0.dp) sliderSize.width + strokeWidth
                    else config.thumbSize.width.toPx()
                val thumbX = (size.width - thumbWidth) / 2

                drawRoundRect(
                    color = config.thumbColor,
                    topLeft = Offset(x = thumbX, y = thumbPositionY),
                    size = Size(width = thumbWidth, height = thumbHeightPx),
                    style =
                        if (strokeWidth <= 0f) Fill
                        else Stroke(width = strokeWidth, cap = StrokeCap.Round),
                    cornerRadius = CornerRadius(x = config.thumbBorderRadius.toPx()),
                )
            }
        }
    }
}
