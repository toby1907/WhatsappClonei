package com.example.whatsappclonei.data.model

// Data class for message model

enum class MessageType {
    RECORDING,
    MESSAGE,
    PICTURE
}
data class MessageModel(
    val uid: String,
    val message: String,

    val messageId: String,

    val timestamp: Long?,
    val audioUrl: String?,
    val messageType : MessageType
)
{
    constructor() : this("", "", "", null,"",MessageType.MESSAGE)

}