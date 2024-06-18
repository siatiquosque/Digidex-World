package br.com.siatiquosque.digidexworld.presentation.theme

import androidx.compose.ui.graphics.Color



interface DigiColor {
    val PrimaryOrangeAgumon: Color
    val PrimaryOrangeAgumonLight: Color
    val PrimaryOrangeAgumonDark: Color
    val SecondaryBlueGabumon: Color
    val SecondaryBlueGabumonLight: Color
    val SecondaryBlueGabumonDark: Color
    val Surface: Color
    val SurfaceDark: Color
    val Background: Color
    val BackgroundDark: Color
    val Error: Color
    val ErrorLight: Color
    val ErrorDark: Color
    val TextPrimary: Color
    val TextSecondary: Color
    val OnPrimary: Color
    val OnSecondary: Color
    val OnSurface: Color
    val OnBackground: Color
    val OnError: Color
}

data class DigiColorLight(
    override val PrimaryOrangeAgumon: Color = Color(0xFFFF9800),
    override val PrimaryOrangeAgumonLight: Color = Color(0xFFFFC947),
    override val PrimaryOrangeAgumonDark: Color = Color(0xFFC66900),
    override val SecondaryBlueGabumon: Color = Color(0xFF64B5F6),
    override val SecondaryBlueGabumonLight: Color = Color(0xFF9BE7FF),
    override val SecondaryBlueGabumonDark: Color = Color(0xFF2286C3),
    override val Surface: Color = Color(0xFFFFFFFF),
    override val SurfaceDark: Color = Color(0xFFE0E0E0),
    override val Background: Color = Color(0xFFFAFAFA),
    override val BackgroundDark: Color = Color(0xFFE0E0E0),
    override val Error: Color = Color(0xFFCF6679),
    override val ErrorLight: Color = Color(0xFFFFCDD2),
    override val ErrorDark: Color = Color(0xFFB00020),
    override val TextPrimary: Color = Color(0xFF212121),
    override val TextSecondary: Color = Color(0xFF757575),
    override val OnPrimary: Color = Color(0xFFFFFFFF),
    override val OnSecondary: Color = Color(0xFFFFFFFF),
    override val OnSurface: Color = Color(0xFF212121),
    override val OnBackground: Color = Color(0xFF212121),
    override val OnError: Color = Color(0xFFFFFFFF),
) : DigiColor

data class DigiColorDark(
    override val PrimaryOrangeAgumon: Color = Color(0xFFFFB74D),
    override val PrimaryOrangeAgumonLight: Color = Color(0xFFFFD180),
    override val PrimaryOrangeAgumonDark: Color = Color(0xFFF57C00),
    override val SecondaryBlueGabumon: Color = Color(0xFF42A5F5),
    override val SecondaryBlueGabumonLight: Color = Color(0xFF80D6FF),
    override val SecondaryBlueGabumonDark: Color = Color(0xFF0077C2),
    override val Surface: Color = Color(0xFF121212),
    override val SurfaceDark: Color = Color(0xFF1E1E1E),
    override val Background: Color = Color(0xFF121212),
    override val BackgroundDark: Color = Color(0xFF1E1E1E),
    override val Error: Color = Color(0xFFCF6679),
    override val ErrorLight: Color = Color(0xFFFFCDD2),
    override val ErrorDark: Color = Color(0xFFB00020),
    override val TextPrimary: Color = Color(0xFFFFFFFF),
    override val TextSecondary: Color = Color(0xFFB3B3B3),
    override val OnPrimary: Color = Color(0xFF212121),
    override val OnSecondary: Color = Color(0xFF212121),
    override val OnSurface: Color = Color(0xFFFFFFFF),
    override val OnBackground: Color = Color(0xFFFFFFFF),
    override val OnError: Color = Color(0xFF000000),
) : DigiColor
