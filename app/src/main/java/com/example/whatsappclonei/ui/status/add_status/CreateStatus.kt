package com.example.whatsappclonei.ui.status.add_status

// ... (other imports)
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.whatsappclonei.ui.status.add_status.util.FirebaseStorageManager
import kotlinx.coroutines.launch

// ... (inside your composable where you upload the status)

@Composable
fun CreateStatusScreen(
uri: Uri
) {
    // ... other code

    var uploadProgress by remember { mutableStateOf(0f) }
    var isUploading by remember { mutableStateOf(false) }

    // ... (inside your button click or other upload trigger)

    val scope = rememberCoroutineScope()
  LaunchedEffect (Unit) {
        scope.launch {
            isUploading = true
            val downloadUrl = FirebaseStorageManager.uploadMedia(
                uri = uri, // Or selectedVideoUri
                type = "image", // Or "video"
                progress = mutableStateOf(uploadProgress)
            )
            isUploading = false
            // ... (handle the downloadUrl and update your database)
        }
    }

    // ... (in your UI layout)
    if (isUploading) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(
                progress = uploadProgress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .padding(horizontal = 16.dp),
                color = Color.Green
            )
        }
    }
    // ... (rest of your UI)
}