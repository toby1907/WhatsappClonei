package com.example.whatsappclonei.ui.status.add_status.component

import android.app.Activity
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.whatsappclonei.ui.snackbar.SnackbarController
import com.example.whatsappclonei.ui.snackbar.SnackbarEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FileChooser(
    onOk: () -> Unit,
    saveSelectedUris: (uri: Uri, type: String) -> Unit,
    fileChooserState: Boolean,
) {
    val scope = CoroutineScope(Dispatchers.Main)
    // Get the current activity from the LocalContext
    val activity = LocalContext.current as Activity
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris ->
            // Check the size of the list
            when (uris.size) {
                0 -> {
                    // Handle the empty case, such as showing a message
                    Toast.makeText(activity, "No file selected", Toast.LENGTH_SHORT).show()
                    onOk()
                }

                1 -> {
                    // Handle the single file case, such as displaying the image

                    onOk()
                    // Use the uri to load the image from Coil or other libraries
                    saveSelectedUris(uris.single(), "image")
                }

                else -> {
                    // Handle the multiple files case, such as displaying the images
                    val selectedStrings = uris.map { it.toString() }
                 //   saveSelectedUris(selectedStrings, "image")
                    scope.launch {
                        SnackbarController.sendEvent(
                            event = SnackbarEvent(
                                message = "Multiple files selected, you can only select one image",
                            )
                        )
                    }
                    onOk()
                    // Use the uris to load the images from Coil or other libraries
                }
            }
        }
    )

    LaunchedEffect(key1 = true) {
        if (fileChooserState) {
            launcher.launch("image/*")
        }
    }


}