package com.example.whatsappclonei.data.model

import com.example.whatsappclonei.ui.theme.OffWhite_Dark
import com.example.whatsappclonei.ui.theme.OffWhite_Light
import com.example.whatsappclonei.ui.theme.PrimaryGray_A101
import com.example.whatsappclonei.ui.theme.PrimaryGreen_A101
import com.example.whatsappclonei.ui.theme.PrimaryGreen_A102

data class StatusItem(
    val statusId: String = "",
    val type: String = "", // "text", "image", "video"
    val content: String = "",
    val timestamp: com.google.firebase.Timestamp = com.google.firebase.Timestamp.now(),
    val duration: Long = 0L,
    val viewers: List<String> = emptyList()
)

data class Status(
    val userId: String = "",
    val userName: String = "",
    val userPhotoUrl: String = "",
    val statusItems: List<StatusItem> = emptyList(),
    val createdAt: com.google.firebase.Timestamp = com.google.firebase.Timestamp.now(),
    val expiresAt: com.google.firebase.Timestamp = com.google.firebase.Timestamp.now()
){
    companion object {
        val noteColors = listOf(OffWhite_Light, OffWhite_Dark, PrimaryGreen_A101, PrimaryGreen_A102, PrimaryGray_A101)
    }
}