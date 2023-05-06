package com.example.whatsappclonei.ui.profile

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
import com.example.whatsappclonei.Constants.ACCESS_REVOKED_MESSAGE
import com.example.whatsappclonei.Constants.ALREADY_VERIFIED
import com.example.whatsappclonei.Constants.EMAIL_NOT_VERIFIED_MESSAGE
import com.example.whatsappclonei.Constants.REVOKE_ACCESS
import com.example.whatsappclonei.Constants.REVOKE_ACCESS_MESSAGE
import com.example.whatsappclonei.Constants.SENSITIVE_OPERATION_MESSAGE
import com.example.whatsappclonei.Constants.SIGN_OUT
import com.example.whatsappclonei.Constants.SPAM_EMAIL
import com.example.whatsappclonei.constants.VERIFY_EMAIL_MESSAGE
import com.example.whatsappclonei.data.Response
import com.example.whatsappclonei.ui.onboarding.signUp.ProgressBar
import com.example.whatsappclonei.ui.onboarding.signUp.SignInScreenViewModel
import com.example.whatsappclonei.utils.Utils.Companion.showMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyEmailScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToProfileScreen: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

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
                            Text(text = SIGN_OUT)
                        },
                        onClick = {
                            signOut()
                            openMenu = !openMenu
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = REVOKE_ACCESS)
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
                text = ALREADY_VERIFIED,
                fontSize = 16.sp,
                textDecoration = TextDecoration.Underline
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = SPAM_EMAIL,
                fontSize = 15.sp
            )
        }
    }

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
            message = REVOKE_ACCESS_MESSAGE,
            actionLabel = SIGN_OUT
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
                    showMessage(context, ACCESS_REVOKED_MESSAGE)
                }
            }
        }
        is Response.Failure -> revokeAccessResponse.apply {
            LaunchedEffect(e) {
                print(e)
                if (e.message == SENSITIVE_OPERATION_MESSAGE) {
                    showRevokeAccessMessage()
                }
            }
        }
    }

}
