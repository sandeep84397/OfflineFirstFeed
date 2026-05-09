package com.sandeep.offlinefirstfeed.di

import com.sandeep.offlinefirstfeed.data.local.database.DatabaseFactory
import com.sandeep.offlinefirstfeed.data.remote.api.FakeFeedApi
import com.sandeep.offlinefirstfeed.data.remote.api.InMemoryFeedApi
import com.sandeep.offlinefirstfeed.data.repository.FeedRepositoryImpl
import com.sandeep.offlinefirstfeed.domain.repository.FeedRepository
import org.koin.dsl.module

val dataModule = module {
    single { get<DatabaseFactory>().create() }
    single<FakeFeedApi> { InMemoryFeedApi(seed = 42L) }
    single<FeedRepository> { FeedRepositoryImpl(db = get(), api = get()) }
}
