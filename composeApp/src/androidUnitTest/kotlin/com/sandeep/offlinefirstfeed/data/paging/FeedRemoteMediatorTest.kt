@file:OptIn(androidx.paging.ExperimentalPagingApi::class)

package com.sandeep.offlinefirstfeed.data.paging

import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.sandeep.offlinefirstfeed.data.remote.api.InMemoryFeedApi
import com.sandeep.offlinefirstfeed.db.FeedDatabase
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FeedRemoteMediatorTest {

    private fun inMemoryDatabase(): FeedDatabase {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        FeedDatabase.Schema.create(driver)
        return FeedDatabase(driver)
    }

    @Test
    fun refresh_insertsPostsIntoDatabase() = runTest {
        val db = inMemoryDatabase()
        val api = InMemoryFeedApi()
        val mediator = FeedRemoteMediator(db = db, api = api, pageSize = 20)

        val result = mediator.load(
            loadType = LoadType.REFRESH,
            state = PagingState(
                pages = emptyList(),
                anchorPosition = null,
                config = PagingConfig(pageSize = 20),
                leadingPlaceholderCount = 0,
            ),
        )

        val count = db.postQueries.count().executeAsOne()
        assertTrue(count > 0L, "Expected posts in DB after REFRESH, got $count")
    }

    @Test
    fun append_afterRefresh_insertsMorePosts() = runTest {
        val db = inMemoryDatabase()
        val api = InMemoryFeedApi()
        val mediator = FeedRemoteMediator(db = db, api = api, pageSize = 20)

        val refreshState = PagingState<Int, com.sandeep.offlinefirstfeed.db.SelectPaged>(
            pages = emptyList(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0,
        )

        mediator.load(loadType = LoadType.REFRESH, state = refreshState)
        val afterRefresh = db.postQueries.count().executeAsOne()

        mediator.load(loadType = LoadType.APPEND, state = refreshState)
        val afterAppend = db.postQueries.count().executeAsOne()

        assertTrue(afterAppend > afterRefresh, "Expected more posts after APPEND ($afterAppend > $afterRefresh)")
    }
}
