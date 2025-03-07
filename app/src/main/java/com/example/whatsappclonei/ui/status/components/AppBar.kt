package com.example.whatsappclonei.ui.status.components

import androidx.annotation.DimenRes
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclonei.R
import com.example.whatsappclonei.ui.theme.PrimaryGray_A101
import com.example.whatsappclonei.ui.theme.White

@Composable
fun AppBar(
    @DimenRes height: Int = R.dimen.app_bar_height,
    title: @Composable () -> Unit,

    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(height))
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,

    ) {
       title()

        Spacer(modifier = Modifier.weight(1f))
        ActionIcon(id = R.drawable.ic_camera_outline)
        Spacer(modifier = Modifier.width(20.dp))
        ActionIcon(id = R.drawable.ic_search_outline)
        Spacer(modifier = Modifier.width(16.dp))
        ActionIcon(id = R.drawable.ic_overflow_filled)

    }
}

@Composable
fun ActionIcon(id: Int) {
    Icon(
        painter = painterResource(id),
        contentDescription = "",
        tint = if (isSystemInDarkTheme()) PrimaryGray_A101 else White
    )
}