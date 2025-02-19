package com.example.whatsappclonei.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.whatsappclonei.R
import com.example.whatsappclonei.screens.MESSAGE_SCREEN
import com.example.whatsappclonei.screens.SPLASH_SCREEN
import com.example.whatsappclonei.screens.VERIFY_PHONE_SCREEN
import kotlinx.coroutines.delay

private const val SplashWaitTime: Long = 2000
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,

    viewModel: SplashScreenViewModel = hiltViewModel(),
    openAndPopUp: (String,String) -> Unit
){
val isAuthenticated = viewModel.isAuthenticated

    val currentOnTimeout by rememberUpdatedState(newValue = openAndPopUp)
    LaunchedEffect(openAndPopUp){
        delay(SplashWaitTime)//Simulates loading things

        if (isAuthenticated){
            currentOnTimeout(MESSAGE_SCREEN, SPLASH_SCREEN)
        }
        else{
            currentOnTimeout(VERIFY_PHONE_SCREEN, SPLASH_SCREEN)
        }


    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center

    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Image(
                painter = painterResource(id =  R.drawable.splash_logo),
                contentDescription = null
            )
            Spacer(
                Modifier.height(16.dp)
            )
            Text(
                text = "WhatsappClonei"
            )
        }
    }
}
