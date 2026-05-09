package com.sandeep.offlinefirstfeed.data.repository

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.sandeep.offlinefirstfeed.data.remote.api.InMemoryFeedApi
import com.sandeep.offlinefirstfeed.db.FeedDatabase
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class FeedRepositoryImplTest {

    private fun inMemoryDatabase(): FeedDatabase {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        FeedDatabase.Schema.create(driver)
        return FeedDatabase(driver)
    }

    @Test
    fun observeFeed_returnsNonNullFlow() = runTest {
        val db = inMemoryDatabase()
        val repository = FeedRepositoryImpl(db = db, api = InMemoryFeedApi())
        assertNotNull(repository.observeFeed())
    }
}
