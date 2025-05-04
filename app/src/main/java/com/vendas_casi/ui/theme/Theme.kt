package com.vendas_casi.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// === Brand Colors ===
private val Primary = Color(0xFF090811)           // --primary-color
private val PrimaryDark = Color(0xFF100F1C)       // --primary-dark
private val Secondary = Color(0xFFF92F60)         // --secondary-color
private val SecondaryDark = Color(0xFF920B2F)     // --secondary-dark
private val SecondaryAccent = Color(0xFFF2C00A)   // --secondary-accent

private val BackgroundDark = Color(0xFF090811)    // --dark-bg
private val DarkPurple = Color(0xFF231D36)        // --dark-purple
private val TrackColor = Color(0xFFF0F0F0)        // --track-color

private val White = Color(0xFFFFFFFF)             // --white-color
private val LightText = Color(0xFFE0E0E0)         // --light-text
private val LighterText = Color(0xFFF5F5F5)       // --lighter-text
private val QuoteColor = Color(0xFFA1A1FF)        // --quote-color
private val Silver = Color(0xFFC0C0C0)            // --silver

private val DarkColorScheme = darkColorScheme(
    primary = White,
    onPrimary = PrimaryDark,
    secondary = SecondaryAccent,
    onSecondary = Color.Black,
    background = BackgroundDark,
    onBackground = LighterText,
    surface = PrimaryDark,
    onSurface = LighterText,
    surfaceVariant = DarkPurple,
    onSurfaceVariant = LightText
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = White,
    secondary = Secondary,
    onSecondary = White,
    background = White,
    onBackground = Color.Black,
    surface = White,
    onSurface = Color.Black,
    surfaceVariant = TrackColor,
    onSurfaceVariant = Color.DarkGray.copy(alpha = 0.6f)
)

@Composable
fun VendasCASITheme(
    darkTheme: Boolean? = null,
    content: @Composable () -> Unit
) {
    val isDark = darkTheme ?: true

    val colorScheme = if (isDark) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
