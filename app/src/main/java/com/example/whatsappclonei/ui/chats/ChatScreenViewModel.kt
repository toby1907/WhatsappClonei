package com.example.whatsappclonei.ui.chats

import android.content.Context
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsappclonei.data.AuthRepository
import com.example.whatsappclonei.data.model.Response
import com.example.whatsappclonei.data.model.User
import com.example.whatsappclonei.screens.CHAT_ID
import com.example.whatsappclonei.screens.CHAT_SCREEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val context: Context,
    private val repo: AuthRepository
): ViewModel(){

    init {
        getAccounts()

    }
    // Initialize Firebase Auth
    data class SignUpUiState(
        val username: String = "",
        val email: String = "",
        val password: String = ""
    )
    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    fun postMessage(message: String) {
        // Do something with the message
        _message.value = message
    }
    private val _accounts = MutableStateFlow<List<User>>(emptyList())
    val accounts: StateFlow<List<User>> = _accounts

    private fun getAccounts() {
        viewModelScope.launch {

                when(val response = repo.getAccounts()){
                is Response.Loading -> {
                    // handle loading state
                }
                is Response.Success -> {
                    _accounts.value = response.data
                    // handle success state with data
                    Log.d("messagevm", "Accounts: $accounts")
                }
                is Response.Failure -> {
                    val exception = response.e
                    // handle failure state with exception
                //    postMessage(exception.message.toString())
                }

                    Response.None -> {

                    }
                }

        }
    }

    fun onUserClick(openScreen: (String) -> Unit, userId: String) {
openScreen("$CHAT_SCREEN?$CHAT_ID={${userId}}")
    }

}
