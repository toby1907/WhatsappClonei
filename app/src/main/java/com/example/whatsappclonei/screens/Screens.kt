package com.example.whatsappclonei.screens

import com.example.whatsappclonei.Constants.SIGN_IN_SCREEN
import com.example.whatsappclonei.Constants.SIGN_UP_SCREEN

sealed class Screen(val route: String) {
    object SignInScreen: Screen(SIGN_IN_SCREEN)
    object SignUpScreen: Screen(SIGN_UP_SCREEN)
  /*  object ForgotPasswordScreen: Screen(FORGOT_PASSWORD_SCREEN)
    object VerifyEmailScreen: Screen(VERIFY_EMAIL_SCREEN)
    object ProfileScreen: Screen(PROFILE_SCREEN)*/
}