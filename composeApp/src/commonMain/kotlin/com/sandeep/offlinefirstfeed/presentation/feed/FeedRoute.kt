package com.sandeep.offlinefirstfeed.presentation.feed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FeedRoute(
    store: FeedStore = koinViewModel(),
) {
    val state by store.state.collectAsState()

    LaunchedEffect(Unit) {
        store.onIntent(FeedIntent.ScreenStarted)
    }

    FeedScreen(
        state = state,
        postsFlow = store.posts,
        onIntent = store::onIntent,
    )
}
