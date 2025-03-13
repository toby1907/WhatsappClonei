package com.example.whatsappclonei.ui.status
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.whatsappclonei.R
import com.example.whatsappclonei.data.model.Status
import com.example.whatsappclonei.data.model.StatusItem
import com.example.whatsappclonei.ui.status.components.DownloadableImage
import com.example.whatsappclonei.ui.status.components.VideoPlayer
import com.example.whatsappclonei.ui.theme.White
import com.example.whatsappclonei.utils.DummyData
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserStatusesScreen(

    onBackClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
  viewModel: StatusViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = viewModel.statuses.value) {

        viewModel.getStatuses()
    }
    val statuses = viewModel.statuses.value
    val pagerState = rememberPagerState(pageCount = { statuses.size })

    HorizontalPager(state = pagerState) { page ->
        val userStatus = statuses[page]
        UserStatusPage(
            status = userStatus,
            onBackClick = onBackClick,
            onMenuClick = onMenuClick
        )
    }
}

@Composable
fun UserStatusPage(
    status: Status,
    onBackClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    var currentStatusItemIndex by remember { mutableStateOf(0) }
    val progress = remember {
        status.statusItems.map { Animatable(0f) }
    }

    LaunchedEffect(key1 = currentStatusItemIndex) {
        if (currentStatusItemIndex < status.statusItems.size) {
            progress[currentStatusItemIndex].animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 10000, // 30 seconds per status item
                    easing = LinearEasing
                )
            )
            if (currentStatusItemIndex < status.statusItems.size - 1) {
                currentStatusItemIndex++
                progress[currentStatusItemIndex].snapTo(0f)
            } else {
                // Handle end of status items for this user (e.g., go to next user)
                // For now, we'll just reset to the first item
                currentStatusItemIndex = 0
                progress[currentStatusItemIndex].snapTo(0f)
            }
        }
    }

    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        // Status Content (Image/Video)
        val currentStatusItem = status.statusItems[currentStatusItemIndex]
        when (currentStatusItem.type) {
            "image" -> {
                DownloadableImage(imageUrl = currentStatusItem.imageUrl ?: "")
            }

            "video" -> {
                // Implement video player here
                // You can use a library like ExoPlayer
                VideoPlayer(videoUri = currentStatusItem.videoUrl ?: "")
            }

            "text" -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = currentStatusItem.text ?: "",
                        color = Color.White,
                        fontSize = 24.sp
                    )
                }
            }
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Progress Bars
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                status.statusItems.forEachIndexed { index, _ ->
                    LinearProgressIndicator(
                        progress = if (index < currentStatusItemIndex) 1f else if (index == currentStatusItemIndex) progress[index].value else 0f,
                        modifier = Modifier
                            .weight(1f)
                            .height(2.dp),
                        color = Color.White
                    )
                }
            }

            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onBackClick() }
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    /*AsyncImage(
                        model = status.userPhotoUrl,
                        contentDescription = "User Profile",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )*/
                    Image(
                        painter = painterResource(id = R.drawable.img_default_user),
                        contentDescription = "default user image",
                        modifier = Modifier
                            .size(50.dp)
                            .border(1.dp, White, CircleShape)
                            .clip(CircleShape),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = status.userName,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = status.statusItems[currentStatusItemIndex].timestamp.toDate().toFormattedString(),
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
                IconButton(onClick = onMenuClick) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

fun Date.toFormattedString(): String {
    val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return formatter.format(this)
}