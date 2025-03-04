package com.example.whatsappclonei.ui.status.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclonei.ui.status.domain.model.ChatListData
import com.example.whatsappclonei.ui.status.domain.model.MessageDeliveryStatus
import com.example.whatsappclonei.ui.status.domain.model.MessageType
import com.example.whatsappclonei.ui.theme.Black
import com.example.whatsappclonei.ui.theme.OffWhite
import com.example.whatsappclonei.ui.theme.OffWhite_Dark
import com.example.whatsappclonei.ui.theme.OffWhite_Light
import com.example.whatsappclonei.ui.theme.White
import com.example.whatsappclonei.R
import com.example.whatsappclonei.ui.theme.Blue_Dark
import com.example.whatsappclonei.ui.theme.Blue_Light


@Composable
fun UserAndMessageDetails(
    chatData: ChatListData
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(start = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        MessageHeader(chatData)
        SubMessage(chatData)
    }
}

@Composable
private fun MessageHeader(chatData: ChatListData) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = chatData.userName,
            modifier = Modifier.weight(1f),
            style = TextStyle(
                color = if (isSystemInDarkTheme()) White else Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
            )
        )
        Text(
            text = chatData.timeStamp,
            style = TextStyle(
                color = OffWhite,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
            )
        )
    }
}

@Composable
private fun SubMessage(chatData: ChatListData) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (chatData.lastMessageSentByMe) {
            DeliveryStatusIcon(chatData.deliveryStatus)
        }
        if (chatData.messageType != MessageType.TEXT) {
            MessageMediaIcon(chatData.messageType)
        }

        val message =
            subMessage(chatData.lastMessageSentByMe, chatData.messageType, chatData.message)
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = if (chatData.lastMessageSentByMe) {
                        2.dp
                    } else if (chatData.messageType != MessageType.TEXT) {
                        2.dp
                    } else {
                        0.dp
                    },
                ),
            text = message,
            style = TextStyle(
                color = if (isSystemInDarkTheme()) OffWhite_Dark else OffWhite_Light,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
            ),
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun MessageMediaIcon(messageType: MessageType) {
    if (messageType != MessageType.TEXT || messageType != MessageType.REACTION) {
        val icon = when (messageType) {
            MessageType.TEXT -> TODO()
            MessageType.VIDEO -> R.drawable.ic_video
            MessageType.IMAGE -> R.drawable.ic_photo
            MessageType.AUDIO -> R.drawable.ic_audio
            MessageType.REACTION -> TODO()
        }
        Image(
            modifier = Modifier.size(18.dp),
            painter = painterResource(id = icon),
            contentDescription = "media type",
            colorFilter = ColorFilter.tint(OffWhite)
        )
    }
}

@Composable
private fun DeliveryStatusIcon(deliveryStatus: MessageDeliveryStatus?) {
    val deliveryIcon = when (deliveryStatus) {
        MessageDeliveryStatus.NOT_SENT -> R.drawable.ic_undelivered
        MessageDeliveryStatus.PENDING -> R.drawable.ic_single_check
        MessageDeliveryStatus.DELIVERED, MessageDeliveryStatus.READ -> R.drawable.ic_double_check
        else -> null
    }
    Image(
        modifier = Modifier.size(18.dp),
        painter = painterResource(id = deliveryIcon!!),
        contentDescription = "delivery status",
        colorFilter = ColorFilter.tint(
            if (deliveryStatus == MessageDeliveryStatus.READ) {
                if (isSystemInDarkTheme()) {
                    Blue_Dark
                } else {
                    Blue_Light
                }
            } else {
                OffWhite
            }
        )
    )
}

private fun subMessage(
    messageSentByMe: Boolean,
    messageType: MessageType,
    message: String?
): String {
    return when (messageType) {
        MessageType.TEXT -> message!!
        MessageType.VIDEO -> "Video"
        MessageType.IMAGE -> "Photo"
        MessageType.AUDIO -> "Audio"
        MessageType.REACTION -> "${if (messageSentByMe) "You " else ""}reacted to a message"
    }
}