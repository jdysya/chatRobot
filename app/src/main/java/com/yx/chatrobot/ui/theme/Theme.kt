package com.yx.chatrobot.ui.theme

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yx.chatrobot.ui.AppViewModelProvider
import com.yx.chatrobot.ui.config.ConfigViewModel
import com.yx.chatrobot.ui.login.LoginViewModel

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun ChatRobotTheme(
    loginViewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory),
    content: @Composable () -> Unit
) {
    val themeState = loginViewModel.themeState.collectAsState().value
    val fontState = loginViewModel.fontState.collectAsState().value
    val colors = if (themeState) DarkColorPalette else LightColorPalette
    val fontTypography = when (fontState) {
        "小" -> TypographySmall
        "中" -> TypographyMedium
        "大" -> TypographyLarge
        else -> TypographyMedium
    }
    MaterialTheme(
        colors = colors,
        typography = fontTypography,
        shapes = Shapes,
        content = content
    )
}