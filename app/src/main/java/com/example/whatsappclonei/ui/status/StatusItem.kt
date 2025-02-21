package com.example.whatsappclonei.ui.status

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.whatsappclonei.data.model.Status

@Composable
fun StatusItem(status: Status, onStatusClick: (Status) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onStatusClick(status) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = status.userPhotoUrl,
            contentDescription = "${status.userName}'s Status",
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(text = status.userName, fontWeight = FontWeight.Bold)
            Text(text = "Tap to view status update")
        }
    }
}

@Composable
fun MyStatusItem(onMyStatusClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onMyStatusClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = "https://placehold.co/150x150", // Replace with user's photo URL
            contentDescription = "My Status",
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(text = "My Status", fontWeight = FontWeight.Bold)
            Text(text = "Tap to add status update")
        }
    }
}