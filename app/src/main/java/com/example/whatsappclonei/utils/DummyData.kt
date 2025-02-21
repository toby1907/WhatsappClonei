package com.example.whatsappclonei.utils


import com.example.whatsappclonei.data.model.Status
import com.example.whatsappclonei.data.model.StatusItem
import com.google.firebase.Timestamp
import java.util.UUID

object DummyData {
    fun getDummyStatuses(): List<Status> {
        val now = Timestamp.now()
        val oneHourAgo = Timestamp(now.seconds - 3600, now.nanoseconds)
        val twoHoursAgo = Timestamp(now.seconds - 7200, now.nanoseconds)
        val threeHoursAgo = Timestamp(now.seconds - 10800, now.nanoseconds)
        val fourHoursAgo = Timestamp(now.seconds - 14400, now.nanoseconds)

        return listOf(
            Status(
                userId = "user1",
                userName = "Alice",
                userPhotoUrl = "https://via.placeholder.com/150?text=Alice",
                statusItems = listOf(
                    StatusItem(
                        statusId = UUID.randomUUID().toString(),
                        type = "text",
                        content = "Hello, this is Alice's first status!",
                        timestamp = oneHourAgo,
                        duration = 10,
                        viewers = listOf("user2", "user3")
                    ),
                    StatusItem(
                        statusId = UUID.randomUUID().toString(),
                        type = "image",
                        content = "https://via.placeholder.com/300?text=Image1",
                        timestamp = twoHoursAgo,
                        duration = 5,
                        viewers = listOf("user2")
                    )
                ),
                createdAt = oneHourAgo,
                expiresAt = Timestamp(now.seconds + 86400, now.nanoseconds)
            ),
            Status(
                userId = "user2",
                userName = "Bob",
                userPhotoUrl = "https://via.placeholder.com/150?text=Bob",
                statusItems = listOf(
                    StatusItem(
                        statusId = UUID.randomUUID().toString(),
                        type = "video",
                        content = "https://via.placeholder.com/300?text=Video1",
                        timestamp = threeHoursAgo,
                        duration = 15,
                        viewers = listOf("user1", "user3")
                    ),
                    StatusItem(
                        statusId = UUID.randomUUID().toString(),
                        type = "text",
                        content = "Bob's second status!",
                        timestamp = fourHoursAgo,
                        duration = 10,
                        viewers = listOf("user1")
                    )
                ),
                createdAt = threeHoursAgo,
                expiresAt = Timestamp(now.seconds + 86400, now.nanoseconds)
            ),
            Status(
                userId = "user3",
                userName = "Charlie",
                userPhotoUrl = "https://via.placeholder.com/150?text=Charlie",
                statusItems = listOf(
                    StatusItem(
                        statusId = UUID.randomUUID().toString(),
                        type = "image",
                        content = "https://via.placeholder.com/300?text=Image2",
                        timestamp = now,
                        duration = 5,
                        viewers = emptyList()
                    )
                ),
                createdAt = now,
                expiresAt = Timestamp(now.seconds + 86400, now.nanoseconds)
            )
        )
    }
}