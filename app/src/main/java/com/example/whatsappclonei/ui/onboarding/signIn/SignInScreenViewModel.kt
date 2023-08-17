package com.example.whatsappclonei.ui.onboarding.signIn

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsappclonei.Constants.TAG
import com.example.whatsappclonei.components.ext.isValidEmail
import com.example.whatsappclonei.data.AuthRepository
import com.example.whatsappclonei.data.SignInResponse
import com.example.whatsappclonei.data.model.Response
import com.example.whatsappclonei.screens.LOGIN_SCREEN
import com.example.whatsappclonei.screens.MESSAGE_SCREEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.whatsappclonei.R.string as AppText

@HiltViewModel
class SignInScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val context: Context,
    private val repo: AuthRepository
) : ViewModel() {


    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    fun postMessage(message: String) {
        // Do something with the message
        _message.value = message
    }

    var signInResponse by mutableStateOf<SignInResponse>(Response.Success(false))
        private set

    fun signInWithEmailAndPassword(email: String, password: String,openAndPopUp: (String, String) -> Unit) = viewModelScope.launch {
        signInResponse = Response.Loading
        signInResponse = repo.firebaseSignInWithEmailAndPassword(email, password,openAndPopUp)

    }



    var uiState = mutableStateOf(LoginUiState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            // SnackbarManager.showMessage(AppText.email_error)
            postMessage(AppText.email_error.toString())
            return
        }

        if (password.isBlank()) {
            //SnackbarManager.showMessage(AppText.empty_password_error)
            postMessage(AppText.empty_password_error.toString())
            return
        }

        signInWithEmailAndPassword(email, password,openAndPopUp)

/*when(signInResponse){
    is Response.Failure -> Log.d(TAG,"it failed")
    Response.Loading -> Log.d(TAG, "its still loading")
    is Response.Success ->   openAndPopUp(MESSAGE_SCREEN, LOGIN_SCREEN)
}*/


    }

    /*  fun onForgotPasswordClick() {
          if (!email.isValidEmail()) {
              SnackbarManager.showMessage(AppText.email_error)
              return
          }

          launchCatching {
              accountService.sendRecoveryEmail(email)
              SnackbarManager.showMessage(AppText.recovery_email_sent)
          }
      }*/


}