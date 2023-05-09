package com.example.whatsappclonei.ui.onboarding.signIn

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.whatsappclonei.R
import com.example.whatsappclonei.ui.onboarding.signUp.SignUpScreenViewModel
import com.example.whatsappclonei.ui.theme.WhatsappCloneiTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInFullScreen(
    navigateToSignUpScreen: () -> kotlin.Unit){
    val snackbarHostState = remember{ SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)},
        content = {innerPadding ->
            SignInScreen(
                modifier = Modifier
                    .padding(innerPadding)
                    .wrapContentSize(),
                navigateToSignUpScreen =navigateToSignUpScreen
                ,){

                // show snackbar as a suspend function
                scope.launch {
                    snackbarHostState.showSnackbar(
                        "Enter Credential"
                    )
                }
            }

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    modifier: Modifier =Modifier,
    viewModel: SignUpScreenViewModel = hiltViewModel(),
    navigateToSignUpScreen: () -> Unit,
    snackbar: () ->Unit  ) {
    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    Box(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize()
    ){
        Image(
            contentScale = ContentScale.FillBounds,
            painter = painterResource(id = R.drawable.manuel_will_gd3t5dtbwkw_unsplash),
            contentDescription =""
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        )  {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {

                Image(
                    modifier = Modifier.size(60.dp),
                    painter = painterResource(id = R.drawable.ic_whatsapp),
                    contentDescription = stringResource(id = R.string.whatsapp_content_description),

                    )
                Spacer(modifier = Modifier.size(8.dp))
                TextField(
                    value = emailText,
                    onValueChange = { emailText = it },
                    placeholder = { Text(text = "Email") },
                    colors = TextFieldDefaults
                        .outlinedTextFieldColors(textColor = Color.LightGray)


                )
                TextField(
                    value = passwordText,
                    onValueChange = { passwordText = it },
                    label = { Text("Enter password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.outlinedTextFieldColors()
                )
                Spacer(Modifier.size(4.dp))
                Row(
                    modifier = Modifier
                        ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(Modifier.size(width = 220.dp, height = 0.dp))
                   TextButton(onClick = { navigateToSignUpScreen.invoke() }) {
                       Text(
                           fontSize = 8.sp,
                           text = "Click to SignUp",
                           color =Color.LightGray
                       )
                   }
                }
                Button(
                    onClick = {
                              snackbar.invoke()
                    },
                    shape = RectangleShape
                ) {
                    Text(text = "Sign In")
                }
            }

        }

    }

}

@Preview(showBackground = true)
@Composable
fun SiginFieldPreview() {
    WhatsappCloneiTheme {
        SignInScreen(modifier = Modifier,
            viewModel = hiltViewModel()
        ,{},{})
    }
}




