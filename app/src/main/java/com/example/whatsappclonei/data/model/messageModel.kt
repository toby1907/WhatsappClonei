package com.example.whatsappclonei.data.model

// Data class for message model
data class MessageModel(
    val uid: String,
    val message: String,

    val messageId: String,

    val timestamp: Long?
)
{
    constructor() : this("", "", "", null)

}