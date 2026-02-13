package com.kborowy.colorpicker.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun ThumbCanvas(
    config: TrackConfig,
    trackWidth: Float,
    positionY: Float,
    height: Float,
    modifier: Modifier,
) {
    Canvas(modifier = modifier) {
        val strokeWidth = config.thumbBorderSize.toPx()
        val thumbWidth =
            if (config.thumbSize.width == 0.dp) trackWidth + strokeWidth
            else config.thumbSize.width.toPx()
        val thumbX = (size.width - thumbWidth) / 2

        drawRoundRect(
            color = config.thumbColor,
            topLeft = Offset(x = thumbX, y = positionY),
            size = Size(width = thumbWidth, height = height),
            style =
                if (strokeWidth <= 0f) Fill else Stroke(width = strokeWidth, cap = StrokeCap.Round),
            cornerRadius = CornerRadius(x = config.thumbBorderRadius.toPx()),
        )
    }
}
