package com.example.whatsappclonei.ui.status.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.whatsappclonei.ui.status.components.ListHeader
import com.example.whatsappclonei.ui.status.components.StatusImageSection
import com.example.whatsappclonei.ui.status.components.StatusUpdateSection
import com.example.whatsappclonei.ui.status.data.statusList
import com.example.whatsappclonei.ui.status.domain.StatusUpdateCategory
import com.example.whatsappclonei.R
import com.example.whatsappclonei.components.ext.fieldModifier
import com.example.whatsappclonei.screens.CREATE_STATUS_SCREEN
import com.example.whatsappclonei.screens.STATUS_SCREEN

@Composable
fun StatusListScreen(
    openAndPopUp: (String,String) -> Unit
)
{
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        item{
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
            items = statusList.filter { it.category == StatusUpdateCategory.RECENT },
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
                    openAndPopUp(
                        CREATE_STATUS_SCREEN,
                        STATUS_SCREEN
                    )
                }
            )
        }
        item {
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
                    openAndPopUp(
                        CREATE_STATUS_SCREEN,
                        STATUS_SCREEN
                    )
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
        item {
            Spacer(modifier = Modifier.height(75.dp))
        }
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
