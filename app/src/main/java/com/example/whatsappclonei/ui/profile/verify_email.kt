package com.example.whatsappclonei.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.whatsappclonei.Constants.EMAIL_NOT_VERIFIED_MESSAGE
import com.example.whatsappclonei.constants.VERIFY_EMAIL_MESSAGE
import com.example.whatsappclonei.ui.onboarding.signUp.components.ReloadUser
import com.example.whatsappclonei.ui.onboarding.signUp.components.RevokeAccess
import com.example.whatsappclonei.ui.onboarding.signUp.components.TopBar
import com.example.whatsappclonei.ui.onboarding.signUp.components.VerifyEmailContent
import com.example.whatsappclonei.utils.Utils.Companion.showMessage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyEmailScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToProfileScreen: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current



    Scaffold(
        topBar = {
            TopBar(
                title = VERIFY_EMAIL_MESSAGE,
                signOut = {
                    viewModel.signOut()
                },
                revokeAccess = {
                    viewModel.revokeAccess()
                }
            )
        },
        content = { padding ->
            VerifyEmailContent(
                padding = padding,
                reloadUser = {
                    viewModel.reloadUser()
                }
            )

        },
    )
    ReloadUser(
        navigateToProfileScreen = {
            if (viewModel.isEmailVerified) {
                navigateToProfileScreen()
            } else {
                showMessage(context, EMAIL_NOT_VERIFIED_MESSAGE)
            }
        }
    )
    RevokeAccess(
        scaffoldState = snackbarHostState,
        coroutineScope = coroutineScope,
        signOut = {
            viewModel.signOut()
        }
    )


}



