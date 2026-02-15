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
package com.kborowy.colorpicker

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kborowy.colorpicker.components.AlphaTrack
import com.kborowy.colorpicker.components.BrightnessPicker
import com.kborowy.colorpicker.components.HueTrack
import com.kborowy.colorpicker.config.PickerConfig
import com.kborowy.colorpicker.config.TrackConfig

@Composable
fun KolorPicker(
    initialColor: Color,
    onColorSelected: (Color) -> Unit,
    modifier: Modifier = Modifier,
    pickerConfig: PickerConfig = PickerConfig.Default,
    trackConfig: TrackConfig = TrackConfig.Default,
) {

    KolorPicker(
        initialColor = initialColor,
        onColorSelected = onColorSelected,
        pickerConfig = pickerConfig,
        alphaTrackConfig = trackConfig,
        hueTrackConfig = trackConfig,
        modifier = modifier,
    )
}

@Composable
fun KolorPicker(
    initialColor: Color,
    onColorSelected: (Color) -> Unit,
    pickerConfig: PickerConfig,
    alphaTrackConfig: TrackConfig,
    hueTrackConfig: TrackConfig,
    modifier: Modifier = Modifier,
) {
    var selectedHue by remember { mutableStateOf(initialColor) }

    Row(modifier = modifier) {
        BrightnessPicker(
            color = selectedHue,
            onColorSelected = { onColorSelected(it.copy(alpha = initialColor.alpha)) },
            config = pickerConfig,
            modifier = Modifier.weight(8f),
        )

        Spacer(modifier = Modifier.weight(0.25f))

        HueTrack(
            color = selectedHue,
            onColorSelected = { selectedHue = it },
            config = hueTrackConfig,
            modifier = Modifier.weight(1f),
        )

        Spacer(modifier = Modifier.weight(0.25f))

        AlphaTrack(
            color = selectedHue.copy(alpha = initialColor.alpha),
            onColorSelected = onColorSelected,
            modifier = Modifier.weight(1f),
            config = alphaTrackConfig,
        )
    }
}
