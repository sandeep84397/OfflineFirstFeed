package com.sandeep.offlinefirstfeed.domain.usecase

import androidx.paging.PagingData
import com.sandeep.offlinefirstfeed.domain.model.Post
import com.sandeep.offlinefirstfeed.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow

open class GetFeedUseCase(private val feedRepository: FeedRepository) {
    open operator fun invoke(): Flow<PagingData<Post>> = feedRepository.observeFeed()
}
