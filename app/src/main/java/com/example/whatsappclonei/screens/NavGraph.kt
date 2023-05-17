package com.example.whatsappclonei.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.whatsappclonei.ui.onboarding.signIn.SignInFullScreen
import com.example.whatsappclonei.ui.onboarding.signUp.SignUpScreenViewModel
import com.example.whatsappclonei.ui.onboarding.signUp.SignUpFullScreen
import com.example.whatsappclonei.ui.profile.ProfileScreen
import com.example.whatsappclonei.ui.profile.VerifyEmailScreen

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    startDestination: String = Screen.SignInScreen.route,
    signUpScreenViewModel: SignUpScreenViewModel = hiltViewModel()
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

        composable(
            route = Screen.VerifyEmailScreen.route
        ){
            VerifyEmailScreen(
                navigateToProfileScreen = {
                    navController.navigate(Screen.ProfileScreen.route){
                        popUpTo(navController.graph.id){
                            inclusive =true
                        }
                    }
                }
            )
        }

        composable(
            route = Screen.ProfileScreen.route
        ){
            ProfileScreen()
        }
    }
}