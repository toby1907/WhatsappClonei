package com.example.whatsappclonei.ui.status.preview

import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.whatsappclonei.R
import com.example.whatsappclonei.data.model.Response
import com.example.whatsappclonei.ui.status.add_status.AddStatusViewModel
import com.example.whatsappclonei.ui.status.add_status.util.BitmapUtils

@Composable
fun ImagePreviewScreen(
    navController: NavController,
    imageUri: Uri,
    viewModel: AddStatusViewModel = hiltViewModel()
){


    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val addStatusResponse = viewModel.addStatusResponse.value

    LaunchedEffect(key1 = imageUri) {
       bitmap = BitmapUtils.getBitmapFromUri(context, imageUri)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(
                    painter = painterResource(R.drawable.cancel_icon),
                    contentDescription = "Cancel",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {


                }) {
                    Text(
                        text = "T",

                    )
                }
                IconButton(onClick = {

                }) {
                    Icon(
                        painter = painterResource(R.drawable.palete_icon),
                        contentDescription = "Palette",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .background(Color.LightGray)
                .weight(1f)
                .clickable {

                }

            ,
            contentAlignment = Alignment.Center
        ) {
            if (bitmap != null) {
                Image(
                    bitmap = bitmap!!.asImageBitmap(),
                    contentDescription = "Selected Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    "No image selected"
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    viewModel.uploadMedia(imageUri, "image")
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "Send",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Send")
            }
        }
        if (addStatusResponse is Response.Loading || viewModel.isUploading.value) {
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

}
