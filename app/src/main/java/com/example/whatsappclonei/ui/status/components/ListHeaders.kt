package com.example.whatsappclonei.ui.status.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.whatsappclonei.ui.theme.OffWhite_Dark
import com.example.whatsappclonei.ui.theme.OffWhite_Light


@Composable
fun ListHeader(
    header: String
) {
    Text(
        text = header,
        style = TextStyle(
            color = if (isSystemInDarkTheme()) OffWhite_Dark else OffWhite_Light,
            fontSize = 16.sp,
        )
    )
}