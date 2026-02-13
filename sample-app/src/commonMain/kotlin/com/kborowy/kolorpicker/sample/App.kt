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
package com.kborowy.kolorpicker.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kborowy.colorpicker.KolorPicker
import com.kborowy.colorpicker.components.HueSliderThumbConfig
import com.kborowy.colorpicker.components.PickerThumbConfig
import com.kborowy.colorpicker.ext.toHex
import kotlin.random.Random
import kotlin.random.nextInt
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    var selectedColor by remember {
        mutableStateOf(Color(red = 120, green = 194, blue = 87, alpha = 255))
    }
    var visible by remember { mutableStateOf(true) }

    MaterialTheme {
        Column(
            Modifier.fillMaxSize()
                .background(Color(32, 34, 48))
                .windowInsetsPadding(WindowInsets.safeContent)
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Button(onClick = { visible = !visible }) { Text("Toggle visibility") }

                    Button(enabled = !visible, onClick = { selectedColor = Color.random() }) { Text("Randomize color") }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("#${selectedColor.toHex()}", color = Color.White)
                    Text("alpha: ${selectedColor.alphaFormatted}", color = Color.White)
                    Box(modifier = Modifier.size(100.dp).background(selectedColor))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (visible) {
                KolorPicker(
                    initialColor = selectedColor,
                    onColorSelected = { selectedColor = it },
                    modifier =
                        Modifier.width(400.dp)
                            .height(300.dp),
                    hueSliderConfig = HueSliderThumbConfig.Default.copy(color = Color.Cyan),
                    pickerThumbConfig = PickerThumbConfig(color = Color.DarkGray),
                )
            }

            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Suppress("unused")
private fun Color.Companion.random(): Color =
    Color(
        red = Random.nextInt(0..255),
        green = Random.nextInt(0..255),
        blue = Random.nextInt(0..255),
        alpha = Random.nextInt(0..255),
    )

private val Color.alphaFormatted: String
    get() {
        val number = alpha.toString().split(".")
        if (number[1].isEmpty()) {
            return "${number.first()}.00}"
        }
        return "${number.first()}.${number[1].take(2)}"
    }
