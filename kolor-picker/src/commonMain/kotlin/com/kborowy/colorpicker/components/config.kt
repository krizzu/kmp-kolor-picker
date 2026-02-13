package com.kborowy.colorpicker.components

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class SliderConfig(
    val sliderBorderRadius: Dp,
    val sliderPaddingRadius: Dp,
    val thumbHeight: Dp,
    val thumbColor: Color,
    val thumbBorderRadius: Dp,
    val thumbBorderSize: Dp,
) {
    companion object {
        val Default =
            SliderConfig(
                sliderBorderRadius = 4.dp,
                sliderPaddingRadius = 4.dp,
                thumbHeight = 24.dp,
                thumbColor = Color.White,
                thumbBorderRadius = 6.dp,
                thumbBorderSize = 4.dp
            )
    }
}
