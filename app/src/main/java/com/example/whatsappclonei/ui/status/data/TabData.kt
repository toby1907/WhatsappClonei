package com.example.whatsappclonei.ui.status.data

val homeTabs = listOf(
    TabData(Tabs.CHAT), TabData(Tabs.MESSAGE_STATUS,1), TabData(Tabs.CALLS,5)
)

data class TabData(
    val tab: Tabs,
    val unreadCount: Int? = null,
)

enum class Tabs(val value: String) {
    CHAT("Chats"),
    MESSAGE_STATUS("Status"),
    CALLS("Calls");

    fun sameAs(tab: Tabs): Boolean {
        return this == tab
    }
}