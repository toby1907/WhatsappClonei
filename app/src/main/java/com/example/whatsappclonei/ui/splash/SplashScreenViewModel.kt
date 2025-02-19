package com.example.whatsappclonei.ui.splash

import androidx.activity.result.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsappclonei.data.AuthRepository
import com.example.whatsappclonei.ui.onboarding.validation.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(

    private val repository: AuthRepository
) : ViewModel() {


    var isAuthenticated by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {

            isAuthenticated = repository.isUserAuthenticated()
        }

    }
}