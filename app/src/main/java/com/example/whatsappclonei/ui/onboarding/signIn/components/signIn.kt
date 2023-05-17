package com.example.whatsappclonei.ui.onboarding.signIn.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.whatsappclonei.data.Response
import com.example.whatsappclonei.ui.onboarding.signIn.SignInScreenViewModel
import com.example.whatsappclonei.ui.onboarding.signUp.ProgressBar
import com.example.whatsappclonei.utils.Utils.Companion.Print

@Composable
fun Signin(
    viewModel: SignInScreenViewModel = hiltViewModel(),
    showErrorMessage: (errorMessage: String?) -> Unit,
    showSnackbar: () -> Unit
) {
    when (val signInResponse = viewModel.signInResponse) {
        is Response.Loading -> ProgressBar()
        is Response.Success -> signInResponse.apply {
showSnackbar.invoke()
        }
        is Response.Failure -> signInResponse.apply {
            LaunchedEffect(e) {
                print(e)
                showErrorMessage(e.message)
            }
        }
    }
}