package com.sandeep.offlinefirstfeed.data.local.database

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.sandeep.offlinefirstfeed.db.FeedDatabase

actual class DatabaseFactory {
    actual fun create(): FeedDatabase = FeedDatabase(
        driver = NativeSqliteDriver(FeedDatabase.Schema, "feed.db"),
    )
}
