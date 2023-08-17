package com.example.whatsappclonei.ui.private_chat

import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.whatsappclonei.Constants
import com.example.whatsappclonei.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

@Composable
fun ProfilePictureWithIndicator(userIconUrl: String?) {

    val image = remember { mutableStateOf<ImageBitmap?>(null) }
    //  getPic(messageItems, messageItem) // this function sets the image.value
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    val context = LocalContext.current
    //val storage = FirebaseStorage.getInstance()
    val  storage = Firebase.storage
    if(userIconUrl!=null) {
        LaunchedEffect(userIconUrl) {
            val ref: StorageReference =
                storage.getReferenceFromUrl(userIconUrl)
            ref.downloadUrl.addOnSuccessListener { uri ->
                Log.d(Constants.TAG, "downloaded successfully")
                val imageLoader = ImageLoader.Builder(context).build()
                val request = ImageRequest.Builder(context)
                    .data(uri)
                    .target { drawable ->
                        image.value = (drawable as BitmapDrawable).bitmap.asImageBitmap()
                    }
                    .build()
                imageLoader.enqueue(request)

            }
        }
    }







    // A variable that indicates whether the user is online or not
    val isOnline = true

// A color for the badge when the user is online
    val onlineColor = Color.Green

// A color for the badge when the user is offline
    val offlineColor = Color.Gray

    Box(modifier = Modifier.padding(all = 8.dp)) {
        // The profile picture
        Image(
            painter =   if(image.value==null){
                painterResource(id = R.drawable.placeholder_foreground)
            }
            else BitmapPainter(image.value!!),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )
        // The badge
        Image(
            painter = painterResource(id = R.drawable.indicator),
            contentDescription = "Badge",
            // Change the color of the badge based on the user's online status
            colorFilter = ColorFilter.tint(if (isOnline) onlineColor else offlineColor),
            modifier = Modifier
                // Align the badge to the bottom end of the box
                .align(Alignment.BottomEnd)
                // Set badge size to 8 dp
                .size(8.dp)
                // Clip badge to be shaped as a circle
                .clip(CircleShape)
        )
    }
}

