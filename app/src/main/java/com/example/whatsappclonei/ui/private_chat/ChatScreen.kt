package com.example.whatsappclonei.ui.private_chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    val user by viewModel.user.collectAsState()
    LaunchedEffect(Unit){
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
                    Row(verticalAlignment = Alignment.CenterVertically,
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

            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    ,
                containerColor = Color(0xfffcfcfc),
            ) {

                ChatBottomAppBar()

            }

        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                ChatsScreen()
            }
        }
    )
}
@Composable
fun ChatBottomAppBar(){
    // Row to arrange elements horizontally
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Attach icon
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.attach),
                contentDescription = "Attach file",
                tint = Color.Black
            )
        }
        ChatEditText()

        // Microphone icon
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.microphone),
                contentDescription = "Record voice",
                tint = Color.Black
            )
        }
    }
}


@Composable
fun ChatEditText(){
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        placeholder = { Text("Type a message") },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.emoji),
                contentDescription = "Insert emoticon",
                tint = Color.Black
            )
        }
    )
}
@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    WhatsappCloneiTheme {
        ChatScreen({  }, "it.arguments?.getString(CHAT_ID")
    }
}