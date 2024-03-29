package com.example.whatsappclonei.ui.onboarding.signIn

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.whatsappclonei.WhatsappCloneiAppState
import com.example.whatsappclonei.ui.onboarding.signIn.components.SignInContent

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SignInFullScreen(
    viewModel: SignInScreenViewModel = hiltViewModel(),
    navigateToSignUpScreen: () -> Unit,
    openAndPopUp: (String,String) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { innerPadding ->

            SignInContent(
                padding = innerPadding,
                navigateToForgotPasswordScreen = { /*TODO*/ },
                navigateToSignUpScreen = navigateToSignUpScreen,
                onSignIn = openAndPopUp,
                viewModel = viewModel
            )

        }
    )
   /* Signin(
        showErrorMessage = { errorMessage ->
            showMessage(context, errorMessage)
        },
    onSignIn = { openAndPopUp() },
    showSnackbar ={

        // show snackbar as a suspend function
        scope.launch {
            snackbarHostState.showSnackbar(
                "Enter Credential"
            )
        }
    } )*/


    LaunchedEffect(viewModel.message) {
        if (viewModel.message.value.isNotEmpty()) {
            snackbarHostState.showSnackbar(viewModel.message.value)
            viewModel.postMessage("")
        }
    }
}





