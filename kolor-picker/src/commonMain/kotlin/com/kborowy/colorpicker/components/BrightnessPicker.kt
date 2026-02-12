/*
 * Copyright 2025 Krzysztof Borowy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kborowy.colorpicker.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.kborowy.colorpicker.ext.fromHsv
import com.kborowy.colorpicker.ext.toHsv
import com.kborowy.colorpicker.ext.toHueDegree

@Immutable
data class PickerThumbConfig(
    val size: Dp = 16.dp,
    val strokeWidth: Float = 4f,
    val color: Color = Color.White,
) {
    companion object {
        val Default = PickerThumbConfig()
    }
}

@Composable
internal fun BrightnessPicker(
    color: Color,
    onColorSelected: (Color) -> Unit,
    modifier: Modifier = Modifier,
    thumbConfig: PickerThumbConfig = PickerThumbConfig.Default,
) {
    val initialSelectedHue = remember { color }
    var rectSize by remember { mutableStateOf(IntSize.Zero) }
    var selectorPosition by remember { mutableStateOf(Offset.Zero) }
    val thumbSizePx = with(LocalDensity.current) { thumbConfig.size.toPx() }
    val strokeSizePx = thumbConfig.strokeWidth
    val thumbRadius = thumbSizePx / 2f
    val thumbEdge = thumbRadius + strokeSizePx / 2f

    fun updatePosition(newOffset: Offset) {
        val x = newOffset.x.coerceIn(thumbEdge, rectSize.width.toFloat() - thumbEdge)
        val y = newOffset.y.coerceIn(thumbEdge, rectSize.height.toFloat() - thumbEdge)
        selectorPosition = Offset(x, y)
    }

    LaunchedEffect(rectSize, selectorPosition, color) {
        if (rectSize == IntSize.Zero || selectorPosition == Offset.Zero) {
            return@LaunchedEffect
        }
        val newColor = positionToColor(color, rectSize, selectorPosition, thumbEdge)
        onColorSelected(newColor)
    }

    LaunchedEffect(rectSize, initialSelectedHue) {
        if (rectSize == IntSize.Zero) {
            return@LaunchedEffect
        }

        selectorPosition = colorToPosition(color, rectSize, thumbEdge)
    }

    Canvas(
        modifier =
            modifier
                .fillMaxSize()
                .onSizeChanged { rectSize = it }
                .pointerInput(Unit) { detectTapGestures { updatePosition(it) } }
                .pointerInput(Unit) {
                    detectDragGestures { change, _ -> updatePosition(change.position) }
                }
    ) {
        drawRect(Brush.horizontalGradient(listOf(Color.White, color)))
        drawRect(Brush.verticalGradient(listOf(Color.Transparent, Color.Black)))
        drawCircle(
            color = thumbConfig.color,
            style = Stroke(width = strokeSizePx, cap = StrokeCap.Round),
            center = selectorPosition,
            radius = thumbRadius,
        )
    }
}

fun colorToPosition(c: Color, size: IntSize, edge: Float): Offset {
    val (_, saturation, value) = c.toHsv()
    val travelWidth = (size.width - 2 * edge).coerceAtLeast(1f)
    val travelHeight = (size.height - 2 * edge).coerceAtLeast(1f)
    val x = saturation * travelWidth + edge
    val y = (1f - value) * travelHeight + edge
    return Offset(x, y)
}

private fun positionToColor(color: Color, size: IntSize, position: Offset, edge: Float): Color {
    val hue = color.toHueDegree()
    val travelWidth = (size.width - 2 * edge).coerceAtLeast(1f)
    val travelHeight = (size.height - 2 * edge).coerceAtLeast(1f)
    val saturation = (position.x - edge) / travelWidth
    val value = 1f - (position.y - edge) / travelHeight
    return Color.fromHsv(hue, saturation.coerceIn(0f, 1f), value.coerceIn(0f, 1f))
}
