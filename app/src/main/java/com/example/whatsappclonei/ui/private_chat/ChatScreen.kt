package com.example.whatsappclonei.ui.private_chat

import android.accessibilityservice.AccessibilityService.SoftKeyboardController
import android.view.LayoutInflater
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.whatsappclonei.R
import com.example.whatsappclonei.ui.theme.WhatsappCloneiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    popUpScreen: () -> Unit,
    userId: String,
    viewModel: PrivateChatScreenViewModel = hiltViewModel()
) {
    val cardVisible = remember { mutableStateOf(false) }
    val user by viewModel.user.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.initialize(userId)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    user?.username?.let {
                        Text(
                            text = it,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,

                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF000000),
                            )
                        )
                    }
                },


                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(
                        0xFFFCFCFC
                    )
                ),
                navigationIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        IconButton(onClick = { /* go back */ }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                        Text(
                            text = "Message",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF000000),
                                textAlign = TextAlign.Center,
                            )
                        )
                        Spacer(Modifier.padding(horizontal = 38.dp))
                    }

                },
                actions = {
                    IconButton(onClick = { /* open profile */ }) {
                        /* Icon(
                             painterResource(R.drawable.placeholder_foreground),
                             contentDescription = "Profile"
                         )*/
                        ProfilePictureWithIndicator(user?.userIcon)
                    }
                },

                )

        },
        bottomBar = {

            ChatBottomAppBar(viewModel = viewModel, cardVisibilty = { cardVisible.value = !cardVisible.value})


        },
        content = {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                ChatsScreen(viewModel)

            }
            AttachmentIconScreen(cardVisible = cardVisible.value,it)

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttachmentIconScreen(cardVisible:Boolean,paddingValues:PaddingValues){
    if (cardVisible) {
        Row(modifier= Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center)  {
            Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFCFCFC)),
                onClick = { /* Do something */ },
                modifier = Modifier
                    // Use a fixed size modifier with 375 x 291
                    .size(width = 375.dp, height = 291.dp)
                    .align(Alignment.Bottom)
                    .padding(paddingValues)
            ) {
                // A lazy vertical grid with 3 items in a row and scrollable
                LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                    items(7) { index ->
                        // A list of icons and texts for each item
                        val icons = listOf(
                            R.drawable.document,
                            R.drawable.camera,
                            R.drawable.gallery,
                            R.drawable.audio,
                            R.drawable.location,
                            R.drawable.person,
                            R.drawable.poll,

                            )
                        val texts = listOf(
                            "Document",
                            "Camera",
                            "Gallery",
                            "Audio",
                            "Location",
                            "Contact",
                            "Poll",
                        )
                        val colors = listOf(
                            Color(0xFF1EBE71),
                            Color(0xFFF5D941),
                            Color(0xFF1E98BE),
                            Color(0xFF8BBE1E),
                            Color(0xFFDB3227),
                            Color(0xFFFFA800),
                            Color(0xFFF5D941)

                        )
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            IconButton(
                                modifier = Modifier
                                    .size(56.dp)
                                    .background(shape = CircleShape, color = colors[index]),
                                onClick = { /* Do something */ }) {
                                Icon(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .size(24.dp),
                                    painter = painterResource(id = icons[index]),
                                    tint = Color.White,
                                    contentDescription = texts[index]
                                )
                            }
                            Text(texts[index], Modifier.padding(top = 4.dp))
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun ChatBottomAppBar(viewModel: PrivateChatScreenViewModel, cardVisibilty: () -> Unit) {

    val keyboardController = LocalSoftwareKeyboardController.current

    // Row to arrange elements horizontally

    BottomAppBar(
        modifier = Modifier
            .wrapContentHeight()
            ,
        containerColor =  Color(0xFFFCFCFC)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Attach icon
            IconButton(onClick = {cardVisibilty.invoke() }) {
                Icon(
                    painter = painterResource(id = R.drawable.attach),
                    contentDescription = "Attach file",
                    tint = Color(0xff808080)
                )
            }
            // Create a mutable state variable to store the current icon
            val currentIcon = remember { mutableStateOf(R.drawable.microphone) }
            ChatEditText(
                text = viewModel.messageState.value.message,
                // Update the icon based on the text input
                onValueChange = { text ->
                    viewModel.onMessageChange(text)

                    if (text.isEmpty()) {
                        currentIcon.value = R.drawable.microphone
                    } else {
                        currentIcon.value = R.drawable.send_icon
                    }
                },
                onSendActionClicked = {
                    viewModel.onMessageSent()
                    keyboardController?.hide()
                    viewModel.messageState.value =
                        viewModel.messageState.value.copy(message = "")
                }
            )

            // Microphone/Send icon
            IconButton(
                onClick = {
                    // Send the message if the icon is send
                    if (currentIcon.value == R.drawable.send_icon) {

                        viewModel.onMessageSent()
                        keyboardController?.hide()
                        viewModel.messageState.value =
                            viewModel.messageState.value.copy(message = "")

                    } else {
                        // TODO: record voice logic
                    }
                },
                // Add a circle shape and a green background
                modifier = Modifier
                    .background(color = Color.White, shape = CircleShape)
                    .width(48.dp)
                    .height(48.dp),

                ) {

                Icon(
                    painter = painterResource(id = currentIcon.value),
                    contentDescription = "Record voice or send message",
                    tint = Color(0xff808080)
                )
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatEditText(
    text: String,
    onValueChange: (newValue: String) -> Unit,
   onSendActionClicked : () -> Unit
) {

    val emojiOn = remember { mutableStateOf(false) }
    TextField(
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedContainerColor = Color(0xFFF2F2F2),
            unfocusedContainerColor = Color(0xFFF2F2F2),
        ),
        maxLines = 10,
        value = text,
        onValueChange = {
            onValueChange(it)
        },
        placeholder = { Text("Type a message") },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.emoji),
                contentDescription = "Insert emoticon",
                tint = Color(0xff808080),
                modifier = Modifier.clickable {
                    emojiOn.value = !emojiOn.value
                }

            )
        },
        shape = RoundedCornerShape(40.dp),
        modifier = Modifier
            .width(248.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Send,
            keyboardType =  if (emojiOn.value) KeyboardType.Ascii else KeyboardType.Text,
            ),
        keyboardActions = KeyboardActions {  onSendActionClicked() }
    )
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    WhatsappCloneiTheme {
        ChatScreen({ }, "it.arguments?.getString(CHAT_ID")
    }
}