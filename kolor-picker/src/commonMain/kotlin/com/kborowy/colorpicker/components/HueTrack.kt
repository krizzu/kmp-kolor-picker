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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.kborowy.colorpicker.ext.colorList
import com.kborowy.colorpicker.ext.hueDegreeToColor
import com.kborowy.colorpicker.ext.toHueDegree

@Composable
internal fun HueTrack(
    color: Color,
    onColorSelected: (Color) -> Unit,
    config: TrackConfig,
    modifier: Modifier = Modifier,
) {
    var trackSize by remember { mutableStateOf(Size.Zero) }
    var thumbPositionY by remember { mutableStateOf(0f) }
    val thumbHeightPx = with(LocalDensity.current) { config.thumbSize.height.toPx() }
    val updateColor by rememberUpdatedState(onColorSelected)

    fun onThumbPositionChange(position: Offset) {
        val thumbCenter = thumbHeightPx / 2
        val y = position.y.coerceIn(thumbCenter, trackSize.height - thumbCenter)
        val coercedY = y - thumbCenter

        // calculate hue value based on new position value
        val hueDegreeAtPosition =
            (coercedY / (trackSize.height - thumbHeightPx) * 360f).coerceIn(0f, 360f)
        val newColor = hueDegreeToColor(hueDegreeAtPosition)

        thumbPositionY = coercedY
        if (newColor != color) {
            updateColor(newColor)
        }
    }

    LaunchedEffect(trackSize, color) {
        if (!trackSize.isEmpty()) {
            val deg = color.toHueDegree()
            thumbPositionY = (deg / 360f) * (trackSize.height - thumbHeightPx)
        }
    }

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .onSizeChanged { trackSize = it.toSize() }
                .pointerInput(Unit) { detectTapGestures(onTap = ::onThumbPositionChange) }
                .pointerInput(Unit) {
                    detectDragGestures { change, _ -> onThumbPositionChange(change.position) }
                }
    ) {
        // color track
        var trackModifier = Modifier.fillMaxSize()
        if (config.trackWidth != 0.dp) {
            trackModifier = trackModifier.wrapContentSize().fillMaxHeight().size(config.trackWidth)
        }
        Canvas(
            trackModifier
                .align(Alignment.Center)
                .padding(horizontal = config.trackPadding)
                .clip(RoundedCornerShape(size = config.trackBorderRadius))
        ) {
            drawRect(Brush.verticalGradient(Color.colorList))
        }

        if (!trackSize.isEmpty()) {
            ThumbCanvas(
                modifier = Modifier.fillMaxSize(),
                config = config,
                trackWidth = trackSize.width,
                height = thumbHeightPx,
                positionY = thumbPositionY,
            )
        }
    }
}
