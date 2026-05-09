package com.sandeep.offlinefirstfeed.fixtures

import com.sandeep.offlinefirstfeed.domain.model.Post
import com.sandeep.offlinefirstfeed.domain.model.SyncStatus
import com.sandeep.offlinefirstfeed.domain.model.User
import kotlinx.datetime.Instant

fun aUser(
    id: String = "user-001",
    displayName: String = "Ada Lovelace",
    avatarUrl: String? = null,
) = User(id = id, displayName = displayName, avatarUrl = avatarUrl)

fun aPost(
    id: String = "post-001",
    author: User = aUser(),
    text: String = "Hello, offline world.",
    imageUrl: String? = null,
    likeCount: Int = 0,
    likedByMe: Boolean = false,
    createdAt: Instant = Instant.fromEpochMilliseconds(1_700_000_000_000L),
    updatedAt: Instant = Instant.fromEpochMilliseconds(1_700_000_000_000L),
    syncStatus: SyncStatus = SyncStatus.SYNCED,
) = Post(
    id = id,
    author = author,
    text = text,
    imageUrl = imageUrl,
    likeCount = likeCount,
    likedByMe = likedByMe,
    createdAt = createdAt,
    updatedAt = updatedAt,
    syncStatus = syncStatus,
)
