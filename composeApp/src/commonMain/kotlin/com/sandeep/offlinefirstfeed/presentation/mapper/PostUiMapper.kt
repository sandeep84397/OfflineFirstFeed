package com.sandeep.offlinefirstfeed.presentation.mapper

import com.sandeep.offlinefirstfeed.domain.model.Post
import com.sandeep.offlinefirstfeed.domain.model.SyncStatus
import com.sandeep.offlinefirstfeed.presentation.feed.PostUiModel
import com.sandeep.offlinefirstfeed.presentation.feed.SyncBadge
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration

fun Post.toUiModel(): PostUiModel = PostUiModel(
    id = id,
    authorDisplayName = author.displayName,
    authorAvatarUrl = author.avatarUrl,
    text = text,
    imageUrl = imageUrl,
    likeCount = formatLikeCount(likeCount),
    likedByMe = likedByMe,
    timeAgo = formatTimeAgo(createdAt),
    syncBadge = syncStatus.toBadge(),
)

private fun formatLikeCount(count: Int): String = when {
    count >= 1_000_000 -> "${count / 1_000_000}M"
    count >= 1_000 -> "${(count / 100) / 10.0}k"
    else -> count.toString()
}

private fun formatTimeAgo(instant: Instant): String {
    val diff: Duration = Clock.System.now() - instant
    return when {
        diff.inWholeSeconds < 60 -> "${diff.inWholeSeconds}s ago"
        diff.inWholeMinutes < 60 -> "${diff.inWholeMinutes}m ago"
        diff.inWholeHours < 24 -> "${diff.inWholeHours}h ago"
        else -> "${diff.inWholeDays}d ago"
    }
}

private fun SyncStatus.toBadge(): SyncBadge = when (this) {
    SyncStatus.SYNCED -> SyncBadge.NONE
    SyncStatus.PENDING_CREATE,
    SyncStatus.PENDING_UPDATE,
    SyncStatus.PENDING_DELETE -> SyncBadge.PENDING
    SyncStatus.FAILED -> SyncBadge.FAILED
}
