package com.sandeep.offlinefirstfeed.di

import com.sandeep.offlinefirstfeed.presentation.feed.FeedStore
import com.sandeep.offlinefirstfeed.presentation.mapper.toUiModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        FeedStore(
            getFeed = get(),
            mapToUiModel = { it.toUiModel() },
            navigator = get(),
            exceptionHandler = get(),
        )
    }
}
