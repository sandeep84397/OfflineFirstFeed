package com.sandeep.offlinefirstfeed.presentation.mapper

import com.sandeep.offlinefirstfeed.domain.model.Post
import com.sandeep.offlinefirstfeed.domain.model.SyncStatus
import com.sandeep.offlinefirstfeed.domain.model.User
import com.sandeep.offlinefirstfeed.presentation.feed.SyncBadge
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class PostUiMapperTest {

    private fun makePost(
        likeCount: Int = 0,
        syncStatus: SyncStatus = SyncStatus.SYNCED,
        createdAt: Instant = Clock.System.now() - 30.seconds,
    ) = Post(
        id = "post-001",
        author = User("u1", "Ada Lovelace", null),
        text = "Hello",
        imageUrl = null,
        likeCount = likeCount,
        likedByMe = false,
        createdAt = createdAt,
        updatedAt = createdAt,
        syncStatus = syncStatus,
    )

    // --- likeCount formatting ---

    @Test
    fun `likeCount under 1000 rendered as-is`() {
        assertEquals("999", makePost(likeCount = 999).toUiModel().likeCount)
    }

    @Test
    fun `likeCount 1000 rendered as 1_0k`() {
        assertEquals("1.0k", makePost(likeCount = 1_000).toUiModel().likeCount)
    }

    @Test
    fun `likeCount 1500 rendered as 1_5k`() {
        assertEquals("1.5k", makePost(likeCount = 1_500).toUiModel().likeCount)
    }

    @Test
    fun `likeCount 1_000_000 rendered as 1M`() {
        assertEquals("1M", makePost(likeCount = 1_000_000).toUiModel().likeCount)
    }

    @Test
    fun `likeCount 2_500_000 rendered as 2M`() {
        assertEquals("2M", makePost(likeCount = 2_500_000).toUiModel().likeCount)
    }

    // --- timeAgo formatting ---

    @Test
    fun `timeAgo seconds`() {
        val post = makePost(createdAt = Clock.System.now() - 45.seconds)
        val timeAgo = post.toUiModel().timeAgo
        assertTrue(timeAgo.endsWith("s ago"), "Expected '...s ago' but got '$timeAgo'")
    }

    @Test
    fun `timeAgo minutes`() {
        val post = makePost(createdAt = Clock.System.now() - 5.minutes)
        val timeAgo = post.toUiModel().timeAgo
        assertTrue(timeAgo.endsWith("m ago"), "Expected '...m ago' but got '$timeAgo'")
    }

    @Test
    fun `timeAgo hours`() {
        val post = makePost(createdAt = Clock.System.now() - 3.hours)
        val timeAgo = post.toUiModel().timeAgo
        assertTrue(timeAgo.endsWith("h ago"), "Expected '...h ago' but got '$timeAgo'")
    }

    @Test
    fun `timeAgo days`() {
        val post = makePost(createdAt = Clock.System.now() - 48.hours)
        val timeAgo = post.toUiModel().timeAgo
        assertTrue(timeAgo.endsWith("d ago"), "Expected '...d ago' but got '$timeAgo'")
    }

    // --- syncBadge mapping ---

    @Test
    fun `SYNCED maps to SyncBadge_NONE`() {
        assertEquals(SyncBadge.NONE, makePost(syncStatus = SyncStatus.SYNCED).toUiModel().syncBadge)
    }

    @Test
    fun `PENDING_CREATE maps to SyncBadge_PENDING`() {
        assertEquals(SyncBadge.PENDING, makePost(syncStatus = SyncStatus.PENDING_CREATE).toUiModel().syncBadge)
    }

    @Test
    fun `PENDING_UPDATE maps to SyncBadge_PENDING`() {
        assertEquals(SyncBadge.PENDING, makePost(syncStatus = SyncStatus.PENDING_UPDATE).toUiModel().syncBadge)
    }

    @Test
    fun `PENDING_DELETE maps to SyncBadge_PENDING`() {
        assertEquals(SyncBadge.PENDING, makePost(syncStatus = SyncStatus.PENDING_DELETE).toUiModel().syncBadge)
    }

    @Test
    fun `FAILED maps to SyncBadge_FAILED`() {
        assertEquals(SyncBadge.FAILED, makePost(syncStatus = SyncStatus.FAILED).toUiModel().syncBadge)
    }

    // --- field passthrough ---

    @Test
    fun `toUiModel passes through id and author fields`() {
        val ui = makePost().toUiModel()
        assertEquals("post-001", ui.id)
        assertEquals("Ada Lovelace", ui.authorDisplayName)
    }
}
