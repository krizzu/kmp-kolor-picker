package com.kborowy.colorpicker.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.kborowy.colorpicker.ext.toHex

@Immutable
data class AlphaSliderThumbConfig(
    val size: DpSize = DpSize(4.dp, height = 12.dp),
    val color: Color = Color.White,
    val borderSize: Dp = 4.dp,
    val borderRadius: Dp = 6.dp,
) {
    companion object {
        val Default = AlphaSliderThumbConfig()
    }
}

@Composable
internal fun AlphaSlider(
    color: Color,
    onColorSelected: (Color) -> Unit,
    modifier: Modifier = Modifier,
    config: AlphaSliderThumbConfig = AlphaSliderThumbConfig.Default,
) {
    var sliderSize by remember { mutableStateOf(Size.Zero) }
    val innerPadding = with(LocalDensity.current) { config.borderRadius.roundToPx() }
    val checkerSize = sliderSize.width / 2 - innerPadding
    val rows = (sliderSize.height / checkerSize).toInt() + 1
    val thumbHeightPx = with(LocalDensity.current) { config.size.height.toPx() }
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
        val relativeY = y - thumbCenter
        thumbPositionY = relativeY
        onColorSelected(color.copy(alpha = positionToAlphaValue(relativeY)))
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
        Canvas(
            modifier =
                Modifier.fillMaxSize()
                    .padding(horizontal = config.borderRadius)
                    .clip(RoundedCornerShape(config.borderRadius))
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
            modifier =
                Modifier.fillMaxSize()
                    .padding(horizontal = config.borderRadius)
                    .clip(RoundedCornerShape(config.borderRadius))
        ) {
            drawRect(
                brush =
                    Brush.verticalGradient(
                        colors = listOf(color.copy(alpha = 1f), color.copy(alpha = 0f))
                    )
            )
        }

        if (!sliderSize.isEmpty()) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRoundRect(
                    color = config.color,
                    topLeft = Offset(x = (config.borderSize.toPx()) / 2, y = thumbPositionY),
                    size =
                        Size(
                            width = sliderSize.width - (config.borderSize.toPx()),
                            height = thumbHeightPx,
                        ),
                    style = Stroke(width = config.borderSize.toPx()),
                    cornerRadius =
                        CornerRadius(config.borderRadius.toPx(), config.borderRadius.toPx()),
                )
            }
        }
    }
}
