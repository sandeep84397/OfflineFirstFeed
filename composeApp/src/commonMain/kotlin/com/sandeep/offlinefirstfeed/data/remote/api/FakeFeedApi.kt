package com.sandeep.offlinefirstfeed.data.remote.api

import com.sandeep.offlinefirstfeed.data.remote.dto.FeedPageDto

interface FakeFeedApi {
    suspend fun fetchFeed(offset: Int, limit: Int): FeedPageDto
}
