package com.example.whatsappclonei.data

import android.util.Log
import com.example.whatsappclonei.Constants.TAG
import com.example.whatsappclonei.data.model.Response
import com.example.whatsappclonei.data.model.Status
import com.example.whatsappclonei.data.model.StatusItem
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StatusRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : StatusRepository {

    override suspend fun createStatus(status: Status): Response<Boolean> {
        return try {
            val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")

            val userDocument = firestore.collection("Users").document(userId).get().await()
            val userName = userDocument.getString("username") ?: ""
            val userPhotoUrl = userDocument.getString("userPhotoUrl") ?: ""
            val statusDocumentRef = firestore.collection("statuses").document(userId)
            val existingStatus = statusDocumentRef.get().await()
            val now = Timestamp.now()
            val expiresAt = Timestamp(now.seconds + 86400, now.nanoseconds) // 24 hours from now

            if (existingStatus.exists()) {
                // Update existing status
                val existingStatusData = existingStatus.toObject<Status>()
                val updatedStatusItems = existingStatusData?.statusItems?.toMutableList() ?: mutableListOf()
                updatedStatusItems.addAll(status.statusItems)

                statusDocumentRef.update(mapOf(
                    "statusItems" to updatedStatusItems,
                    "expiresAt" to expiresAt
                )).await()
            }
            else {
                // Create new status
                val newStatus = Status(
                    userId = userId,
                    userName = userName,
                    userPhotoUrl = userPhotoUrl,
                    statusItems = status.statusItems,
                    createdAt = now,
                    expiresAt = expiresAt
                )
                statusDocumentRef.set(newStatus).await()
            }

            Response.Success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error creating status: ${e.message}")
            Response.Failure(e)
        }
    }

    override suspend fun getStatuses(): Response<List<Status>> {
        return try {
            // Get the current user's ID (we don't need it for now, but we might later)
            val currentUserId = auth.currentUser?.uid ?: throw Exception("User not authenticated")

            // Option 1: Get all statuses (no contact filtering)
            val statusesQuery = firestore.collection("statuses")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()

            // Option 2: Get statuses from contacts (when contact feature is implemented)
            /*
            // Fetch the current user's document from Firestore
            val userDocument = firestore.collection("Users")
                .document(currentUserId)
                .get()
                .await()

            // Extract the contactIds from the user document
            val contactIds = userDocument.get("contacts") as? List<String> ?: emptyList()

            // Query Firestore for statuses from contacts
            val statusesQuery = firestore.collection("statuses")
                .whereIn("userId", contactIds)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()
            */

            val statuses = statusesQuery.mapNotNull { document ->
                document.toObject<Status>()
            }

            Response.Success(statuses)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting statuses: ${e.message}")
            Response.Failure(e)
        }
    }

    override suspend fun addViewer(userId: String, statusId: String, statusItem: StatusItem): Response<Boolean> {
        return try {
            val currentUserId = auth.currentUser?.uid ?: throw Exception("User not authenticated")
            val statusDocumentRef = firestore.collection("statuses").document(userId)
            val statusDocument = statusDocumentRef.get().await()
            if (statusDocument.exists()) {
                val status = statusDocument.toObject<Status>()
                if (status != null) {
                    val updatedStatusItems = status.statusItems.map {
                        if (it.statusId == statusId) {
                            if (!it.viewers.contains(currentUserId)) {
                                it.copy(viewers = it.viewers + currentUserId)
                            } else {
                                it
                            }
                        } else {
                            it
                        }
                    }
                    statusDocumentRef.update("statusItems", updatedStatusItems).await()
                    Response.Success(true)
                } else {
                    Response.Failure(Exception("Status not found"))
                }
            } else {
                Response.Failure(Exception("Status not found"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error adding viewer: ${e.message}")
            Response.Failure(e)
        }
    }

    override suspend fun deleteExpiredStatuses(): Response<Boolean> {
        return try {
            val now = Timestamp.now()
            val statusesQuery = firestore.collection("statuses")
                .whereLessThan("expiresAt", now)
                .get()
                .await()
            statusesQuery.forEach { document ->
                document.reference.delete().await()
            }
            Response.Success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting expired statuses: ${e.message}")
            Response.Failure(e)
        }
    }
}