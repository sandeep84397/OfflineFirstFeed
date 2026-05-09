package com.sandeep.offlinefirstfeed.presentation.feed

data class PostUiModel(
    val id: String,
    val authorDisplayName: String,
    val authorAvatarUrl: String?,
    val text: String,
    val imageUrl: String?,
    val likeCount: String,
    val likedByMe: Boolean,
    val timeAgo: String,
    val syncBadge: SyncBadge,
)

enum class SyncBadge { NONE, PENDING, FAILED }
