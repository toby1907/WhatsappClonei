package com.example.whatsappclonei.data.model

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
)