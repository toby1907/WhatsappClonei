package com.example.whatsappclonei.ui.onboarding.signUp

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsappclonei.data.AuthRepository
import com.example.whatsappclonei.data.model.Response
import com.example.whatsappclonei.data.SendEmailVerificationResponse
import com.example.whatsappclonei.data.SignUpResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val context: Context,
    private val repo: AuthRepository
): ViewModel(){
    // Initialize Firebase Auth
var signUpResponse by mutableStateOf<SignUpResponse>(Response.Success(false))
private set

    var sendEmailVerificationResponse by mutableStateOf<SendEmailVerificationResponse>(Response.Success(false))
    private set

    fun signUpWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        signUpResponse = Response.Loading
        signUpResponse = repo.firebaseSignUpWithEmailAndPassword(email,password)
    }
    fun sendEmailVerification() = viewModelScope.launch {
        sendEmailVerificationResponse = Response.Loading
        sendEmailVerificationResponse = repo.sendEmailVerification()
    }
/*fun onEvent(event:SignInEvent){
    when(event){
        is SignInEvent.SignIn -> {
TODO()
        }
        is SignInEvent.SignInCredentials -> {
TODO()
        }
        is SignInEvent.SignOut -> {
TODO()
        }
    }
}
    data class SignInState(
        val EmailText: String = "",
        val PasswordText: String = ""
    )
    private  val _emailText = mutableStateOf(
      SignInState()
    )
    private val _passwordText = mutableStateOf(
        SignInState()
    )
    val emailText: State<SignInState> = _emailText
    val PasswordText: State<SignInState> = _passwordText

    sealed class SignInEvent{
        data class SignInCredentials(val email: String, val password: String): SignInEvent()
        object SignIn: SignInEvent()
        object SignOut: SignInEvent()
    }*/



}
