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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.kborowy.colorpicker.config.TrackConfig

@Composable
internal fun ThumbCanvas(
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
            cornerRadius = CornerRadius(x = config.thumbCornerRadius.toPx()),
        )
    }
}
