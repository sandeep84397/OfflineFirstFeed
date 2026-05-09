package com.sandeep.offlinefirstfeed.domain.model

import kotlinx.datetime.Instant

data class Post(
    val id: String,
    val author: User,
    val text: String,
    val imageUrl: String?,
    val likeCount: Int,
    val likedByMe: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant,
    val syncStatus: SyncStatus,
)
