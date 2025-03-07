package com.example.whatsappclonei.ui.chats

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclonei.R
import com.example.whatsappclonei.WhatsappCloneiAppState
import com.example.whatsappclonei.screens.PROFILE_SCREEN
import com.example.whatsappclonei.screens.STATUS_SCREEN
import com.example.whatsappclonei.ui.status.components.AppBar
import com.example.whatsappclonei.ui.status.screens.CallsListScreen
import com.example.whatsappclonei.ui.status.screens.StatusListScreen
import com.example.whatsappclonei.ui.theme.ColorPrimary
import com.example.whatsappclonei.ui.theme.ColorPrimaryDary
import com.example.whatsappclonei.ui.theme.PrimaryGray_A101
import com.example.whatsappclonei.ui.theme.WhatsappCloneiTheme
import com.example.whatsappclonei.ui.theme.White
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(appState: WhatsappCloneiAppState) {
    val viewPagerState = rememberPagerState( pageCount = { 3 } )
    val scope = rememberCoroutineScope()
    var actionButtonDrawable = remember {
        mutableStateOf(  R.drawable.ic_chat_filled)
    }
    var selectedTab by remember { mutableIntStateOf(0) }

    val navigationItems = listOf(
        NavigationItem(
            title = stringResource(id = R.string.status),
            icon = R.drawable.status_icon,
            screen = 1
        ),
        NavigationItem(
            title = stringResource(id = R.string.calls),
            icon = R.drawable.call_icon,
            screen = 2
        ),
        NavigationItem(
            title = stringResource(id = R.string.messages),
            icon = R.drawable.chats_icon,
            screen = 0
        ),
        NavigationItem(
            title = stringResource(id = R.string.settings),
            icon = R.drawable.settings_icon,
            screen = 3
        )
    )

    LaunchedEffect(key1 = viewPagerState.currentPage) {
        scope.launch {
            when (viewPagerState.currentPage) {
                0 -> actionButtonDrawable.value = R.drawable.ic_chat_filled
                1 -> actionButtonDrawable.value = R.drawable.ic_camera
                2 -> actionButtonDrawable.value = R.drawable.ic_add_call
            }
         //   viewPagerState.animateScrollToPage(selectedTab)
        }
    }
LaunchedEffect(key1 = viewPagerState.currentPage) {
    selectedTab = viewPagerState.currentPage
}

    WhatsappCloneiTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
           Column {
                AppBar(
                    title =
                    {
                        when (viewPagerState.currentPage) {
                            0 -> Text(text = stringResource(id = R.string.messages),
                                style = TextStyle(
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (isSystemInDarkTheme()) PrimaryGray_A101 else White
                                )
                            )
                            1 -> Text(text = stringResource(id = R.string.status),
                                style = TextStyle(
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (isSystemInDarkTheme()) PrimaryGray_A101 else White
                                ))
                            2 -> Text(text = stringResource(id = R.string.calls),
                                style = TextStyle(
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (isSystemInDarkTheme()) PrimaryGray_A101 else White
                                ))

                        }
                    }

                )

                HorizontalPager(
                    beyondBoundsPageCount = 3,
                    state = viewPagerState,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize(),
                ) { page ->
                    when (page) {
                        0 -> MessageScreen(
                            navigateProfile = { appState.navigate(PROFILE_SCREEN) },
                            openChat = { route -> appState.navigate(route) },
                            navigateStatus = {
                                appState.navigate(STATUS_SCREEN)
                            }
                        )
                        1 ->   StatusListScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })

                        2 -> CallsListScreen()
                    }
                }
            }

          Column (
              modifier = Modifier
                  .fillMaxWidth()
                  .align(Alignment.BottomCenter)
          ) {
           Box(modifier = Modifier.fillMaxWidth())   {
                  FloatingActionButton(
                      onClick = {},
                      modifier = Modifier
                          .padding(16.dp)
                          .size(60.dp)
                          .align(Alignment.CenterEnd),
                      shape = CircleShape,
                  ) {
                      Icon(
                          painter = painterResource(actionButtonDrawable.value),
                          contentDescription = "",
                          tint = Color.White
                      )
                  }
              }
              NavigationBar(
                  containerColor = MaterialTheme.colorScheme.primary,
              ) {
                  navigationItems.forEach { item ->
                      NavigationBarItem(
                          icon = {
                              Icon(
                                  painter = painterResource(id = item.icon),
                                  contentDescription = item.title,
                              )
                          },
                          selected = selectedTab == item.screen,
                          onClick = {
                              selectedTab = item.screen
                           if (item.screen!=3)   {

                                  scope.launch {
                                      viewPagerState.animateScrollToPage(selectedTab)
                                  }
                              }
                          },
                          colors = NavigationBarItemDefaults.colors(
                              selectedIconColor = Color.White,
                              unselectedIconColor = Color.Gray,
                              indicatorColor = ColorPrimary
                          )
                      )
                  }
              }
           }

        }
    }
}
data class NavigationItem(
    val title: String,
    val icon: Int,
    val screen: Int
)