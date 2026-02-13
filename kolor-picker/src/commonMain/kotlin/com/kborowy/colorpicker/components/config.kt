package com.kborowy.colorpicker.components

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@Immutable
data class SliderConfig(
    val sliderBorderRadius: Dp,
    val sliderPadding: Dp,
    val thumbSize: DpSize,
    val thumbColor: Color,
    val thumbBorderRadius: Dp,
    val thumbBorderSize: Dp,
) {
    companion object {
        val Default =
            SliderConfig(
                sliderBorderRadius = 4.dp,
                sliderPadding = 0.dp,
                thumbSize = DpSize(width = 24.dp, height = 24.dp),
                thumbColor = Color.White,
                thumbBorderRadius = 4.dp,
                thumbBorderSize = 0.dp
            )

        val Circle =
            SliderConfig(
                sliderBorderRadius = 20.dp,
                sliderPadding = 4.dp,
                thumbSize = DpSize(width = 20.dp, height = 20.dp),
                thumbColor = Color.White,
                thumbBorderRadius = 20.dp,
                thumbBorderSize = 0.dp
            )
    }
}
