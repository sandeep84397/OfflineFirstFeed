package com.sandeep.offlinefirstfeed.presentation.feed

import androidx.paging.PagingData
import androidx.paging.map as mapPagingData
import com.sandeep.kmpcore.navigation.Navigator
import com.sandeep.kmpcore.presentation.BaseViewModel
import com.sandeep.offlinefirstfeed.domain.model.Post
import com.sandeep.offlinefirstfeed.domain.usecase.GetFeedUseCase
import com.sandeep.offlinefirstfeed.presentation.navigation.CreatePostRoute
import com.sandeep.offlinefirstfeed.presentation.navigation.PostDetailRoute
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FeedStore(
    private val getFeed: GetFeedUseCase,
    private val mapToUiModel: (Post) -> PostUiModel,
    private val navigator: Navigator,
    exceptionHandler: CoroutineExceptionHandler,
) : BaseViewModel(exceptionHandler) {

    private val _state = MutableStateFlow(FeedState())
    val state: StateFlow<FeedState> = _state.asStateFlow()

    private val _posts = MutableStateFlow<Flow<PagingData<PostUiModel>>>(emptyFlow())
    val posts: StateFlow<Flow<PagingData<PostUiModel>>> = _posts.asStateFlow()

    override fun onError(throwable: Throwable) {
        _state.update { it.copy(errorMessage = throwable.message) }
    }

    fun onIntent(intent: FeedIntent) {
        when (intent) {
            FeedIntent.ScreenStarted -> observeFeed()
            FeedIntent.Refresh -> observeFeed()
            is FeedIntent.PostClicked -> navigator.navigate(PostDetailRoute(intent.postId))
            FeedIntent.CreatePostClicked -> navigator.navigate(CreatePostRoute)
            FeedIntent.ErrorShown -> _state.update { it.copy(errorMessage = null) }
        }
    }

    private fun observeFeed() {
        val mappedFlow = getFeed().map { pagingData ->
            pagingData.mapPagingData { post -> mapToUiModel(post) }
        }
        _posts.update { mappedFlow }
    }
}
