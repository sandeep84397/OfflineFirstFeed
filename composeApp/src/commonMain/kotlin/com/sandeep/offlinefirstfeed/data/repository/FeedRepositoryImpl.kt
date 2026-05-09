@file:OptIn(androidx.paging.ExperimentalPagingApi::class)

package com.sandeep.offlinefirstfeed.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sandeep.kmpcore.data.local.adapters.booleanAdapter
import com.sandeep.kmpcore.data.local.adapters.instantAdapter
import com.sandeep.offlinefirstfeed.data.local.database.syncStatusAdapter
import com.sandeep.offlinefirstfeed.data.paging.FeedPagingSource
import com.sandeep.offlinefirstfeed.data.paging.FeedRemoteMediator
import com.sandeep.offlinefirstfeed.data.remote.api.FakeFeedApi
import com.sandeep.offlinefirstfeed.db.FeedDatabase
import com.sandeep.offlinefirstfeed.domain.model.Post
import com.sandeep.offlinefirstfeed.domain.model.User
import com.sandeep.offlinefirstfeed.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val PAGE_SIZE = 20

class FeedRepositoryImpl(
    private val db: FeedDatabase,
    private val api: FakeFeedApi,
) : FeedRepository {

    override fun observeFeed(): Flow<PagingData<Post>> =
        Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = FeedRemoteMediator(db = db, api = api, pageSize = PAGE_SIZE),
            pagingSourceFactory = { FeedPagingSource(db = db, pageSize = PAGE_SIZE) },
        ).flow.map { pagingData ->
            pagingData.map { row ->
                Post(
                    id = row.id,
                    author = User(
                        id = row.authorId,
                        displayName = row.authorDisplayName,
                        avatarUrl = row.authorAvatarUrl,
                    ),
                    text = row.text,
                    imageUrl = row.imageUrl,
                    likeCount = row.likeCount.toInt(),
                    likedByMe = booleanAdapter.decode(row.likedByMe),
                    createdAt = instantAdapter.decode(row.createdAt),
                    updatedAt = instantAdapter.decode(row.updatedAt),
                    syncStatus = syncStatusAdapter.decode(row.syncStatus),
                )
            }
        }
}
