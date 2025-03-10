package com.example.whatsappclonei.data.model

import com.example.whatsappclonei.ui.theme.OffWhite_Dark
import com.example.whatsappclonei.ui.theme.OffWhite_Light
import com.example.whatsappclonei.ui.theme.PrimaryGray_A101
import com.example.whatsappclonei.ui.theme.PrimaryGreen_A101
import com.example.whatsappclonei.ui.theme.PrimaryGreen_A102
import com.google.firebase.Timestamp


data class StatusItem(
    val statusId: String = "",
    val type: String = "text", // "text", "image", "video"
    val text: String? = null,
    val imageUrl: String? = null,
    val videoUrl: String? = null,
    val timestamp: Timestamp = Timestamp.now(),
    val duration: Long = 0L, // Duration in milliseconds (for videos)
    val viewers: List<String> = emptyList()
)

data class Status(
    val userId: String = "",
    val userName: String = "",
    val userPhotoUrl: String = "",
    val statusItems: List<StatusItem> = emptyList(),
    val createdAt: Timestamp = Timestamp.now(),
    val expiresAt: Timestamp = Timestamp.now()
){
    companion object {
        val noteColors = listOf(OffWhite_Light, OffWhite_Dark, PrimaryGreen_A101, PrimaryGreen_A102, PrimaryGray_A101)
    }
}