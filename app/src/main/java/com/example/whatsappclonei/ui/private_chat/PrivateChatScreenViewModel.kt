package com.example.whatsappclonei.ui.private_chat

import android.content.Context
import android.media.MediaRecorder
import android.os.Environment
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsappclonei.Constants.TAG
import com.example.whatsappclonei.components.ext.idFromParameter
import com.example.whatsappclonei.data.AuthRepository
import com.example.whatsappclonei.data.model.MessageModel
import com.example.whatsappclonei.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PrivateChatScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val context: Context,
    private val repo: AuthRepository
) : ViewModel() {

    val recorder: MediaRecorder = MediaRecorder()
    private var audioPath: String = ""

    val user = MutableStateFlow<User?>(null) // Create a state flow to hold the user data
    val messages = MutableStateFlow<List<MessageModel?>>(emptyList())
    var receiverId: String = ""

    var messageState = mutableStateOf(MessageUiState())
        private set

    private val message
        get() = messageState.value.message

    private val timestamp
        get() = messageState.value.timestamp

    fun onMessageChange(newValue: String) {
        messageState.value = messageState.value.copy(message = newValue)
    }

    fun onTimeChange(newValue: Long) {
        messageState.value = messageState.value.copy(timestamp = newValue)
    }

    fun initialize(chatId: String) {
        viewModelScope.launch {
            repo.getUser(chatId.idFromParameter()).collect { user1 ->
                // Update the state flow with the user data
                receiverId = chatId
                user.value = user1
                Log.d(TAG, "$user1")
            }
            repo.getChats(receiverId).collect {
                messages.value = emptyList()
                messages.value = it
                Log.d(TAG, "list of messages during initalization ${messages.value}")
            }
        }
    }

    fun onMessageSent() {
        viewModelScope.launch {
            if (message.isNotEmpty()) {
                repo.createChats(message, receiverId).collect {
                    if (it) {
                        Log.d(TAG, "message sent successfully")
                    } else {
                        Log.d(TAG, "message failed")
                    }
                    repo.loadPrivateChats(receiverId).collect { messagesList ->
                        messages.value = emptyList()
                        messages.value = messagesList
                        Log.d(TAG, "list of messages after creation ${messages.value}")
                    }

                }
            }

        }

    }

    private fun setUpRecording() {

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)


        val file = File(
            Environment.getExternalStorageDirectory().absolutePath,
            "WhatsappClonei/Media/Recording"
        )

        if (!file.exists()) {
            file.mkdirs()
        }
        audioPath = "${file.absolutePath}+${File.separator}+${System.currentTimeMillis()}+.3gp"

        recorder.setOutputFile(audioPath)
    }

    fun onStartRecord() {
        //StartRecording...
        Log.d(TAG, "Recording Started")
        setUpRecording()
        try {
            recorder.prepare()
            recorder.start()
        } catch (e: IOException) {
            Log.d(TAG, "$e")
        }
    }

    fun onCancelRecord() {
        Log.d(TAG, "onCancelRecording")
        recorder.reset()
        recorder.release()

        val file = File(audioPath)
        if (file.exists()) {
            file.delete()
        }


    }

    fun onFinishRecording(recordTime: Long) {
        Log.d(TAG, "onFinishRecord")
        recorder.stop()
        recorder.release()
        viewModelScope.launch {
            repo.sendRecordingMessage(audioPath,receiverId)

        }
    }

    fun onLessThanSecond() {
        recorder.reset()
        recorder.release()

        val file = File(audioPath)
        if (file.exists()) {
            file.delete()
        }
    }

}