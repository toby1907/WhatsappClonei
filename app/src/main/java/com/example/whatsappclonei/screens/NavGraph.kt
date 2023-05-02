package com.example.whatsappclonei.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.whatsappclonei.ui.onboarding.*
import com.example.whatsappclonei.ui.onboarding.signUp.SignUpFullScreen

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    startDestination: String = Screen.SignUpScreen.route,
    signInScreenViewModel: SignInScreenViewModel= hiltViewModel()
) {
    val navController: NavHostController= rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
       composable(Screen.SignInScreen.route){
           SignInFullScreen {
               navController.navigate(Screen.SignUpScreen.route)
           }

       }
        composable(Screen.SignUpScreen.route){
           SignUpFullScreen {
               navController.navigate(Screen.SignInScreen.route)
           }
        }
    }
}