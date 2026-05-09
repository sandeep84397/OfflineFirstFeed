package com.sandeep.offlinefirstfeed.di

import com.sandeep.offlinefirstfeed.data.local.database.DatabaseFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidPlatformModule = module {
    single { DatabaseFactory(context = androidContext()) }
}
