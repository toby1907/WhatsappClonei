package com.example.whatsappclonei.ui.onboarding.signIn

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsappclonei.data.AuthRepository
import com.example.whatsappclonei.data.Response
import com.example.whatsappclonei.data.SignInResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val context: Context,
    private val repo: AuthRepository
): ViewModel() {

    var signInResponse by mutableStateOf<SignInResponse>(Response.Success(false))
    private set

    fun signInWithEmailAndPassword(email: String,password: String) = viewModelScope.launch {
        signInResponse = Response.Loading
        signInResponse = repo.firebaseSignInWithEmailAndPassword(email, password)
    }

    fun onEvent(event: SignUpEvent){
        when(event){

            is SignUpEvent.SignUp ->{

            }
            is SignUpEvent.SignUpCredentials ->{

            }
        }
    }
    data class SignUpState(
        val EmailText: String = "",
        val PasswordText: String = ""
    )
    private  val _emailText = mutableStateOf(
        SignUpState()
    )
    val emailText: State<SignUpState> = _emailText

    sealed class SignUpEvent{

        data class SignUpCredentials(val email: String, val password: String): SignUpEvent()
        object SignUp: SignUpEvent()

    }
    /*
    var signUpResponse by mutableStateOf<SignUpResponse>(Success(false))
        private set
    var sendEmailVerificationResponse by mutableStateOf<SendEmailVerificationResponse>(Success(false))
        private set

    fun signUpWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        signUpResponse = Loading
        signUpResponse = repo.firebaseSignUpWithEmailAndPassword(email, password)
    }

    fun sendEmailVerification() = viewModelScope.launch {
        sendEmailVerificationResponse = Loading
        sendEmailVerificationResponse = repo.sendEmailVerification()
    }
    */
}