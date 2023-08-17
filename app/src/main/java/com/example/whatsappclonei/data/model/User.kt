package com.example.whatsappclonei.data.model

data class User(
    val userIcon: String?,
    val username: String?,
    val email: String?,
    val password: String?,
    val lastmessage: String?,
    val userId: String,
    val userIconUrl: String?
) {




    constructor() : this(null, null, null, null, null,"",null)
}

