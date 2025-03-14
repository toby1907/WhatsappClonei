package com.example.whatsappclonei.ui.status.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.whatsappclonei.R

@Composable
fun RemoteImage(
    imageUrl: String? = null,
    hasBorder: Boolean = false,
) {
    val context = LocalContext.current
    val imageRequest = ImageRequest.Builder(context)
        .placeholder(R.drawable.img_default_user)
        .data(imageUrl.takeIf { !it.isNullOrEmpty() } ?: R.drawable.img_default_user)
        .crossfade(true)
        .build()

    AsyncImage(
        model = imageRequest,
        contentDescription = null,
        modifier = Modifier
            .size(50.dp)
            .padding(if (hasBorder) 4.dp else 0.dp)
            .clip(CircleShape)
    )
}