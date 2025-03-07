package com.example.whatsappclonei.ui.status.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.whatsappclonei.R
import com.example.whatsappclonei.ui.status.components.AppBar
import com.example.whatsappclonei.ui.status.components.TabBar
import com.example.whatsappclonei.ui.theme.PrimaryGreen_A102
import com.example.whatsappclonei.ui.theme.WhatsappCloneiTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WhatsappHomeScreen() {
    val viewPagerState = rememberPagerState(initialPage = 0, pageCount = { 0 })
    val scope = rememberCoroutineScope()
    var actionButtonDrawable by remember {
        mutableStateOf(R.drawable.ic_chat_filled)
    }

    WhatsappCloneiTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column {
                AppBar(title = {
                    when (viewPagerState.currentPage) {
                        0 -> Text(text = stringResource(id = R.string.messages))
                        1 -> Text(text = stringResource(id = R.string.status))
                        2 -> Text(text = stringResource(id = R.string.calls))
                    }
                })
                TabBar(
                    initialIndex = 0,
                    pagerState = viewPagerState
                ) { selectedTab ->
                    scope.launch {
                        when (selectedTab) {
                            0 -> actionButtonDrawable = R.drawable.ic_chat_filled
                            1 -> actionButtonDrawable = R.drawable.ic_camera
                            2 -> actionButtonDrawable = R.drawable.ic_add_call
                        }
                        viewPagerState.animateScrollToPage(selectedTab)
                    }
                }
                HorizontalPager(
                   // pageCount = 3,
                    beyondBoundsPageCount = 3,
                    state = viewPagerState,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize(),
                ) { page ->
                    when (page) {
                        0 -> ChatListScreen()
                        1 -> StatusListScreen(
                            openAndPopUp = {route, popUp -> }
                        )
                        2 -> CallsListScreen()
                    }
                }
            }
            FloatingActionButton(
                onClick = {},
                modifier = Modifier
                    .padding(16.dp)
                    .size(60.dp)
                    .align(Alignment.BottomEnd),
                shape = CircleShape,
            containerColor =  PrimaryGreen_A102
            ) {
                Icon(
                    painter = painterResource(actionButtonDrawable),
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    }
}