package com.example.whatsappclonei.ui.status.add_status

import java.io.File

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsappclonei.Constants.TAG
import com.example.whatsappclonei.data.AuthRepository
import com.example.whatsappclonei.data.StatusRepositoryImpl
import com.example.whatsappclonei.data.model.Response
import com.example.whatsappclonei.data.model.Status
import com.example.whatsappclonei.data.model.StatusItem
import com.example.whatsappclonei.ui.status.add_status.util.FirebaseStorageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddStatusViewModel @Inject constructor(
    private val repository: StatusRepositoryImpl
) : ViewModel() {

    var addStatusResponse = mutableStateOf<Response<Boolean>>(Response.Success(false))
        private set
    var uploadProgress = mutableStateOf(0f)
        private set
    var isUploading = mutableStateOf(false)
        private set

    fun createStatus(
        statusItem: StatusItem
    ) = viewModelScope.launch {
        addStatusResponse.value = Response.Loading
        try {
            val status = Status(
                statusItems = listOf(statusItem)
            )
            repository.createStatus(status).let { response ->
                addStatusResponse.value = response
            }
        } catch (e: Exception) {
            addStatusResponse.value = Response.Failure(e)
        }
    }
    fun resetAddStatusResponse() {
        addStatusResponse.value = Response.Success(false)
    }

    fun uploadMedia(uri: Uri, type: String) {
        viewModelScope.launch {
            isUploading.value = true
            val downloadUrl = FirebaseStorageManager.uploadMedia(
                uri = uri,
                type = type,
                progress = uploadProgress
            )
            isUploading.value = false
            if (downloadUrl != null) {
                val statusItem = when (type) {
                    "image" -> StatusItem(type = "image", imageUrl = downloadUrl)
                    "video" -> StatusItem(type = "video", videoUrl = downloadUrl)
                    else -> StatusItem(type = "text", text = "")
                }
                createStatus(statusItem)
            } else {
                Log.e(TAG, "Error uploading media")
            }
        }
    }

    fun saveImage(bitmap: Bitmap) {
        viewModelScope.launch {
            // Convert Bitmap to Uri
            val uri = bitmapToUri(bitmap)
            uploadMedia(uri, "image")
        }
    }

    private fun bitmapToUri(bitmap: Bitmap): Uri {
        // Implement the logic to convert Bitmap to Uri
        // This is a placeholder, you'll need to implement the actual conversion
        // Example: Save the bitmap to a temporary file and get the Uri
        // You can use a library like Glide or create your own implementation
        // For simplicity, let's assume you have a function called 'saveBitmapToFile'
        // that saves the bitmap and returns the Uri
        val file = saveBitmapToFile(bitmap)
        return Uri.fromFile(file)
    }

    private fun saveBitmapToFile(bitmap: Bitmap): File {
        // Implement the logic to save the bitmap to a file
        // This is a placeholder, you'll need to implement the actual saving
        // Example: Save the bitmap to a temporary file in the cache directory
        // You can use a library like Glide or create your own implementation
        // For simplicity, let's assume you have a function called 'saveBitmapToFile'
        // that saves the bitmap and returns the File
        // Example:
        // val file = File(context.cacheDir, "temp_image.jpg")
        // file.outputStream().use {
        //     bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        // }
        // return file
        TODO("Not yet implemented")
    }
}
