package com.example.whatsappclonei.screens

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.whatsappclonei.WhatsappCloneiAppState
import com.example.whatsappclonei.constants.VERIFY_EMAIL_SCREEN
import com.example.whatsappclonei.ui.chats.HomeScreen
import com.example.whatsappclonei.ui.chats.MessageScreen
import com.example.whatsappclonei.ui.onboarding.phone_no.AddPhoneNoScreen
import com.example.whatsappclonei.ui.onboarding.signIn.SignInFullScreen
import com.example.whatsappclonei.ui.onboarding.signUp.SignUpFullScreen
import com.example.whatsappclonei.ui.private_chat.ChatScreen
import com.example.whatsappclonei.ui.profile.ProfileScreen
import com.example.whatsappclonei.ui.profile.VerifyEmailScreen
import com.example.whatsappclonei.ui.splash.SplashScreen
import com.example.whatsappclonei.ui.status.UserStatusesScreen
import com.example.whatsappclonei.ui.status.add_status.AddStatusScreen
import com.example.whatsappclonei.ui.status.add_status.CreateStatusScreen
import com.example.whatsappclonei.ui.status.preview.ImagePreviewScreen
import com.example.whatsappclonei.ui.status.screens.StatusListScreen


@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),

    ) = remember(navController) {
    WhatsappCloneiAppState(
        navController
    )
}


@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    startDestination: String = SPLASH_SCREEN,
    saveImage: (Bitmap) -> Unit,
) {
    val appState = rememberAppState()
    NavHost(
        modifier = modifier,
        navController = appState.navController,
        startDestination = startDestination,

    ) {

        WhatsappCloneNavGraph(appState = appState,saveImage = saveImage)
    }
}

fun NavGraphBuilder.WhatsappCloneNavGraph(appState: WhatsappCloneiAppState,
                                          saveImage: (Bitmap) -> Unit,
                                          ) {
    composable(LOGIN_SCREEN) {
        SignInFullScreen(
            navigateToSignUpScreen = { appState.navigate(SIGN_UP_SCREEN) },
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })

    }
    composable(SIGN_UP_SCREEN) {
        SignUpFullScreen(openAndPopUp = { route, popUp ->
            appState.navigateAndPopUp(route, popUp)
        },
            navigateBack = { appState.navigate(LOGIN_SCREEN) }
        )
    }

    composable(
        route = VERIFY_EMAIL_SCREEN
    ) {
        VerifyEmailScreen(
            navigateToProfileScreen = {
                /*   appState.navigate(Screen.ProfileScreen.route){
                       popUpTo(navController.graph.id){
                           inclusive =true
                       }
                   }*/
            }
        )
    }

    composable(
        route = PROFILE_SCREEN
    ) {
        ProfileScreen(
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
    composable(
        route = MESSAGE_SCREEN
    ) {
        HomeScreen(
            appState = appState,
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }
        )

    }

    composable(
        route = "$CHAT_SCREEN$CHAT_ID_ARG",
        arguments = listOf(navArgument(CHAT_ID) { defaultValue = CHAT_DEFAULT_ID })
    ) {
        ChatScreen(
            popUpScreen = { appState.popUp() },
            userId = it.arguments?.getString(CHAT_ID) ?: CHAT_DEFAULT_ID
        )
    }

    composable(
        route = VERIFY_PHONE_SCREEN,
    ){
        AddPhoneNoScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
    composable(
        route = SPLASH_SCREEN,
    ){
        SplashScreen(
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) },
        )
    }
    composable(
        route = STATUS_SCREEN,
    ){
       /* StatusScreen(
            onStatusClick = {},
            onMyStatusClick = {},
            onNewStatusClick = {},
            isTesting = true
        )*/
        StatusListScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
    composable(
        route = CREATE_STATUS_SCREEN,
    ){
   /*    CreateStatusScreen(
           onCancelClick = {},
           onTextClick = {},
           onPaletteClick ={},
           onVideoClick = {},
           onMicClick = {},
           navController = appState.navController,
           saveImage = saveImage
       )*/

        AddStatusScreen(
            navController = appState.navController,
        )
    }

    composable(route = STATUSESPREVIEW){

        UserStatusesScreen()
    }
    composable(route = "preview"+"?uri={uri}",
        arguments = listOf(
            navArgument("uri"){
                type = NavType.StringType
                defaultValue = ""
            }
        )
        ){
        ImagePreviewScreen(
            navController = appState.navController,
            imageUri = Uri.parse(it.arguments?.getString("uri"))
        )
    }


}