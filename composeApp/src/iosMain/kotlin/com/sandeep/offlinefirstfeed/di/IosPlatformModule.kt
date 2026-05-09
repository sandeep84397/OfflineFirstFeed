package com.sandeep.offlinefirstfeed.di

import com.sandeep.offlinefirstfeed.data.local.database.DatabaseFactory
import org.koin.dsl.module

val iosPlatformModule = module {
    single { DatabaseFactory() }
}
