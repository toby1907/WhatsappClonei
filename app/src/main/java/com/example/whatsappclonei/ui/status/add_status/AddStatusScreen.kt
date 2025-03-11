package com.example.whatsappclonei.ui.status.add_status
/*import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.whatsappclonei.R
import com.example.whatsappclonei.data.model.Status
import com.example.whatsappclonei.ui.status.add_status.component.FileChooser
import com.example.whatsappclonei.ui.status.add_status.util.TextStyleState
import com.example.whatsappclonei.ui.status.util.rememberDrawController
import com.example.whatsappclonei.ui.theme.PrimaryGreen
import com.example.whatsappclonei.ui.theme.White*/

/*
@Composable
fun CreateStatusScreen(
    onCancelClick: () -> Unit,
    onTextClick: () -> Unit,
    onPaletteClick: () -> Unit,
    onVideoClick: () -> Unit,
    onMicClick: () -> Unit,
    navController: NavController,
    saveImage: (Bitmap) -> Unit,
    viewModel: AddStatusViewModel = hiltViewModel()
) {

    var fileChooserState by rememberSaveable {
        mutableStateOf(false)
    }

    fun showFileChooser() {
        fileChooserState = true
    }

    fun hideFileChooser() {
        fileChooserState = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val context = LocalContext.current
        val drawController = rememberDrawController()
        var text by remember { mutableStateOf(TextFieldValue("")) }
        var backgroundColor by remember {
            mutableStateOf(Status.noteColors[0]) }
        var textStyleState by remember { mutableStateOf(TextStyleState.Neutral) }
        val textStyle = when (textStyleState) {
            TextStyleState.Neutral -> TextStyle(
                fontSize = 22.5.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            TextStyleState.Italic -> TextStyle(
                fontSize = 22.5.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Italic,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            TextStyleState.Bold -> TextStyle(
                fontSize = 22.5.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }





        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                onCancelClick()
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
                    onTextClick()
                    textStyleState = when (textStyleState) {
                        TextStyleState.Neutral -> TextStyleState.Italic
                        TextStyleState.Italic -> TextStyleState.Bold
                        TextStyleState.Bold -> TextStyleState.Neutral
                    }
                }) {
                    Text(
                        text = "T",
                        style = textStyle
                    )
                }
                IconButton(onClick = { onPaletteClick()
                    backgroundColor = Status.noteColors.random()
                }) {
                    Icon(
                        painter = painterResource(R.drawable.palete_icon),
                        contentDescription = "Palette",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }


      AndroidView(
          factory = {
              ComposeView(context).apply {
                  setContent {
                      LaunchedEffect(drawController) {
                          drawController.trackBitmaps(
                              this@apply, this,
                              onCaptured = { imageBitmap, _ ->
                                  imageBitmap?.let { bitmap ->
                                      saveImage(bitmap.asAndroidBitmap())

                                  }
                              }
                          )


                      }

                      Column(
                          modifier = Modifier
                              .fillMaxHeight(0.8f)
                              .fillMaxWidth()
                              .weight(1f)
                              .padding(16.dp)
                              .background(backgroundColor),
                          horizontalAlignment = Alignment.CenterHorizontally,
                          verticalArrangement = Arrangement.Center
                      ) {
                          BasicTextField(
                              value = text,
                              onValueChange = { text = it },
                              textStyle = textStyle,
                              cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                              modifier = Modifier.fillMaxWidth(),
                              decorationBox = { innerTextField ->
                                  if (text.text.isEmpty()) {
                                      Text(
                                          text = stringResource(id = R.string.type_a_status),
                                          color = Color.Gray,
                                          fontSize = 24.sp,
                                          textAlign = TextAlign.Center,
                                          fontWeight = FontWeight.Bold
                                      )

                                      */
/*Text(
                                          text = "Type a status",
                                          style = TextStyle(
                                              fontSize = 38.sp,
                                              lineHeight = 46.sp,
                                              fontWeight = FontWeight(500),
                                              color = Color(0x66FFFFFF),

                                              textAlign = TextAlign.Center,
                                          )
                                      )*//*

                                  }
                                  innerTextField()
                              }
                          )
                      }
                  }


              }}
      )



        // Bottom Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .clickable(onClick = onVideoClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_video),
                        contentDescription = "Video",
                        tint = White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .clickable(onClick = {
                            showFileChooser()
                        }),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_photo),
                        contentDescription = "Photo",
                        tint = White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .clickable(onClick = onTextClick),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "T",
                        style = TextStyle(
                            fontSize = 22.5.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF000000),

                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(PrimaryGreen)
                    .clickable(onClick = {
                        drawController.saveBitmap()
                        onMicClick()

                    }),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.mic_icon),
                    contentDescription = "Mic",
                    tint = White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }

    if (fileChooserState) {

        FileChooser(
            onOk = {
                hideFileChooser()
            },
            saveSelectedUris = viewModel::uploadMedia,
            fileChooserState = fileChooserState
        )
    }
}
*/


import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.whatsappclonei.data.model.Response
import com.example.whatsappclonei.ui.status.add_status.util.BitmapUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStatusScreen(
    navController: NavController,
    viewModel: AddStatusViewModel = hiltViewModel()
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        uri?.let {
            bitmap = BitmapUtils.getBitmapFromUri(context, it)
        }
    }
    val addStatusResponse = viewModel.addStatusResponse.value

    LaunchedEffect(key1 = addStatusResponse) {
        when (addStatusResponse) {
            is Response.Success -> {
                if (addStatusResponse.data) {
                    Toast.makeText(context, "Status created successfully", Toast.LENGTH_SHORT).show()
                    navController.navigateUp()
                    viewModel.resetAddStatusResponse()
                }
            }

            is Response.Failure -> {
                Toast.makeText(context, "Error creating status", Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Status") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                        viewModel.resetAddStatusResponse()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .clickable {
                        launcher.launch("image/*")
                    },
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
                    Icon(
                        imageVector = Icons.Filled.Face,
                        contentDescription = "Add Image",
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        if (selectedImageUri != null) {
                            viewModel.uploadMedia(selectedImageUri!!, "image")
                        } else {
                            Toast.makeText(context, "Please select an image", Toast.LENGTH_SHORT).show()
                        }
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
            Spacer(modifier = Modifier.height(16.dp))
            if (addStatusResponse is Response.Loading || viewModel.isUploading.value) {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}