package com.example.m07_0488_endterm_pr01_evafioen.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = BrandBlueDark,
    onPrimary = DarkGreyText,
    secondary = LightGreySurface,
    onSecondary = DarkGreyText,
    background = DarkBackground,
    onBackground = LightGreyText,
    surface = DarkSurface,
    onSurface = LightGreyText
)

private val LightColorScheme = lightColorScheme(
    primary = BrandBlue,
    onPrimary = PureWhite,
    secondary = LightGreySurface,
    onSecondary = DarkGreyText,
    background = WhiteBackground,
    onBackground = DarkGreyText,
    surface = LightGreySurface,
    onSurface = DarkGreyText
)

@Composable
fun M070488ENDTERMPR01evafioenTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.setDecorFitsSystemWindows(window, false)

            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
