package com.sandeep.offlinefirstfeed.domain.repository

import androidx.paging.PagingData
import com.sandeep.offlinefirstfeed.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    fun observeFeed(): Flow<PagingData<Post>>
}
