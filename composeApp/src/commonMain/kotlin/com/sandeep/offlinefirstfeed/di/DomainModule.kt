package com.sandeep.offlinefirstfeed.di

import com.sandeep.offlinefirstfeed.domain.usecase.GetFeedUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetFeedUseCase(get()) }
}
