package com.example.whatsappclonei.data

import com.example.whatsappclonei.data.model.Response
import com.example.whatsappclonei.data.model.Status
import com.example.whatsappclonei.data.model.StatusItem

interface StatusRepository {
    suspend fun createStatus(status: Status): Response<Boolean>
    suspend fun getStatuses(): Response<List<Status>>
    suspend fun addViewer(userId: String, statusId: String, statusItem: StatusItem): Response<Boolean>
    suspend fun deleteExpiredStatuses(): Response<Boolean>
}