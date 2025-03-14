package com.example.whatsappclonei.ui.status

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsappclonei.data.StatusRepository
import com.example.whatsappclonei.data.model.Response
import com.example.whatsappclonei.data.model.Status
import com.example.whatsappclonei.data.model.StatusItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatusViewModel @Inject constructor(
    private val statusRepository: StatusRepository
) : ViewModel() {


    var createStatusResponse = mutableStateOf<Response<Boolean>>(Response.None)
        private set
    var addViewerResponse = mutableStateOf<Response<Boolean>>(Response.None)
        private set
    var deleteExpiredStatusesResponse = mutableStateOf<Response<Boolean>>(Response.None)
        private set
    val statuses: StateFlow<Response<List<Status>>> = statusRepository.getStatuses()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Response.Loading
        )

    fun createStatus(status: Status) {
        viewModelScope.launch {
            createStatusResponse.value = Response.Loading
            createStatusResponse.value = statusRepository.createStatus(status)
        }
    }

    fun addViewer(userId: String, statusId: String, statusItem: StatusItem) {
        viewModelScope.launch {
            addViewerResponse.value = Response.Loading
            addViewerResponse.value = statusRepository.addViewer(userId, statusId, statusItem)
        }
    }
    fun deleteExpiredStatuses() {
        viewModelScope.launch {
            deleteExpiredStatusesResponse.value = Response.Loading
            deleteExpiredStatusesResponse.value = statusRepository.deleteExpiredStatuses()
        }
    }
}