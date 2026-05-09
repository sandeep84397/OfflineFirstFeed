package com.sandeep.offlinefirstfeed.data.remote.api

import com.sandeep.offlinefirstfeed.data.remote.dto.FeedPageDto
import com.sandeep.offlinefirstfeed.data.remote.dto.PostDto
import com.sandeep.offlinefirstfeed.data.remote.dto.UserDto
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

private val AUTHORS = listOf(
    UserDto("user-001", "Ada Lovelace", null),
    UserDto("user-002", "Grace Hopper", null),
    UserDto("user-003", "Linus Torvalds", null),
    UserDto("user-004", "Margaret Hamilton", null),
    UserDto("user-005", "Hedy Lamarr", null),
)

private const val BASE_TIME_MS = 1_700_000_000_000L

internal fun buildSeedPosts(count: Int = 50): List<PostDto> =
    (0 until count).map { index ->
        val author = AUTHORS[index % AUTHORS.size]
        PostDto(
            id = "post-${index.toString().padStart(3, '0')}",
            author = author,
            text = seedText(index),
            imageUrl = null,
            likeCount = index * 3,
            likedByMe = index % 7 == 0,
            createdAtMillis = BASE_TIME_MS - (index * 60_000L),
            updatedAtMillis = BASE_TIME_MS - (index * 60_000L),
        )
    }

private fun seedText(index: Int): String = when (index % 5) {
    0 -> "Offline-first means your users never see a spinner. Post #$index."
    1 -> "The DB is the source of truth. The network is just a sync partner. Post #$index."
    2 -> "MVI: one State, one direction, zero surprises. Post #$index."
    3 -> "Clean Architecture is a forcing function, not a religion. Post #$index."
    else -> "KMP lets you write this once and run it on iOS too. Post #$index."
}

class InMemoryFeedApi(private val seed: Long = 42L) : FakeFeedApi {

    private val posts: List<PostDto> = buildSeedPosts(50)

    override suspend fun fetchFeed(offset: Int, limit: Int): FeedPageDto {
        delay(150.milliseconds)
        val page = posts.drop(offset).take(limit)
        val nextOffset = if (offset + limit < posts.size) offset + limit else null
        return FeedPageDto(posts = page, nextOffset = nextOffset)
    }
}
