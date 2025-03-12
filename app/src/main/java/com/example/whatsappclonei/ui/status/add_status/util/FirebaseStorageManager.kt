package com.example.whatsappclonei.ui.status.add_status.util

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.util.UUID

object FirebaseStorageManager {

    private val storage = Firebase.storage
    private val storageRef = storage.reference

    suspend fun uploadMedia(
        uri: Uri,
        type: String,
        progress: MutableState<Float> = mutableStateOf(0f)
    ): String? {
        val mediaRef: StorageReference = when (type) {
            "image" -> storageRef.child("images/${UUID.randomUUID()}")
            "video" -> storageRef.child("videos/${UUID.randomUUID()}")
            else -> return null
        }

        return try {
            val uploadTask = mediaRef.putFile(uri)
            uploadTask.addOnProgressListener { taskSnapshot ->
                val currentProgress =
                    100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                progress.value = currentProgress.toFloat() / 100f
                Log.d("Upload", "Upload is $currentProgress% done")
            }
            uploadTask.await()
            mediaRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}