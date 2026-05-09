package com.sandeep.offlinefirstfeed.presentation.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    state: FeedState,
    postsFlow: StateFlow<Flow<PagingData<PostUiModel>>>,
    onIntent: (FeedIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentPostsFlow = postsFlow.collectAsState().value
    val pagingItems = currentPostsFlow.collectAsLazyPagingItems()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = { Text("Feed") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onIntent(FeedIntent.CreatePostClicked) }) {
                Text("+")
            }
        },
    ) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = { onIntent(FeedIntent.Refresh) },
            modifier = Modifier.padding(paddingValues),
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(count = pagingItems.itemCount, key = pagingItems.itemKey { it.id }) { index ->
                    val post = pagingItems[index]
                    if (post != null) {
                        PostCard(
                            post = post,
                            onClick = { onIntent(FeedIntent.PostClicked(post.id)) },
                        )
                    }
                }
                if (pagingItems.loadState.append is LoadState.Loading) {
                    item { CircularProgressIndicator(modifier = Modifier.padding(16.dp)) }
                }
            }
        }
    }
}

@Composable
private fun PostCard(post: PostUiModel, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        onClick = onClick,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                Text(
                    text = post.authorDisplayName,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = post.timeAgo,
                    style = MaterialTheme.typography.labelSmall,
                )
                if (post.syncBadge != SyncBadge.NONE) {
                    Spacer(modifier = Modifier.width(8.dp))
                    SyncBadgeIndicator(badge = post.syncBadge)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = post.text, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${post.likeCount} likes",
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}

@Composable
private fun SyncBadgeIndicator(badge: SyncBadge) {
    val color = when (badge) {
        SyncBadge.PENDING -> MaterialTheme.colorScheme.tertiary
        SyncBadge.FAILED -> MaterialTheme.colorScheme.error
        SyncBadge.NONE -> return
    }
    Surface(
        color = color,
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.size(8.dp),
    ) {}
}
