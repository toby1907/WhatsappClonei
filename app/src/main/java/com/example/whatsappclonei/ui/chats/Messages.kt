package com.example.whatsappclonei.ui.chats

import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.whatsappclonei.Constants.TAG
import com.example.whatsappclonei.R
import com.example.whatsappclonei.components.SmallSpacer
import com.example.whatsappclonei.ui.theme.WhatsAppColor
import com.example.whatsappclonei.ui.theme.WhatsappCloneiTheme
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage


data class MessageItem(
    val userIcon: String?,
    val userName: String?,
    val lastMessage: String?,
    val lastMessageTime: String?,
    val userIconUrl: String?,
    val userId: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageScreen(
    viewModel: ChatScreenViewModel = hiltViewModel(),
    navigateProfile: () -> Unit,
    openChat: (String) -> Unit
) {

    val accounts by viewModel.accounts.collectAsState()
    Log.d("message", "Accounts: $accounts")

    val messageItemss = accounts.map { user ->
        MessageItem(
            userName = user.username,
            lastMessage = user.lastmessage,
            userIcon = user.userIcon,
            lastMessageTime = null,
            userIconUrl = user.userIconUrl,
            userId = user.userId
        )
    }


    /* val messageItems = listOf(
         MessageItem(
             userIcon = R.drawable.avatar,
             userName = "John",
             lastMessage = "Hello!",
             lastMessageTime = "10:00 AM"
         ),
         MessageItem(
             userIcon = R.drawable.avatar,
             userName = "Jane",
             lastMessage = "Hi!",
             lastMessageTime = "11:00 AM"
         ),
         MessageItem(
             userIcon = R.drawable.avatar,
             userName = "Bob",
             lastMessage = "Hey!",
             lastMessageTime = "12:00 PM"
         )
     )*/
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Messages") },
                navigationIcon = {
                    IconButton(
                        onClick = { navigateProfile() }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.camera_icon),
                            contentDescription = "camera icon",
                            tint = Color(0xffC4C4C4)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(
                        0xFFFCFCFC
                    )
                )
            )
        },

        content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding
            ) {
                items(messageItemss.size) { messageItem ->
                    UserItem(messageItems = messageItemss, messageItem = messageItem,viewModel=viewModel, openScreen = openChat)

                }

            }
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(CenterVertically),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = CenterVertically

                    ) {
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.status_icon),
                                contentDescription = "Localized description"
                            )
                        }
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.call_icon),
                                contentDescription = "Localized description",
                            )
                        }
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.chats_icon),
                                contentDescription = "Localized description",
                            )
                        }
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.settings_icon),
                                contentDescription = "Localized description",
                            )
                        }
                    }
                },
                containerColor = Color(0xFFFCFCFC),
                contentColor = Color(0xffC4C4C4),


                )
        }
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserItem(messageItems: List<MessageItem>, messageItem: Int, viewModel: ChatScreenViewModel,openScreen:(String) -> Unit) {


    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable (true){

                                viewModel.onUserClick(openScreen, messageItems[messageItem].userId)
                },
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Row() {


                GetPic(messageItems = messageItems, messageItem = messageItem)
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(CenterVertically)
                ) {
                    messageItems[messageItem].userName?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    messageItems[messageItem].lastMessage?.let {
                        Text(
                            text = it,
                            fontSize = 14.sp
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(CenterVertically),
                horizontalAlignment = Alignment.End
            ) {
                messageItems[messageItem].lastMessageTime?.let {
                    Text(
                        text = it,
                        fontSize = 12.sp
                    )
                }
                SmallSpacer()
                Badge(containerColor = WhatsAppColor)
                {
                    val badgeNumber = "8"
                    Text(
                        badgeNumber,
                        modifier = Modifier.semantics {
                            contentDescription = "$badgeNumber new notifications"
                        }
                    )
                }

            }
        }
        HorizontalDivider(modifier = Modifier.padding(start = 16.dp, end = 16.dp))
    }
}

@Composable
fun GetPic(messageItems: List<MessageItem>, messageItem: Int) {
    val image = remember { mutableStateOf<ImageBitmap?>(null) }
    //  getPic(messageItems, messageItem) // this function sets the image.value
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    val context = LocalContext.current
    //val storage = FirebaseStorage.getInstance()
    val storage = Firebase.storage
    if (messageItems[messageItem].userIcon != null) {
        LaunchedEffect(messageItems[messageItem].userIcon) {
            val ref: StorageReference =
                storage.getReferenceFromUrl(messageItems[messageItem].userIcon!!)
            messageItems[messageItem].userIcon?.let { Log.d(TAG, it) }
            ref.downloadUrl.addOnSuccessListener { uri ->
                Log.d(TAG, "downloaded successfully")
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


    Image(
        painter = if (image.value == null) {
            painterResource(id = R.drawable.placeholder_foreground)
        } else BitmapPainter(image.value!!),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
    )
}

/*

@Preview(showBackground = true)
@Composable
fun MessageScreenPreview() {
    WhatsappCloneiTheme {
        MessageScreen { appState.navigate(com.example.whatsappclonei.screens.PROFILE_SCREEN) }
    }
}
*/


