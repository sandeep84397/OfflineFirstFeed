package com.sandeep.offlinefirstfeed.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sandeep.kmpcore.data.paging.BasePagingSource
import com.sandeep.offlinefirstfeed.db.FeedDatabase
import com.sandeep.offlinefirstfeed.db.SelectPaged

class FeedPagingSource(
    private val db: FeedDatabase,
    private val pageSize: Int,
) : BasePagingSource<Int, SelectPaged>() {

    override suspend fun loadPage(
        params: LoadParams<Int>,
    ): PagingSource.LoadResult.Page<Int, SelectPaged> {
        val offset = params.key ?: 0
        val limit = params.loadSize
        val rows = db.postQueries.selectPaged(
            limit = limit.toLong(),
            offset = offset.toLong(),
        ).executeAsList()
        val nextKey = if (rows.size < limit) null else offset + limit
        val prevKey = if (offset == 0) null else maxOf(0, offset - limit)
        return PagingSource.LoadResult.Page(
            data = rows,
            prevKey = prevKey,
            nextKey = nextKey,
        )
    }

    override fun getRefreshKey(state: PagingState<Int, SelectPaged>): Int? =
        state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(pageSize)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(pageSize)
        }
}
