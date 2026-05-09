package com.sandeep.offlinefirstfeed.domain.usecase

import app.cash.turbine.test
import com.sandeep.offlinefirstfeed.fake.FakeFeedRepository
import com.sandeep.offlinefirstfeed.fixtures.aPost
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class GetFeedUseCaseTest {

    @Test
    fun invoke_returnsFeedRepositoryFlow() = runTest {
        val post = aPost()
        val repository = FakeFeedRepository(posts = listOf(post))
        val useCase = GetFeedUseCase(repository)

        useCase().test {
            assertNotNull(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
