package com.sandeep.offlinefirstfeed.presentation.feed

data class FeedState(
    val isRefreshing: Boolean = false,
    val isOffline: Boolean = false,
    val errorMessage: String? = null,
)
