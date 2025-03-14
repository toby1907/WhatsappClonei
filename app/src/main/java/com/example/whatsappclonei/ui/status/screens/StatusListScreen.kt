package com.example.whatsappclonei.ui.status.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.whatsappclonei.R
import com.example.whatsappclonei.components.ext.fieldModifier
import com.example.whatsappclonei.data.model.Response
import com.example.whatsappclonei.data.model.Status
import com.example.whatsappclonei.screens.CREATE_STATUS_SCREEN
import com.example.whatsappclonei.screens.STATUSESPREVIEW
import com.example.whatsappclonei.screens.STATUS_SCREEN
import com.example.whatsappclonei.ui.status.StatusViewModel
import com.example.whatsappclonei.ui.status.components.ListHeader
import com.example.whatsappclonei.ui.status.components.StatusImageSection
import com.example.whatsappclonei.ui.status.components.StatusUpdateSection
import com.example.whatsappclonei.ui.status.toFormattedString

@Composable
fun StatusListScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: StatusViewModel = hiltViewModel()
) {
    val statusesResponse by viewModel.statuses.collectAsStateWithLifecycle()

    when (statusesResponse) {
        is Response.Loading -> {
            // Show a loading indicator
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is Response.Success -> {
            val statuses = (statusesResponse as Response.Success<List<Status>>).data
            if (statuses.isEmpty()) {
                // Show a message if there are no statuses
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No statuses available")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        StatusRowSection(
                            isOwner = true,
                            header = stringResource(id = R.string.my_status),
                            subHeader = stringResource(id = R.string.tap_to_add_status_update),
                            modifier = Modifier.clickable {
                                openAndPopUp(CREATE_STATUS_SCREEN, STATUS_SCREEN)
                            }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        ListHeader(
                            stringResource(id = R.string.recent_updates)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    items(
                        items = statuses,
                        key = { status ->
                            status.userId
                        }
                    ) { status ->

                        StatusRowSection(
                            statusImage = status.userPhotoUrl,
                            header = status.userName,
                            subHeader = status.statusItems[0].timestamp.toDate().toFormattedString(),
                            modifier = Modifier.clickable {
                                openAndPopUp(
                                    STATUSESPREVIEW,
                                    STATUS_SCREEN
                                )
                            }
                        )
                    }
                    /*item {
                        Spacer(modifier = Modifier.height(24.dp))
                        ListHeader(
                            stringResource(id = R.string.viewed_updates)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    items(
                        items = statusList.filter { it.category == StatusUpdateCategory.VIEWED },
                        key = { status ->
                            status.id
                        }
                    ) { status ->
                        StatusRowSection(
                            statusCount = status.statusCount,
                            statusImage = status.statusImage,
                            header = status.userName,
                            subHeader = status.timeStamp,
                            modifier = Modifier.clickable {

                            }
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                    }*/
                    item {
                        Spacer(modifier = Modifier.height(75.dp))
                    }
                }
            }
        }

        is Response.Failure -> {
            // Show an error message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error loading statuses")
            }
        }

        Response.None -> { }
    }
}

@Composable
fun StatusRowSection(
    isOwner: Boolean = false,
    statusCount: Int? = null,
    statusImage: String? = null,
    header: String,
    subHeader: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
    ) {
        StatusImageSection(
            isOwner,
            statusCount,
            statusImage
        )
        StatusUpdateSection(header, subHeader)
    }
}