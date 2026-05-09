package com.sandeep.offlinefirstfeed.data.local.database

import com.sandeep.offlinefirstfeed.db.FeedDatabase

expect class DatabaseFactory {
    fun create(): FeedDatabase
}
