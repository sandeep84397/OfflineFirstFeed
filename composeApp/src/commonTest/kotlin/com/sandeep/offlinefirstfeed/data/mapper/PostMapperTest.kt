package com.sandeep.offlinefirstfeed.data.mapper

import com.sandeep.offlinefirstfeed.data.remote.dto.PostDto
import com.sandeep.offlinefirstfeed.data.remote.dto.UserDto
import com.sandeep.offlinefirstfeed.domain.model.SyncStatus
import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PostMapperTest {

    private val userDto = UserDto(
        id = "user-001",
        displayName = "Ada Lovelace",
        avatarUrl = "https://example.com/ada.jpg",
    )

    private val postDto = PostDto(
        id = "post-001",
        author = userDto,
        text = "Offline-first is the future.",
        imageUrl = null,
        likeCount = 42,
        likedByMe = true,
        createdAtMillis = 1_700_000_000_000L,
        updatedAtMillis = 1_700_000_001_000L,
    )

    @Test
    fun `toDomain maps id correctly`() {
        assertEquals("post-001", postDto.toDomain().id)
    }

    @Test
    fun `toDomain maps author fields`() {
        val post = postDto.toDomain()
        assertEquals("user-001", post.author.id)
        assertEquals("Ada Lovelace", post.author.displayName)
        assertEquals("https://example.com/ada.jpg", post.author.avatarUrl)
    }

    @Test
    fun `toDomain maps text and null imageUrl`() {
        val post = postDto.toDomain()
        assertEquals("Offline-first is the future.", post.text)
        assertNull(post.imageUrl)
    }

    @Test
    fun `toDomain maps likeCount and likedByMe`() {
        val post = postDto.toDomain()
        assertEquals(42, post.likeCount)
        assertEquals(true, post.likedByMe)
    }

    @Test
    fun `toDomain converts millis to Instant`() {
        val post = postDto.toDomain()
        assertEquals(Instant.fromEpochMilliseconds(1_700_000_000_000L), post.createdAt)
        assertEquals(Instant.fromEpochMilliseconds(1_700_000_001_000L), post.updatedAt)
    }

    @Test
    fun `toDomain defaults syncStatus to SYNCED`() {
        assertEquals(SyncStatus.SYNCED, postDto.toDomain().syncStatus)
    }

    @Test
    fun `toDomain uses provided syncStatus`() {
        assertEquals(SyncStatus.PENDING_CREATE, postDto.toDomain(SyncStatus.PENDING_CREATE).syncStatus)
        assertEquals(SyncStatus.FAILED, postDto.toDomain(SyncStatus.FAILED).syncStatus)
    }
}
