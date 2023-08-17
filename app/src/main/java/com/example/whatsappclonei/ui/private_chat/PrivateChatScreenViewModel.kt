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
import com.example.whatsappclonei.data.model.Response
import com.example.whatsappclonei.data.model.User
import com.example.whatsappclonei.screens.CHAT_DEFAULT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrivateChatScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val context: Context,
    private val repo: AuthRepository
): ViewModel() {

    val user = MutableStateFlow<User?>(null) // Create a state flow to hold the user data

    var

    fun initialize(chatId: String){
        viewModelScope.launch {
            repo.getUser(chatId.idFromParameter()).collect { user1 ->
                // Update the state flow with the user data
               user.value = user1
                Log.d(TAG,"$user1")
            }

        }
    }
}