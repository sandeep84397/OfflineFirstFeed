@file:OptIn(androidx.paging.ExperimentalPagingApi::class)

package com.sandeep.offlinefirstfeed.data.paging

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.sandeep.kmpcore.data.paging.BaseRemoteMediator
import com.sandeep.offlinefirstfeed.data.remote.api.FakeFeedApi
import com.sandeep.offlinefirstfeed.db.FeedDatabase
import com.sandeep.offlinefirstfeed.db.SelectPaged
import kotlinx.datetime.Clock

private const val FEED_REMOTE_KEY_ID = "feed"

class FeedRemoteMediator(
    private val db: FeedDatabase,
    private val api: FakeFeedApi,
    private val pageSize: Int = 20,
) : BaseRemoteMediator<Int, SelectPaged>() {

    override suspend fun loadPage(
        loadType: LoadType,
        state: PagingState<Int, SelectPaged>,
    ): RemoteMediator.MediatorResult {
        val offset = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return RemoteMediator.MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKey = db.remoteKeyQueries
                    .selectById(FEED_REMOTE_KEY_ID)
                    .executeAsOneOrNull()
                remoteKey?.nextOffset?.toInt()
                    ?: return RemoteMediator.MediatorResult.Success(endOfPaginationReached = true)
            }
        }

        val page = api.fetchFeed(offset = offset, limit = pageSize)

        db.transaction {
            if (loadType == LoadType.REFRESH) {
                db.remoteKeyQueries.deleteById(FEED_REMOTE_KEY_ID)
            }

            page.posts.forEachIndexed { index, dto ->
                db.userQueries.insertOrReplace(
                    id = dto.author.id,
                    displayName = dto.author.displayName,
                    avatarUrl = dto.author.avatarUrl,
                )
                // Every 10th post seeded as PENDING_CREATE to demonstrate
                // the sync-status badge rendering path (AC4 — no writes required).
                val syncStatus = if ((offset + index) % 10 == 0) "PENDING_CREATE" else "SYNCED"
                db.postQueries.insertOrReplace(
                    id = dto.id,
                    authorId = dto.author.id,
                    text = dto.text,
                    imageUrl = dto.imageUrl,
                    likeCount = dto.likeCount.toLong(),
                    likedByMe = if (dto.likedByMe) 1L else 0L,
                    createdAt = dto.createdAtMillis,
                    updatedAt = dto.updatedAtMillis,
                    syncStatus = syncStatus,
                )
            }

            db.remoteKeyQueries.insertOrReplace(
                id = FEED_REMOTE_KEY_ID,
                nextOffset = page.nextOffset?.toLong(),
                prevOffset = if (offset == 0) null else (offset - pageSize).toLong(),
                fetchedAt = Clock.System.now().toEpochMilliseconds(),
            )
        }

        return RemoteMediator.MediatorResult.Success(endOfPaginationReached = page.nextOffset == null)
    }
}
