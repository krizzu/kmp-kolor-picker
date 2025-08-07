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
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kborowy.colorpicker.components.HSVPicker
import com.kborowy.colorpicker.components.HueSlider
import com.kborowy.colorpicker.components.HueSliderThumbConfig
import com.kborowy.colorpicker.components.PickerThumbConfig

@Composable
fun KolorPicker(
    onColorSelected: (Color) -> Unit,
    modifier: Modifier = Modifier,
    initialColor: Color = Color.Red,
    hueSliderConfig: HueSliderThumbConfig = HueSliderThumbConfig.Default,
    pickerThumbConfig: PickerThumbConfig = PickerThumbConfig.Default
) {
    var selectedHue by remember { mutableStateOf(initialColor) }

    Row(modifier = modifier) {
        HSVPicker(
            selectedColor = selectedHue,
            onColorSelected = onColorSelected,
            thumbConfig = pickerThumbConfig,
            modifier = Modifier.weight(9f),
        )

        Spacer(modifier = Modifier.width(8.dp))

        HueSlider(
            initialColor = initialColor,
            onColorSelected = { selectedHue = it },
            thumbConfig = hueSliderConfig,
            modifier = Modifier.weight(1f)
        )
    }
}
