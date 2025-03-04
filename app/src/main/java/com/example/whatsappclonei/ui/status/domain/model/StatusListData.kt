package com.example.whatsappclonei.ui.status.domain.model

import com.example.whatsappclonei.ui.status.domain.StatusUpdateCategory

data class StatusListData(
    val id: Int,
    val category: StatusUpdateCategory,
    val statusImage: String? = null,
    val statusCount: Int? = null,
    val userName: String,
    val timeStamp: String,
)