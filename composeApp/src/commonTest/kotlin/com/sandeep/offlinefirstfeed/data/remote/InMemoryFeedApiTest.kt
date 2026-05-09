package com.sandeep.offlinefirstfeed.data.remote

import com.sandeep.offlinefirstfeed.data.remote.api.InMemoryFeedApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class InMemoryFeedApiTest {

    private val api = InMemoryFeedApi()

    @Test
    fun `fetchFeed returns requested limit`() = runTest {
        val page = api.fetchFeed(offset = 0, limit = 10)
        assertEquals(10, page.posts.size)
    }

    @Test
    fun `fetchFeed first page starts from index 0`() = runTest {
        val page = api.fetchFeed(offset = 0, limit = 5)
        assertEquals("post-000", page.posts.first().id)
        assertEquals("post-004", page.posts.last().id)
    }

    @Test
    fun `fetchFeed second page respects offset`() = runTest {
        val page = api.fetchFeed(offset = 10, limit = 5)
        assertEquals("post-010", page.posts.first().id)
    }

    @Test
    fun `fetchFeed provides nextOffset when more items exist`() = runTest {
        val page = api.fetchFeed(offset = 0, limit = 10)
        assertNotNull(page.nextOffset)
        assertEquals(10, page.nextOffset)
    }

    @Test
    fun `fetchFeed nextOffset is null on last page`() = runTest {
        val page = api.fetchFeed(offset = 45, limit = 10)
        assertNull(page.nextOffset)
    }

    @Test
    fun `fetchFeed last partial page returns remaining items`() = runTest {
        val page = api.fetchFeed(offset = 48, limit = 10)
        assertEquals(2, page.posts.size)
    }

    @Test
    fun `fetchFeed total seed is 50 posts`() = runTest {
        var offset = 0
        var total = 0
        while (true) {
            val page = api.fetchFeed(offset = offset, limit = 20)
            total += page.posts.size
            offset = page.nextOffset ?: break
        }
        assertEquals(50, total)
    }

    @Test
    fun `post ids are unique across full dataset`() = runTest {
        val all = mutableListOf<String>()
        var offset = 0
        while (true) {
            val page = api.fetchFeed(offset = offset, limit = 20)
            all += page.posts.map { it.id }
            offset = page.nextOffset ?: break
        }
        assertEquals(all.size, all.toSet().size)
    }
}
