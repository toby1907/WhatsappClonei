package com.example.whatsappclonei.ui.profile

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.whatsappclonei.Constants.PROFILE_SCREEN
import com.example.whatsappclonei.screens.SPLASH_SCREEN
import com.example.whatsappclonei.screens.VERIFY_PHONE_SCREEN
import com.example.whatsappclonei.ui.onboarding.signUp.components.ProfileContent
import com.example.whatsappclonei.ui.onboarding.signUp.components.RevokeAccess
import com.example.whatsappclonei.ui.onboarding.signUp.components.TopBar
import com.example.whatsappclonei.ui.theme.WhatsappCloneiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    openAndPopUp: (String,String) -> Unit,

) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopBar(
                title = PROFILE_SCREEN,
                signOut = {
                    viewModel.signOut()
                    openAndPopUp(VERIFY_PHONE_SCREEN, PROFILE_SCREEN)

                },
                revokeAccess = {
                    viewModel.revokeAccess()
                }
            )
        },
        content = { padding ->
            ProfileContent(
                padding = padding
            )
        },
    )

    RevokeAccess(
        scaffoldState = snackbarHostState,
        coroutineScope = coroutineScope,
        signOut = {
            viewModel.signOut()
        }
    )
}

