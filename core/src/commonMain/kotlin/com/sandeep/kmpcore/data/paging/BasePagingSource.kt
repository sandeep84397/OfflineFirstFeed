package com.sandeep.kmpcore.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class BasePagingSource<Key : Any, Value : Any> : PagingSource<Key, Value>() {

    protected abstract suspend fun loadPage(params: LoadParams<Key>): LoadResult.Page<Key, Value>

    final override suspend fun load(params: LoadParams<Key>): LoadResult<Key, Value> = try {
        loadPage(params)
    } catch (e: Exception) {
        LoadResult.Error(e)
    }

    override fun getRefreshKey(state: PagingState<Key, Value>): Key? =
        state.anchorPosition?.let { anchor ->
            val closest = state.closestPageToPosition(anchor)
            closest?.prevKey ?: closest?.nextKey
        }
}
