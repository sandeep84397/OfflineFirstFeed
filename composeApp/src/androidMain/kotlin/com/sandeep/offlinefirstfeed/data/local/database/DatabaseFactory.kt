package com.sandeep.offlinefirstfeed.data.local.database

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.sandeep.offlinefirstfeed.db.FeedDatabase

actual class DatabaseFactory(private val context: Context) {
    actual fun create(): FeedDatabase = FeedDatabase(
        driver = AndroidSqliteDriver(FeedDatabase.Schema, context, "feed.db"),
    )
}
