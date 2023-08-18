package com.example.whatsappclonei.ui.private_chat

import android.content.Context
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
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class PrivateChatScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val context: Context,
    private val repo: AuthRepository
): ViewModel() {


    val user = MutableStateFlow<User?>(null) // Create a state flow to hold the user data
    val messages = MutableStateFlow<List<MessageModel?>>(emptyList())
var senderId: String=""

    var messageState = mutableStateOf(MessageUiState())
    private set

    private val message
        get() = messageState.value.message

    private val timestamp
        get() = messageState.value.timestamp

    fun onMessageChange(newValue:String){
        messageState.value = messageState.value.copy(message = newValue)
    }

    fun onTimeChange(newValue: Long){
        messageState.value = messageState.value.copy(timestamp = newValue)
    }

    fun initialize(chatId: String){
        viewModelScope.launch {
            repo.getUser(chatId.idFromParameter()).collect { user1 ->
                // Update the state flow with the user data
                senderId =chatId
               user.value = user1
                Log.d(TAG,"$user1")
            }
            repo.getChats(senderId).collect{
                messages.value = emptyList()
                messages.value = it
                Log.d(TAG,"list of messages during initalization ${messages.value}")
            }
        }
    }

    fun onMessageSent(){
viewModelScope.launch {
    repo.createChats(message,senderId).collect{
        if(it){
            Log.d(TAG,"message sent successfully")
        }
        else{
            Log.d(TAG,"message failed")
        }
        repo.loadChats(senderId).collect{ messagesList->
            messages.value = emptyList()
            messages.value = messagesList
            Log.d(TAG,"list of messages after creation ${messages.value}")
        }

    }

}

    }
}