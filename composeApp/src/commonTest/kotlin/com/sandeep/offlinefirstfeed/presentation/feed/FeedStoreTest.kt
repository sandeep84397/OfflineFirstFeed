package com.sandeep.offlinefirstfeed.presentation.feed

import androidx.paging.PagingData
import app.cash.turbine.test
import com.sandeep.offlinefirstfeed.domain.model.Post
import com.sandeep.offlinefirstfeed.domain.model.SyncStatus
import com.sandeep.offlinefirstfeed.domain.model.User
import com.sandeep.offlinefirstfeed.domain.usecase.GetFeedUseCase
import com.sandeep.offlinefirstfeed.domain.repository.FeedRepository
import com.sandeep.kmpcore.navigation.Navigator
import com.sandeep.kmpcore.navigation.Route
import com.sandeep.offlinefirstfeed.presentation.navigation.CreatePostRoute
import com.sandeep.offlinefirstfeed.presentation.navigation.PostDetailRoute
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.Clock
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class FeedStoreTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private val fakePost = Post(
        id = "p1",
        author = User("u1", "Ada", null),
        text = "hello",
        imageUrl = null,
        likeCount = 5,
        likedByMe = false,
        createdAt = Clock.System.now(),
        updatedAt = Clock.System.now(),
        syncStatus = SyncStatus.SYNCED,
    )

    private val fakeUiModel = PostUiModel(
        id = "p1",
        authorDisplayName = "Ada",
        authorAvatarUrl = null,
        text = "hello",
        imageUrl = null,
        likeCount = "5",
        likedByMe = false,
        timeAgo = "0s ago",
        syncBadge = SyncBadge.NONE,
    )

    private class FakeNavigator : Navigator {
        val routes = mutableListOf<Route>()
        var popBackCalled = false

        override fun navigate(route: Route) {
            routes += route
        }

        override fun popBack() {
            popBackCalled = true
        }
    }

    private val noopHandler = CoroutineExceptionHandler { _, _ -> /* swallow in tests */ }

    private fun makeStore(navigator: Navigator = FakeNavigator()): FeedStore {
        val repo = object : FeedRepository {
            override fun observeFeed(): Flow<PagingData<Post>> =
                flowOf(PagingData.from(listOf(fakePost)))
        }
        val useCase = GetFeedUseCase(repo)
        return FeedStore(
            getFeed = useCase,
            mapToUiModel = { fakeUiModel },
            navigator = navigator,
            exceptionHandler = noopHandler,
        )
    }

    @Test
    fun `initial state has no error and is not refreshing`() = runTest {
        val store = makeStore()
        val state = store.state.value
        assertNull(state.errorMessage)
        assertEquals(false, state.isRefreshing)
        assertEquals(false, state.isOffline)
    }

    @Test
    fun `ErrorShown clears errorMessage`() = runTest {
        val store = makeStore()
        store.onIntent(FeedIntent.ErrorShown)
        assertNull(store.state.value.errorMessage)
    }

    @Test
    fun `PostClicked navigates to PostDetailRoute`() = runTest {
        val navigator = FakeNavigator()
        val store = makeStore(navigator)
        store.onIntent(FeedIntent.PostClicked("p1"))
        assertEquals(listOf<Route>(PostDetailRoute("p1")), navigator.routes)
    }

    @Test
    fun `CreatePostClicked navigates to CreatePostRoute`() = runTest {
        val navigator = FakeNavigator()
        val store = makeStore(navigator)
        store.onIntent(FeedIntent.CreatePostClicked)
        assertEquals(CreatePostRoute, navigator.routes.last())
    }

    @Test
    fun `ScreenStarted updates posts flow`() = runTest {
        val store = makeStore()
        store.posts.test {
            awaitItem()
            store.onIntent(FeedIntent.ScreenStarted)
            awaitItem()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Refresh updates posts flow`() = runTest {
        val store = makeStore()
        store.posts.test {
            awaitItem()
            store.onIntent(FeedIntent.Refresh)
            awaitItem()
            cancelAndIgnoreRemainingEvents()
        }
    }
}
