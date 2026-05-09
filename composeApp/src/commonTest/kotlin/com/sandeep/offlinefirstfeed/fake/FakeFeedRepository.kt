package com.sandeep.offlinefirstfeed.fake

import androidx.paging.PagingData
import com.sandeep.offlinefirstfeed.domain.model.Post
import com.sandeep.offlinefirstfeed.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeFeedRepository(
    private val posts: List<Post> = emptyList(),
) : FeedRepository {
    override fun observeFeed(): Flow<PagingData<Post>> =
        flowOf(PagingData.from(posts))
}
