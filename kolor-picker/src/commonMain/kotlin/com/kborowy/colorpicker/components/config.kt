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
                trackPadding = 6.dp,
                trackWidth = 0.dp,
                thumbSize = DpSize(width = 0.dp, height = 12.dp),
                thumbColor = Color.White,
                thumbBorderRadius = 4.dp,
                thumbBorderSize = 4.dp
            )

        val CircleFilled =
            TrackConfig(
                trackBorderRadius = 20.dp,
                trackPadding = 0.dp,
                trackWidth = 20.dp,
                thumbSize = DpSize(width = 20.dp, height = 20.dp),
                thumbColor = Color.White,
                thumbBorderRadius = 20.dp,
                thumbBorderSize = 0.dp
            )
    }
}
