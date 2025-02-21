package com.example.whatsappclonei.ui.status

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.whatsappclonei.data.model.Status
import com.example.whatsappclonei.utils.DummyData

@Composable
fun StatusScreen(
    viewModel: StatusViewModel = hiltViewModel(),
    onStatusClick: (Status) -> Unit,
    onMyStatusClick: () -> Unit,
    onNewStatusClick: () -> Unit,
    isTesting: Boolean = false
) {
    var statuses by remember { mutableStateOf<List<Status>>(emptyList()) }

    LaunchedEffect(key1 = isTesting) {
        statuses = if (isTesting) {
            DummyData.getDummyStatuses()
        } else {
            viewModel.statuses.value
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNewStatusClick) {
                Icon(Icons.Filled.Add, contentDescription = "New Status")
            }
        }
    ) { paddingValues ->
        LazyRow(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(8.dp)
        ) {
            item {
                MyStatusItem(onMyStatusClick)
                Spacer(modifier = Modifier.height(8.dp))
                Divider()
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(statuses) { status ->
                StatusItem(status, onStatusClick)
            }
        }
    }
}