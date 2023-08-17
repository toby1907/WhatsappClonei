package com.example.whatsappclonei.ui.onboarding.signUp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclonei.Constants
import com.example.whatsappclonei.R
import com.example.whatsappclonei.components.EmailField
import com.example.whatsappclonei.components.PasswordField
import com.example.whatsappclonei.components.SmallSpacer
import com.example.whatsappclonei.components.UsernameField
import com.example.whatsappclonei.ui.onboarding.signUp.SignUpScreenViewModel

@Composable
@ExperimentalComposeUiApi
fun SignUpContent(
    padding: PaddingValues,
    navigateToSignInScreen: () -> Unit,
    onSignUp: (String,String) -> Unit,
    snackBar: () -> Unit,
    viewModel: SignUpScreenViewModel
) {
    val uiState by viewModel.uiState
    val context = LocalContext.current
    var progressDialog by remember { mutableStateOf(false) }
    /*var username by rememberSaveable(
            stateSaver = TextFieldValue.Saver
            ){ mutableStateOf(TextFieldValue(Constants.NO_VALUE))}
    var email by rememberSaveable(
        stateSaver = TextFieldValue.Saver
    ) { mutableStateOf(TextFieldValue(Constants.NO_VALUE)) }
    var password by rememberSaveable(
        stateSaver = TextFieldValue.Saver
    ) { mutableStateOf(TextFieldValue(Constants.NO_VALUE)) }*/
    val keyboard = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            contentScale = ContentScale.FillBounds,
            painter = painterResource(id = R.drawable.background),
            contentDescription = "",
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            /*verticalArrangement = Arrangement.Center*/
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //CircularProgress
            if (progressDialog) {
                CircularProgressIndicator()
            }

            Spacer(modifier = Modifier.height(80.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)

            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Welcome to WhatsAppClonei",
// Geometria 24 | Medium
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                )
            )

            Spacer(modifier = Modifier.height(48.dp))
            UsernameField(username = uiState.username, onUsernameValueChange = { newValue ->
                viewModel.onUsernameChange(newValue)
            })
            SmallSpacer()
            EmailField(
                email = uiState.email,
                onEmailValueChange = { newValue ->
                    viewModel.onEmailChange(newValue)
                }
            )
            SmallSpacer()
            PasswordField(
                password = uiState.password,
                onPasswordValueChange = { newValue ->
                    viewModel.onPasswordChange(newValue)
                }
            )
            SmallSpacer()
            SmallSpacer()
            Button(modifier = Modifier
                .width(236.dp)
                .height(45.dp),
                onClick = {

                        keyboard?.hide()
                     //   viewModel.signUpWithEmailAndPassword(uiState.email, uiState.password)
                        viewModel.onSignUpClick(onSignUp)

                }
            ) {
                Text(
                    text = Constants.SIGN_UP,
                    fontSize = 15.sp
                )
            }
            Row {
                Text(
                    modifier = Modifier.clickable {
                        navigateToSignInScreen.invoke()
                    },
                    text = Constants.ALREADY_USER,
                    fontSize = 15.sp
                )
            }
        }
    }
}

