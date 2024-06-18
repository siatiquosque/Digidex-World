package br.com.siatiquosque.digidexworld.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalColors = staticCompositionLocalOf<DigiColor> {
    DigiColorLight()
}

private fun LightColorPalette(digiColorDark: DigiColorLight = DigiColorLight()) = lightColorScheme(
    primary = digiColorDark.PrimaryOrangeAgumon,
    onPrimary = digiColorDark.OnPrimary,
    primaryContainer = digiColorDark.PrimaryOrangeAgumonLight,
    onPrimaryContainer = digiColorDark.TextPrimary,
    secondary = digiColorDark.SecondaryBlueGabumon,
    onSecondary = digiColorDark.OnSecondary,
    secondaryContainer = digiColorDark.SecondaryBlueGabumonLight,
    onSecondaryContainer = digiColorDark.TextPrimary,
    background = digiColorDark.Background,
    onBackground = digiColorDark.TextPrimary,
    surface = digiColorDark.Surface,
    onSurface = digiColorDark.TextPrimary,
    error = digiColorDark.Error,
    onError = digiColorDark.OnError,
    errorContainer = digiColorDark.ErrorLight,
    onErrorContainer = digiColorDark.TextPrimary
)

private fun DarkColorPalette(digiColorDark: DigiColorDark = DigiColorDark()) = darkColorScheme(
    primary = digiColorDark.PrimaryOrangeAgumon,
    onPrimary = digiColorDark.OnPrimary,
    primaryContainer = digiColorDark.PrimaryOrangeAgumonLight,
    onPrimaryContainer = digiColorDark.TextPrimary,
    secondary = digiColorDark.SecondaryBlueGabumon,
    onSecondary = digiColorDark.OnSecondary,
    secondaryContainer = digiColorDark.SecondaryBlueGabumonLight,
    onSecondaryContainer = digiColorDark.TextPrimary,
    background = digiColorDark.Background,
    onBackground = digiColorDark.TextPrimary,
    surface = digiColorDark.Surface,
    onSurface = digiColorDark.TextPrimary,
    error = digiColorDark.Error,
    onError = digiColorDark.OnError,
    errorContainer = digiColorDark.ErrorLight,
    onErrorContainer = digiColorDark.TextPrimary
)

@Composable
fun DigimonAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DigiColorDark()
    } else {
        DigiColorLight()
    }

    val colorsMaterialMap = if (darkTheme) {
        DarkColorPalette()
    } else {
        LightColorPalette()
    }

    CompositionLocalProvider(
        LocalColors provides colors,
        content = {
            MaterialTheme (
                colorScheme = colorsMaterialMap,
                content = content
            )
        }
    )
}

object DigiTheme {
    val colors: DigiColor
        @Composable
        get() = LocalColors.current
}
