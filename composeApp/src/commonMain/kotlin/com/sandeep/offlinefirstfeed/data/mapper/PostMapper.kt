package com.sandeep.offlinefirstfeed.data.mapper

import com.sandeep.offlinefirstfeed.data.remote.dto.PostDto
import com.sandeep.offlinefirstfeed.domain.model.Post
import com.sandeep.offlinefirstfeed.domain.model.SyncStatus
import com.sandeep.offlinefirstfeed.domain.model.User
import kotlinx.datetime.Instant

internal fun PostDto.toDomain(syncStatus: SyncStatus = SyncStatus.SYNCED): Post = Post(
    id = id,
    author = User(
        id = author.id,
        displayName = author.displayName,
        avatarUrl = author.avatarUrl,
    ),
    text = text,
    imageUrl = imageUrl,
    likeCount = likeCount,
    likedByMe = likedByMe,
    createdAt = Instant.fromEpochMilliseconds(createdAtMillis),
    updatedAt = Instant.fromEpochMilliseconds(updatedAtMillis),
    syncStatus = syncStatus,
)
