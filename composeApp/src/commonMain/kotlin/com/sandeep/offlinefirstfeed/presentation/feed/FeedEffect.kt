package com.sandeep.offlinefirstfeed.presentation.feed

sealed interface FeedEffect {
    data class ShowToast(val message: String) : FeedEffect
}
