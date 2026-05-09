package com.sandeep.offlinefirstfeed.presentation.feed

sealed interface FeedIntent {
    data object ScreenStarted : FeedIntent
    data object Refresh : FeedIntent
    data class PostClicked(val postId: String) : FeedIntent
    data object CreatePostClicked : FeedIntent
    data object ErrorShown : FeedIntent
}
