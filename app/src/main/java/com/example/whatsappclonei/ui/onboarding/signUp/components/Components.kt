package com.example.whatsappclonei.ui.onboarding.signUp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.whatsappclonei.Constants
import com.example.whatsappclonei.Constants.WELCOME_MESSAGE
import com.example.whatsappclonei.data.model.Response
import com.example.whatsappclonei.ui.onboarding.signUp.ProgressBar
import com.example.whatsappclonei.ui.profile.ProfileViewModel
import com.example.whatsappclonei.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ReloadUser(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToProfileScreen: () -> Unit
) {
    when (val reloadUserResponse = viewModel.reloadUserResponse) {

        is Response.Loading -> ProgressBar()
        is Response.Success -> {
            val isUserReloaded = reloadUserResponse.data
            LaunchedEffect(isUserReloaded) {
                navigateToProfileScreen()
            }
        }
        is Response.Failure -> reloadUserResponse.apply {
            LaunchedEffect(e) {
                print(e)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    signOut: () -> Unit,
    revokeAccess: () -> Unit
) {
    var openMenu by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {
                            openMenu = !openMenu
                        }) {

                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = null,
                        )
                    }
                }
            }
        },
        actions = {
            DropdownMenu(
                expanded = openMenu,
                onDismissRequest = {
                    openMenu = !openMenu
                }) {
                DropdownMenuItem(
                    text = {
                        Text(text = Constants.SIGN_OUT)
                    },
                    onClick = {
                        signOut()
                        openMenu = !openMenu
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(text = Constants.REVOKE_ACCESS)
                    },
                    onClick = {
                        revokeAccess()
                        openMenu = !openMenu
                    })
            }
        },
    )

}

@Composable
fun VerifyEmailContent(
    padding: PaddingValues,
    reloadUser: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(start = 32.dp, end = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.clickable {
                reloadUser()
            },
            text = Constants.ALREADY_VERIFIED,
            fontSize = 16.sp,
            textDecoration = TextDecoration.Underline
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = Constants.SPAM_EMAIL,
            fontSize = 15.sp
        )
    }
}

@Composable
fun RevokeAccess(
    viewModel: ProfileViewModel = hiltViewModel(),
    scaffoldState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    signOut: () -> Unit,
) {
    val context = LocalContext.current

    fun showRevokeAccessMessage() = coroutineScope.launch {
        val result = scaffoldState.showSnackbar(
            message = Constants.REVOKE_ACCESS_MESSAGE,
            actionLabel = Constants.SIGN_OUT
        )
        if (result == SnackbarResult.ActionPerformed) {
            signOut()
        }
    }

    when (val revokeAccessResponse = viewModel.revokeAccessResponse) {
        is Response.Loading -> ProgressBar()
        is Response.Success -> {
            val isAccessRevoked = revokeAccessResponse.data
            LaunchedEffect(isAccessRevoked) {
                if (isAccessRevoked) {
                    Utils.showMessage(context, Constants.ACCESS_REVOKED_MESSAGE)
                }
            }
        }
        is Response.Failure -> revokeAccessResponse.apply {
            LaunchedEffect(e) {
                print(e)
                if (e.message == Constants.SENSITIVE_OPERATION_MESSAGE) {
                    showRevokeAccessMessage()
                }
            }
        }
    }}
@Composable
fun ProfileContent(
    padding: PaddingValues
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(padding).padding(top = 48.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = WELCOME_MESSAGE,
            fontSize = 24.sp
        )
    }
}