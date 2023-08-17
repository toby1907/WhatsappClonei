package com.example.whatsappclonei.ui.private_chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclonei.Constants.RECEIVER_VIEW_TYPE
import com.example.whatsappclonei.Constants.SENDER_VIEW_TYPE
import com.example.whatsappclonei.R
import com.example.whatsappclonei.data.model.MessageModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ChatsScreen(viewModel: PrivateChatScreenViewModel) {
    val messages by viewModel.messages.collectAsState()

 /*   val messageModelModels : List<MessageModel> = emptyList()
    return if(messageModelModels[position].uid == FirebaseAuth.getInstance().uid){

        SENDER_VIEW_TYPE
    } else{
        RECEIVER_VIEW_TYPE
    }*/

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(messages.size) { index ->

             val viewType = getItemViewType(index, messages)


            BoxWithConstraints(
                modifier = Modifier.fillMaxWidth()
            ) {


                if (viewType == SENDER_VIEW_TYPE) {
                    SenderChat(
                        message = messages[index]!!.message,
                        maxWidth = maxWidth,
                        backgroundColor = Color(0xFF1EBE71),
                        textColor = Color.White
                    )
                } else {
                    ReceiverChat(
                        message = messages[index]!!.message,
                        maxWidth = maxWidth,
                        backgroundColor = Color(0xFFF2F2F2),
                        textColor = Color(0xFF000000)
                    )
                }
            }
        }
    }
}

@Composable
fun SenderChat(
    message: String,
    maxWidth: Dp,
    backgroundColor: Color,
    textColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.End
    ) {
            Box(
                modifier = Modifier
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 10.dp)
                    )
                    .padding(16.dp)
                    .widthIn(max = maxWidth * 0.7f)
            ) {
                Text(
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(400),
                        color = textColor
                    ),
                    text = message,
                )
            }
          Image(
              painter = painterResource(id = R.drawable.placeholder_foreground),
              contentDescription = "Profile picture",
              modifier = Modifier
                  // Set image size to 40 dp
                  .size(16.dp)
                  // Clip image to be shaped as a circle
                  .clip(CircleShape)
                  // Align image to bottom end of row
                  .align(Alignment.Bottom)

          )
        }
    }


@Composable
fun ReceiverChat(
    message: String,
    maxWidth: Dp,
    backgroundColor: Color,
    textColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(id = R.drawable.placeholder_foreground),
            contentDescription = "Profile picture",
            modifier = Modifier
                // Set image size to 40 dp
                .size(16.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
                // Align image to bottom end of row
                .align(Alignment.Bottom)

        )
        Box(
            modifier = Modifier
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 0.dp)
                )
                .padding(16.dp)
                .widthIn(max = maxWidth * 0.7f)
        ) {
            Text(
                text = message,
                color = textColor,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400)),
                    textAlign = TextAlign.Left,
            )
        }

    }
}

fun getItemViewType(position:Int,messageModels:List<MessageModel?>) : Int {
   // val messageModelModels : List<MessageModel> = emptyList()
    return if(messageModels[position]?.uid == FirebaseAuth.getInstance().uid){

        SENDER_VIEW_TYPE
    } else{
        RECEIVER_VIEW_TYPE
    }
}
