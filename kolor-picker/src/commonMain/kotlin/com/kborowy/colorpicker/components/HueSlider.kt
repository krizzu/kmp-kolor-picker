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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.kborowy.colorpicker.ext.colorList
import com.kborowy.colorpicker.ext.hueDegreeToColor
import com.kborowy.colorpicker.ext.toHueDegree

@Composable
internal fun HueSlider(
    onColorSelected: (Color) -> Unit,
    modifier: Modifier = Modifier,
    initialColor: Color? = null,
    thumbConfig: HueSliderThumbConfig = HueSliderThumbConfig.Default,
) {
    val initialSelectedHue = remember { initialColor }
    var sliderSize by remember { mutableStateOf(Size.Zero) }
    var thumbPositionY by remember { mutableStateOf(0f) }
    val thumbHeightPx = with(LocalDensity.current) { thumbConfig.height.toPx() }
    val updateColor by rememberUpdatedState(onColorSelected)

    fun onThumbPositionChange(newOffset: Offset) {
        val start = thumbHeightPx / 2
        val end = sliderSize.height - thumbHeightPx / 2
        val y = newOffset.y.coerceIn(start..end)
        val newPosition = y - start

        // calculate hue value based on new position value
        val hueDegreeAtPosition =
            (newPosition / (sliderSize.height - thumbHeightPx) * 360f).coerceIn(0f, 360f)
        val newColor = hueDegreeToColor(hueDegreeAtPosition)

        thumbPositionY = newPosition
        updateColor(newColor)
    }

    LaunchedEffect(sliderSize, initialSelectedHue) {
        if (!sliderSize.isEmpty() && initialSelectedHue != null) {
            val deg = initialSelectedHue.toHueDegree()
            thumbPositionY = (deg / 360f) * (sliderSize.height - thumbHeightPx)
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
        // color track
        Canvas(
            modifier =
                Modifier.fillMaxSize()
                    .padding(horizontal = thumbConfig.borderSize)
                    .clip(RoundedCornerShape(4.dp))
        ) {
            drawRect(Brush.verticalGradient(Color.colorList))
        }

        // draw thumb only when we know the size
        if (!sliderSize.isEmpty()) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRoundRect(
                    color = thumbConfig.color,
                    topLeft = Offset(x = (thumbConfig.borderSize.toPx()) / 2, y = thumbPositionY),
                    size =
                        Size(
                            width = sliderSize.width - (thumbConfig.borderSize.toPx()),
                            height = thumbHeightPx,
                        ),
                    style = Stroke(width = thumbConfig.borderSize.toPx()),
                    cornerRadius = CornerRadius(thumbConfig.borderRadius, thumbConfig.borderRadius),
                )
            }
        }
    }
}
