package com.example.whatsappclonei.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = ColorPrimaryDary,
    secondary = SecondaryColor,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = WhatsAppColor,
    secondary = SecondaryColor,
    tertiary = Pink40



    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun WhatsappCloneiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        val systemUiController = rememberSystemUiController()
        val statusBarColor = if (isSystemInDarkTheme()) {
            PrimaryGray
        } else {
            PrimaryGreen
        }
        val navigationBarColor = if (isSystemInDarkTheme()) {
            SecondaryGray_10
        } else {
            White_100
        }
        SideEffect {
            systemUiController.setStatusBarColor(
                statusBarColor
            )
            systemUiController.setNavigationBarColor(
                navigationBarColor
            )
        }
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
