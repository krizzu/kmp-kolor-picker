package com.kborowy.colorpicker.components

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class SliderConfig(
    val sliderBorderRadius: Dp,
    val sliderPadding: Dp,
    val thumbHeight: Dp,
    val thumbHorizontalPadding: Dp,
    val thumbColor: Color,
    val thumbBorderRadius: Dp,
    val thumbBorderSize: Dp,
) {
    companion object {
        val Default =
            SliderConfig(
                sliderBorderRadius = 4.dp,
                sliderPadding = 0.dp,
                thumbHeight = 24.dp,
                thumbColor = Color.White,
                thumbBorderRadius = 4.dp,
                thumbBorderSize = 0.dp,
                thumbHorizontalPadding = 0.dp,
            )

        val Circle =
            SliderConfig(
                sliderBorderRadius = 20.dp,
                sliderPadding = 4.dp,
                thumbHeight = 20.dp,
                thumbColor = Color.White,
                thumbBorderRadius = 20.dp,
                thumbBorderSize = 0.dp,
                thumbHorizontalPadding = 4.dp,
            )
    }
}
