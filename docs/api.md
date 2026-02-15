# API Reference

For complete API reference, see the [API Reference page](api-ref/index.html).

## Basic Usage

```kotlin
var selectedColor by remember { mutableStateOf(Color.Red) }

KolorPicker(
    initialColor = selectedColor,
    onColorSelected = { selectedColor = it },
    modifier = Modifier.width(300.dp).height(200.dp),
)
```

### With Custom Configuration

```kotlin
KolorPicker(
    initialColor = selectedColor,
    onColorSelected = { selectedColor = it },
    pickerConfig = PickerConfig.Circle,
    trackConfig = TrackConfig.CircleFilled,
    modifier = Modifier.width(300.dp).height(200.dp),
)
```

### With Separate Track Configurations

```kotlin
KolorPicker(
    initialColor = selectedColor,
    onColorSelected = { selectedColor = it },
    pickerConfig = PickerConfig.Default,
    alphaTrackConfig = TrackConfig.CircleFilled,
    hueTrackConfig = TrackConfig.Default,
    modifier = Modifier.width(300.dp).height(200.dp),
)
```

---

## `KolorPicker` Composable

| Parameter         | Type              | Default                | Description                                        |
|-------------------|-------------------|------------------------|----------------------------------------------------|
| `initialColor`    | `Color`           | **Required**           | The initial color to display                       |
| `onColorSelected` | `(Color) -> Unit` | **Required**           | Callback invoked when color changes                |
| `modifier`        | `Modifier`        | `Modifier`             | Modifier for the picker layout                     |
| `pickerConfig`    | `PickerConfig`    | `PickerConfig.Default` | Configuration for the brightness/saturation picker |
| `trackConfig`     | `TrackConfig`     | `TrackConfig.Default`  | Configuration for both hue and alpha tracks        |

---

## `TrackConfig`

Configuration for hue and alpha track sliders.

| Property            | Type     | Default        | Description                                                                                                  |
|---------------------|----------|----------------|--------------------------------------------------------------------------------------------------------------|
| `trackBorderRadius` | `Dp`     | `4.dp`         | Corner radius of the color track                                                                             |
| `trackPadding`      | `Dp`     | `4.dp`         | Horizontal padding applied to the track. Use when you don't know the exact width but want spacing from edges |
| `trackWidth`        | `Dp`     | `0.dp`         | Width of the color track. **If `0.dp`, takes maximum available width**                                       |
| `thumbSize`         | `DpSize` | `0.dp × 12.dp` | Size of the thumb. **Width `0.dp` = max available width. Height must always be specified**                   |
| `thumbColor`        | `Color`  | `Color.White`  | Color of the thumb indicator                                                                                 |
| `thumbCornerRadius` | `Dp`     | `4.dp`         | Corner radius of the thumb                                                                                   |
| `thumbBorderSize`   | `Dp`     | `4.dp`         | Border thickness. **If `0.dp`, thumb is filled with `thumbColor`. Otherwise only border is drawn**           |

### Presets

**`TrackConfig.Default`** - Rectangular track with bordered thumb.

**`TrackConfig.CircleFilled`** - Rounded track with filled circular thumb.

---

## `PickerConfig`

Configuration for the brightness/saturation picker area.

| Property          | Type            | Default               | Description                                                        |
|-------------------|-----------------|-----------------------|--------------------------------------------------------------------|
| `pickerPadding`   | `PaddingValues` | `PaddingValues(0.dp)` | Padding applied around the picker area                             |
| `pickerRadius`    | `Dp`            | `4.dp`                | Corner radius of the picker area                                   |
| `thumbSize`       | `Dp`            | `16.dp`               | Size (width and height) of the thumb indicator                     |
| `thumbRadius`     | `Dp`            | `4.dp`                | Corner radius of the thumb                                         |
| `thumbColor`      | `Color`         | `Color.White`         | Color of the thumb indicator                                       |
| `thumbBorderSize` | `Dp`            | `4.dp`                | Border thickness. **If `0.dp`, thumb is filled with `thumbColor`** |

### Presets

**`PickerConfig.Default`** - Rectangular thumb with border, no padding.

**`PickerConfig.Circle`** - Circular thumb with border and padding.

**`PickerConfig.CircleFilled`** - Circular filled thumb with minimal padding.

