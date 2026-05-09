@file:OptIn(androidx.paging.ExperimentalPagingApi::class)

package com.sandeep.kmpcore.data.paging

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator

abstract class BaseRemoteMediator<Key : Any, Value : Any> : RemoteMediator<Key, Value>() {

    protected abstract suspend fun loadPage(
        loadType: LoadType,
        state: PagingState<Key, Value>,
    ): MediatorResult

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Key, Value>,
    ): MediatorResult = try {
        if (loadType == LoadType.PREPEND) {
            MediatorResult.Success(endOfPaginationReached = true)
        } else {
            loadPage(loadType, state)
        }
    } catch (e: Exception) {
        MediatorResult.Error(e)
    }
}
