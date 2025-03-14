package com.example.whatsappclonei.ui.status.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclonei.ui.theme.Black
import com.example.whatsappclonei.ui.theme.OffWhite_Dark
import com.example.whatsappclonei.ui.theme.OffWhite_Light
import com.example.whatsappclonei.ui.theme.White


@Composable
fun StatusUpdateSection(
    header: String,
    subHeader: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = header,
            style = TextStyle(
                color = if (isSystemInDarkTheme()) White else Black,
                fontSize = 18.sp,
            )
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = subHeader,
            style = TextStyle(
                color = if (isSystemInDarkTheme()) OffWhite_Dark else OffWhite_Light,
                fontSize = 16.sp,
            )
        )
    }
}