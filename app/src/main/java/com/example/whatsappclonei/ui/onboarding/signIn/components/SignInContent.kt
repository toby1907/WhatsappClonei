package com.example.whatsappclonei.ui.onboarding.signIn.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclonei.Constants.FORGOT_PASSWORD
import com.example.whatsappclonei.Constants.NO_ACCOUNT
import com.example.whatsappclonei.Constants.NO_VALUE
import com.example.whatsappclonei.Constants.SIGN_IN
import com.example.whatsappclonei.Constants.VERTICAL_DIVIDER
import com.example.whatsappclonei.R
import com.example.whatsappclonei.components.EmailField
import com.example.whatsappclonei.components.PasswordField
import com.example.whatsappclonei.components.SmallSpacer

@Composable
@ExperimentalComposeUiApi
fun SignInContent(
    padding: PaddingValues,
    signIn: (email: String, password: String) -> Unit,
    navigateToForgotPasswordScreen: () -> Unit,
    navigateToSignUpScreen: () -> Unit
) {
    var email by rememberSaveable(
        stateSaver = TextFieldValue.Saver
    ) { mutableStateOf(TextFieldValue(NO_VALUE)) }
    var password by rememberSaveable(
        stateSaver = TextFieldValue.Saver
    ) { mutableStateOf(TextFieldValue(NO_VALUE)) }
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
            EmailField(
                email = email,
                onEmailValueChange = { newValue ->
                    email = newValue
                }
            )
            SmallSpacer()
            PasswordField(
                password = password,
                onPasswordValueChange = { newValue ->
                    password = newValue
                }
            )
            SmallSpacer()
            Button(modifier = Modifier
                .width(236.dp)
                .height(45.dp),
                onClick = {
                    keyboard?.hide()
                    signIn(email.text, password.text)
                }
            ) {
                Text(
                    text = SIGN_IN,
                    fontSize = 15.sp
                )
            }
            Row {
                Text(
                    modifier = Modifier.clickable {
                        navigateToForgotPasswordScreen()
                    },
                    text = FORGOT_PASSWORD,
                    fontSize = 15.sp
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                    text = VERTICAL_DIVIDER,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.clickable {
                        navigateToSignUpScreen.invoke()
                    },
                    text = NO_ACCOUNT,
                    fontSize = 15.sp
                )
            }
        }
    }
}
