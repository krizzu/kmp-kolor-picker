package com.kborowy.colorpicker.components

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@Immutable
data class TrackConfig(
    val trackBorderRadius: Dp,
    val trackPadding: Dp,
    val trackWidth: Dp,
    val thumbSize: DpSize,
    val thumbColor: Color,
    val thumbBorderRadius: Dp,
    val thumbBorderSize: Dp,
) {
    companion object {
        val Default =
            TrackConfig(
                trackBorderRadius = 4.dp,
                trackPadding = 0.dp,
                trackWidth = 0.dp,
                thumbSize = DpSize(width = 24.dp, height = 24.dp),
                thumbColor = Color.White,
                thumbBorderRadius = 4.dp,
                thumbBorderSize = 0.dp
            )

        val FilledCircle =
            TrackConfig(
                trackBorderRadius = 14.dp,
                trackPadding = 0.dp,
                trackWidth = 14.dp,
                thumbSize = DpSize(width = 14.dp, height = 14.dp),
                thumbColor = Color.White,
                thumbBorderRadius = 14.dp,
                thumbBorderSize = 0.dp
            )
    }
}
