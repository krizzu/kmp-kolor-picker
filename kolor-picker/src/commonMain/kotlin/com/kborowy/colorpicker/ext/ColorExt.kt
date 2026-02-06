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
package com.kborowy.colorpicker.ext

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

internal val Color.Companion.colorList by lazy {
    IntArray(360) { it }.map { deg -> hueDegreeToColor(deg.toFloat()) }
}

internal fun Color.toHueDegree(): Float {
    val M = max(max(red, green), blue)
    val m = min(min(red, green), blue)
    val chroma = M - m

    if (chroma == 0f) {
        return 0f
    }
    val hue: Float =
        when (M) {
            red -> 60f * (green - blue) / chroma
            green -> 60f * (blue - red) / chroma + 120f
            blue -> 60f * (red - green) / chroma + 240f
            else -> 0f
        }

    return ((hue + 360f) % 360f).coerceIn(0f, 360f)
}

internal fun Color.Companion.fromHsv(hue: Float, saturation: Float, value: Float): Color {
    require(hue in 0f..360f) { "hue out of bounds, hue=$hue" }
    require(saturation in 0f..1f) { "saturation out of bounds, hue=$hue" }
    require(value in 0f..1f) { "value out of bounds, hue=$hue" }
    val chroma = value * saturation
    val x = chroma * (1 - abs((hue / 60f % 2) - 1))
    val m = value - chroma

    val (r, g, b) =
        when (hue.toInt() / 60) {
            0 -> Triple(chroma, x, 0f) // Red → Yellow
            1 -> Triple(x, chroma, 0f) // Yellow → Green
            2 -> Triple(0f, chroma, x) // Green → Cyan
            3 -> Triple(0f, x, chroma) // Cyan → Blue
            4 -> Triple(x, 0f, chroma) // Blue → Magenta
            else -> Triple(chroma, 0f, x) // Magenta → Red
        }

    return Color(r + m, g + m, b + m)
}

internal fun Color.toHsv(): Triple<Float, Float, Float> {
    val max = maxOf(red, green, blue)
    val min = minOf(red, green, blue)
    val chroma = max - min
    val hue =
        when {
            chroma == 0f -> 0f
            max == red -> 60f * ((green - blue) / chroma + 0f)
            max == green -> 60f * ((blue - red) / chroma + 120f)
            else -> 60f * ((red - green) / chroma + 240f)
        }.let { (it + 360) % 360 }
    val saturation = if (max == 0f) 0f else chroma / max
    val value = max

    return Triple(hue, saturation, value)
}

internal fun hueDegreeToColor(hueDegrees: Float): Color {
    require(hueDegrees in 0f..360f) { "hue degrees outside bounds (value=$hueDegrees)" }
    val h = (hueDegrees % 360) / 60f
    val x = (1 - abs(h % 2 - 1)) // Fading between peaks
    val (r, g, b) =
        when (h.toInt()) {
            0 -> Triple(1f, x, 0f) // Red → Yellow
            1 -> Triple(x, 1f, 0f) // Yellow → Green
            2 -> Triple(0f, 1f, x) // Green → Cyan
            3 -> Triple(0f, x, 1f) // Cyan → Blue
            4 -> Triple(x, 0f, 1f) // Blue → Magenta
            else -> Triple(1f, 0f, x) // Magenta → Red
        }
    return Color(r, g, b)
}

fun Color.toHex(): String =
    buildString {
            append((toArgb() shr 16 and 0xff).toHex())
            append((toArgb() shr 8 and 0xff).toHex())
            append((toArgb() and 0xff).toHex())
        }
        .uppercase()




private fun Int.toHex(): String {
    return this.toString(16).let {
        if (it.length == 1) {
            "0$it"
        } else {
            it
        }
    }
}
