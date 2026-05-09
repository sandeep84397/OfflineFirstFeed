package com.sandeep.offlinefirstfeed.fake

import androidx.paging.PagingData
import com.sandeep.offlinefirstfeed.domain.model.Post
import com.sandeep.offlinefirstfeed.domain.usecase.GetFeedUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeGetFeedUseCase(
    private val posts: List<Post> = emptyList(),
) : GetFeedUseCase(FakeFeedRepository(posts)) {
    override operator fun invoke(): Flow<PagingData<Post>> =
        flowOf(PagingData.from(posts))
}
