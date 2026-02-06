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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
data class PickerThumbConfig(val size: Dp = 8.dp, val color: Color = Color.White) {
    companion object {
        val Default = PickerThumbConfig()
    }
}

@Composable
internal fun HSVPicker(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit,
    modifier: Modifier = Modifier,
    thumbConfig: PickerThumbConfig = PickerThumbConfig.Default,
) {
    val initialSelectedHue = remember { selectedColor }
    var rectSize by remember { mutableStateOf(IntSize.Zero) }
    var selectorPosition by remember { mutableStateOf(Offset.Zero) }
    val thumbSizePx = with(LocalDensity.current) { thumbConfig.size.toPx() }

    fun updatePosition(newOffset: Offset) {
        selectorPosition =
            Offset(
                x = newOffset.x.coerceIn(0f, rectSize.width.toFloat() - thumbSizePx / 2),
                y = newOffset.y.coerceIn(0f, rectSize.height.toFloat() - thumbSizePx / 2),
            )
    }

    LaunchedEffect(rectSize, selectorPosition, selectedColor) {
        if (rectSize == IntSize.Zero || selectorPosition == Offset.Zero) {
            return@LaunchedEffect
        }
        val hue = selectedColor.toHueDegree()
        val saturation = selectorPosition.x / (rectSize.width - thumbSizePx / 2)
        val value = 1f - selectorPosition.y / (rectSize.height - thumbSizePx / 2)
        val color = Color.fromHsv(hue, saturation, value)
        onColorSelected(color)
    }

    LaunchedEffect(rectSize, initialSelectedHue) {
        if (rectSize == IntSize.Zero) {
            return@LaunchedEffect
        }
        val (_, saturation, value) = initialSelectedHue.toHsv()
        val x = saturation * (rectSize.width - thumbSizePx / 2)
        val y = (1f - value) * (rectSize.height - thumbSizePx / 2)
        selectorPosition = Offset(x, y)
    }

    Canvas(
        modifier =
            modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(4.dp))
                .onSizeChanged { rectSize = it }
                .pointerInput(Unit) { detectTapGestures { updatePosition(it) } }
                .pointerInput(Unit) {
                    detectDragGestures { change, _ -> updatePosition(change.position) }
                }
    ) {
        drawRect(Brush.horizontalGradient(listOf(Color.White, selectedColor)))
        drawRect(Brush.verticalGradient(listOf(Color.Transparent, Color.Black)))
        drawCircle(
            color = thumbConfig.color,
            style = Stroke(width = thumbSizePx / 2f),
            center = selectorPosition,
            radius = thumbSizePx,
        )
    }
}
