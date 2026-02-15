package com.kborowy.colorpicker.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

/** Configuration for track-based sliders. */
@Immutable
data class TrackConfig(
    /** Corner radius of the color track. */
    val trackBorderRadius: Dp,
    /**
     * Horizontal padding applied to the track. Use this when you don't know the exact width of the
     * slider you want, but want to add some spacing from the edges.
     */
    val trackPadding: Dp,
    /**
     * Width of the color track. If set to 0.dp, the track will take the maximum available width.
     */
    val trackWidth: Dp,
    /**
     * Size of the thumb indicator.
     * - width: If set to 0.dp, the thumb will take the maximum available width. Otherwise, the
     *   specified width is used.
     * - height: Must always be specified explicitly.
     */
    val thumbSize: DpSize,
    /** Color of the thumb indicator. */
    val thumbColor: Color,
    /** Corner radius of the thumb indicator. */
    val thumbCornerRadius: Dp,
    /**
     * Border size of the thumb. If set to 0.dp, the thumb will be filled with [thumbColor].
     * Otherwise, only the border is drawn with the specified thickness.
     */
    val thumbBorderSize: Dp,
) {
    companion object {
        val Default =
            TrackConfig(
                trackBorderRadius = 4.dp,
                trackPadding = 4.dp,
                trackWidth = 0.dp,
                thumbSize = DpSize(width = 0.dp, height = 12.dp),
                thumbColor = Color.White,
                thumbCornerRadius = 4.dp,
                thumbBorderSize = 4.dp,
            )

        val CircleFilled =
            TrackConfig(
                trackBorderRadius = 20.dp,
                trackPadding = 0.dp,
                trackWidth = 20.dp,
                thumbSize = DpSize(width = 20.dp, height = 20.dp),
                thumbColor = Color.White,
                thumbCornerRadius = 20.dp,
                thumbBorderSize = 0.dp,
            )
    }
}

/** Configuration for the brightness/saturation picker . */
@Immutable
data class PickerConfig(
    /** Padding applied around the picker area. */
    val pickerPadding: PaddingValues,
    /** Corner radius of the picker area. */
    val pickerRadius: Dp,
    /** Size (width and height) of the thumb indicator. */
    val thumbSize: Dp,
    /** Corner radius of the thumb indicator. */
    val thumbRadius: Dp,
    /** Color of the thumb indicator. */
    val thumbColor: Color,
    /** Border size of the thumb. If set to 0.dp, the thumb will be filled with [thumbColor]. */
    val thumbBorderSize: Dp,
) {
    companion object {
        val Default =
            PickerConfig(
                thumbSize = 16.dp,
                thumbRadius = 4.dp,
                thumbColor = Color.White,
                thumbBorderSize = 4.dp,
                pickerRadius = 4.dp,
                pickerPadding = PaddingValues(0.dp),
            )

        val Circle =
            PickerConfig(
                thumbSize = 16.dp,
                thumbRadius = 16.dp,
                thumbColor = Color.White,
                thumbBorderSize = 4.dp,
                pickerRadius = 4.dp,
                pickerPadding = PaddingValues(8.dp),
            )

        val CircleFilled =
            PickerConfig(
                thumbSize = 16.dp,
                thumbRadius = 16.dp,
                thumbColor = Color.White,
                thumbBorderSize = 0.dp,
                pickerRadius = 2.dp,
                pickerPadding = PaddingValues(4.dp),
            )
    }
}
